import simComponents.simComponents;
import star.base.neo.DoubleVector;
import star.base.report.*;
import star.common.*;
import star.flow.ForceCoefficientReport;
import star.flow.MassFlowReport;
import star.flow.MomentCoefficientReport;

import java.util.Objects;

public class genReports extends StarMacro {

    public void execute()
    {
        execute0();
    }
    private void execute0()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.activeSim.println("--- SETTING UP REPORTS ---");
        for (Report rep : activeSim.reports)
        {
            if (rep instanceof StatisticsReport)
            {

                AllSamplesFilter all_samples = (AllSamplesFilter) ((StatisticsReport) rep).getSampleFilterManager().getObject("All samples");
                LastNSamplesFilter lastN = (LastNSamplesFilter) ((StatisticsReport) rep).getSampleFilterManager().getObject("Last N Samples");
                if (simComponents.boolEnv("DES"))
                {
                    ((StatisticsReport) rep).setSampleFilterOption(SampleFilterOption.AllSamples);
                }
                else
                {
                    ((StatisticsReport) rep).setSampleFilterOption(SampleFilterOption.LastNSamples);
                    all_samples.setUpdateEvent(null);
                    lastN.setNSamples(500);
                }

                continue;

            }

            // Setting up flags and casted vars to manage logic and part assignments
            int flag = 0;
            MassFlowReport massFlowRep = null;
            MomentCoefficientReport pitchRep = null;
            ForceCoefficientReport fcRep = null;
            ElementCountReport elementCount;

            // Cast the rep to appropriate type based on rep type
            if (rep instanceof star.flow.MassFlowReport)
                massFlowRep = (MassFlowReport) rep;
            else if (rep instanceof  star.flow.MomentCoefficientReport)
            {
                pitchRep = (MomentCoefficientReport) rep;
                if (activeSim.fullCarFlag)
                    pitchRep.getReferenceArea().setValue(1.0);
                else
                    pitchRep.getReferenceArea().setValue(0.5);

                pitchRep.getReferenceVelocity().setValue(activeSim.freestreamVal);
            }
            else if (rep instanceof star.flow.ForceCoefficientReport)
            {
                fcRep = (ForceCoefficientReport) rep;
                if (activeSim.fullCarFlag)
                    fcRep.getReferenceArea().setValue(1.0);
                else
                    fcRep.getReferenceArea().setValue(0.5);

                fcRep.getReferenceVelocity().setValue(activeSim.freestreamVal);
            }
            else if (rep instanceof ElementCountReport)
            {
                elementCount = (ElementCountReport) rep;
                elementCount.getParts().setObjects(activeSim.activeSim.getRegionManager().getRegions());
            }


            String repName = rep.getPresentationName();
            for (String prefix : activeSim.aeroPrefixes)
            {
                if (repName.contains(prefix) && flag != 1)
                {
                    if (activeSim.partSpecBounds.containsKey(prefix))
                        Objects.requireNonNull(fcRep).getParts().setObjects(activeSim.partSpecBounds.get(prefix));
                    else
                        Objects.requireNonNull(fcRep).getParts().setObjects();
                    flag = 1;
                }
            }
            if (flag == 0 && (repName.toLowerCase().contains("lift") || repName.toLowerCase().contains("drag")))
            {
                Objects.requireNonNull(fcRep).getParts().setObjects(activeSim.partBounds);
                fcRep.getParts().addObjects(activeSim.wheelBounds);
                flag = 1;
            }

            if (flag == 0 && repName.contains(activeSim.pitchRepName))
            {
                Objects.requireNonNull(pitchRep).getParts().setObjects(activeSim.partBounds);
                pitchRep.getParts().addObjects(activeSim.wheelBounds);
                flag = 1;
            }

            if (flag == 0 && repName.contains(activeSim.massFlowRepName))
            {
                Objects.requireNonNull(massFlowRep).getParts().setObjects(activeSim.domainRadInlet);
                if (activeSim.dualRadFlag && activeSim.domainDualRadInlet != null)
                    massFlowRep.getParts().addObjects(activeSim.domainDualRadInlet);
            }
        }
        activeSim.maxVelocity.getParts().setObjects(activeSim.domainRegion);
        activeSim.maxVelocity.setRepresentation(activeSim.finiteVol);
        activeSim.point.getPointCoordinate().setCoordinate(activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"), new DoubleVector(new double[] {1, 1, 1}));
        activeSim.point.getInputParts().setObjects(activeSim.domainBounds);
        activeSim.point.getInputParts().addObjects(activeSim.radBounds);

        for (Monitor x : activeSim.activeSim.getMonitorManager().getMonitors())
        {
            if (activeSim.activeSim.getMonitorManager().getResidualMonitors().contains(x))
                continue;

            try {

                if (simComponents.boolEnv("DES"))
                    x.getStarUpdate().getUpdateModeOption().setSelected(StarUpdateModeOption.Type.TIMESTEP);
                else
                    x.getStarUpdate().getUpdateModeOption().setSelected(StarUpdateModeOption.Type.ITERATION);
            }
            catch (IllegalArgumentException e)
            {
                activeSim.activeSim.println("Trigger for " + x.getPresentationName() + " monitor not changed");
            }
        }
    }

}
