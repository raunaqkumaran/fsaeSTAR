import star.base.neo.DoubleVector;
import star.base.report.Report;
import star.common.StarMacro;
import simComponents.*;
import star.flow.ForceCoefficientReport;
import star.flow.MassFlowReport;
import star.flow.MomentCoefficientReport;

public class genReports extends StarMacro {

    public void execute()
    {
        execute0();
    }
    public void execute0()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        for (Report rep : activeSim.reports)
        {
            // Setting up flags and casted vars to manage logic and part assignments
            int flag = 0;
            MassFlowReport massFlowRep = null;
            MomentCoefficientReport pitchRep = null;
            ForceCoefficientReport fcRep = null;

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


            String repName = rep.getPresentationName();
            for (String prefix : activeSim.aeroPrefixes)
            {
                if (repName.contains(prefix) && flag != 1)
                {
                    fcRep.getParts().setObjects(activeSim.partSpecBounds.get(prefix));
                    flag = 1;
                }
            }
            if (flag == 0 && (repName.toLowerCase().contains("lift") || repName.toLowerCase().contains("drag")))
            {
                fcRep.getParts().setObjects(activeSim.partBounds);
                fcRep.getParts().addObjects(activeSim.wheelBounds);
                flag = 1;
            }

            if (flag == 0 && repName.contains(activeSim.pitchRepName))
            {
                pitchRep.getParts().setObjects(activeSim.partBounds);
                pitchRep.getParts().addObjects(activeSim.wheelBounds);
                flag = 1;
            }

            if (flag == 0 && repName.contains(activeSim.massFlowRepName))
            {
                massFlowRep.getParts().setObjects(activeSim.domainRadInlet);
                if (activeSim.dualRadFlag)
                    massFlowRep.getParts().addObjects(activeSim.domainDualRadInlet);
            }

        }
        activeSim.point.getPointCoordinate().setCoordinate(activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"), new DoubleVector(new double[] {1, 1, 1}));
        activeSim.point.getInputParts().setObjects(activeSim.domainBounds);
        activeSim.point.getInputParts().addObjects(activeSim.radBounds);
        activeSim.saveSim();

    }

}
