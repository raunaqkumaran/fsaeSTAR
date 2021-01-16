import star.base.report.*;
import star.common.*;
import star.flow.*;


/*
Sets up reports.
 */

import java.util.Objects;

public class GenReports extends StarMacro {

    public void execute()
    {
        execute0();
    }
    private void execute0()
    {
        SimComponents activeSim = new SimComponents(getActiveSimulation());
        activeSim.activeSim.println("--- SETTING UP REPORTS ---");

        //Iterate through every report in the sim file. Process as appropriate.
        for (Report rep : activeSim.reports)
        {
            if (rep instanceof StatisticsReport)
                setupStatisticsReport(activeSim, (StatisticsReport) rep);
            else
                setupUnkownReport(activeSim, rep);
        }

        //Set up the report for max velocity. Need this to make sure the MeshRepair trigger works. Assign the whole domain to it (might want to do radiator regions too, but no...)
        activeSim.maxVelocity.getParts().setObjects(activeSim.domainRegion);
        activeSim.maxVelocity.setRepresentation(activeSim.finiteVol);

        //Set up monitors. If it's transient, don't update monitors on every iteration. Do every timestep. If it's steady, update every iteration. Throw an exception if something breaks. Since this isn't critical code you probably don't need this to be a terminal exception.
        setupMonitors(activeSim);

        setupPlots(activeSim);
    }

    private void setupMonitors(SimComponents activeSim) {
        for (Monitor x : activeSim.activeSim.getMonitorManager().getMonitors())
        {
            if (activeSim.activeSim.getMonitorManager().getResidualMonitors().contains(x))
                continue;

            try {

                if (activeSim.DESFlag)
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

    private void setupUnkownReport(SimComponents activeSim, Report rep)
    {
        // Setting up flags and casted vars to manage logic and part assignments. The flag var is essentially a break, without using a break.
        int flag = 0;
        MassFlowReport massFlowRep = null;
        MomentCoefficientReport pitchRep = null;
        ForceCoefficientReport fcRep = null;
        ElementCountReport elementCount;

        // Cast the rep to appropriate type based on rep type. Set referance area to 0.5 or 1 based on full car or half car. Set reference velocity to freestream val (defined in the parameter, not affected by yaw)
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

        //Go through aeroPrefixes and assign them to their appropriate lift and drag reports. Set the flag to 1, essentially exiting out of the loop before double counting.
        String repName = rep.getPresentationName();
        for (String prefix : activeSim.AERO_PREFIXES)
        {
            if (repName.contains(prefix) && flag != 1)          //repName only contains prefix if it is a part specific report (eg "RW Lift")
            {
                if (activeSim.partSpecBounds.containsKey(prefix))
                    Objects.requireNonNull(fcRep).getParts().setObjects(activeSim.partSpecBounds.get(prefix));
                else
                    Objects.requireNonNull(fcRep).getParts().setObjects();
                flag = 1;
            }
        }

        //Make sure we don't forget about adding non-aero parts and wheels to the global lift and drag reports.
        if (flag == 0 && (repName.toLowerCase().contains("lift") || repName.toLowerCase().contains("drag")))
        {
            Objects.requireNonNull(fcRep).getParts().setObjects(activeSim.partBounds);
            fcRep.getParts().addObjects(activeSim.wheelBounds);
            flag = 1;
        }

        //Add everything to the pitch moment report.
        if (flag == 0 && repName.contains(activeSim.pitchRepName))
        {
            Objects.requireNonNull(pitchRep).getParts().setObjects(activeSim.partBounds);
            pitchRep.getParts().addObjects(activeSim.wheelBounds);
            flag = 1;
        }

        //Add rad boundaries to the mass flow rep.
        if (flag == 0 && repName.contains(activeSim.massFlowRepName))
        {
            Objects.requireNonNull(massFlowRep).getParts().setObjects(activeSim.domainRadInlet);
            if (activeSim.dualRadFlag && activeSim.domainDualRadInlet != null)
                massFlowRep.getParts().addObjects(activeSim.domainDualRadInlet);
        }

    }

    private void setupStatisticsReport(SimComponents activeSim, StatisticsReport rep) {
        AllSamplesFilter all_samples = (AllSamplesFilter) rep.getSampleFilterManager().getObject("All samples");
        LastNSamplesFilter lastN = (LastNSamplesFilter) rep.getSampleFilterManager().getObject("Last N Samples");
        if (activeSim.DESFlag)
        {
            rep.setSampleFilterOption(SampleFilterOption.AllSamples);
        }
        else
        {
            rep.setSampleFilterOption(SampleFilterOption.LastNSamples);
            all_samples.setUpdateEvent(null);
            lastN.setNSamples(500);
        }

    }

    public void setupPlots(SimComponents sim)
    {
        for (String x: sim.forceTables.keySet())
        {
            AccumulatedForceTable temp = sim.forceTables.get(x);
            temp.setRepresentation(sim.finiteVol);
            temp.getParts().setObjects(sim.partSpecBounds.get(x));
            temp.extract();
        }
        
        for (AccumulatedForceTable x : sim.forceTables.values()) {
            AccumulatedForceHistogram hist = (AccumulatedForceHistogram) x.getHistogram();
            hist.getBinDirection().setComponents(0, 1, 0);
            hist.getProfileDirection().setComponents(1, 0, 0);
            hist.getForceDirection().setComponents(0, 0, 1);
            x.extract();
        }

        for (StarPlot plot : sim.plots)
        {
            //Need to make sure the residuals X axis is physical time for transient, otherwise the plot is basically unreadable.
            if (sim.DESFlag && !plot.getPresentationName().contains("Residuals"))
                ((MonitorPlot) plot).setXAxisMonitor((PlotableMonitor) sim.activeSim.getMonitorManager().getMonitor("Physical Time"));
            else if (plot instanceof MonitorPlot)
                ((MonitorPlot) plot).setXAxisMonitor((PlotableMonitor) sim.activeSim.getMonitorManager().getMonitor("Iteration"));
        }
    }

}
