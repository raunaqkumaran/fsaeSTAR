import star.base.report.PlotableMonitor;
import star.common.MonitorPlot;
import star.common.StarMacro;
import star.common.StarPlot;
import star.flow.AccumulatedForceTable;
import star.vis.Displayer;
import star.vis.DisplayerVisibilityOverride;
import star.vis.Scene;

import java.io.File;
import java.util.Collection;

public class postProc extends StarMacro {

    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
    }

    public void exportPlots(simComponents sim) {
        String plotsPath;
        for (AccumulatedForceTable x : sim.forceTables.values())
            x.extract();
        for (StarPlot plot : sim.plots)
        {
            if (simComponents.boolEnv("DES") && !plot.getPresentationName().contains("Residuals"))
                ((MonitorPlot) plot).setXAxisMonitor((PlotableMonitor) sim.activeSim.getMonitorManager().getMonitor("Physical Time"));
            else if (plot instanceof MonitorPlot)
                ((MonitorPlot) plot).setXAxisMonitor((PlotableMonitor) sim.activeSim.getMonitorManager().getMonitor("Iteration"));
            String plotName = plot.getPresentationName().replaceAll("[\\/]", "");
            String plotsImagePath;
            plotsPath = getFolderPath("Plots", sim);
            makeDir(plotsPath);
            plotsImagePath = plotsPath + sim.separator + plotName + ".png";
            plotsPath = plotsPath + sim.separator + plotName + ".txt";

            plot.export(plotsPath);
            plot.encode(plotsImagePath, "png", 4000, 2000);

        }
    }

    private static String getFolderPath(String folderName, simComponents sim) {
        return sim.dir + sim.separator + sim.simName
                + sim.separator + folderName;
    }

    private void makeDir(String pathName)
    {
        File newFol = new File(resolvePath(pathName));
        if (!newFol.exists())
            newFol.mkdirs();
    }

    private static void hideDisps(Scene scn)
    {
        Collection<Displayer> disps = scn.getDisplayerManager().getNonDummyObjects();
        for (Displayer dis : disps)
            dis.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
    }

    private static void setCrossSectionParts(simComponents sim) {
        sim.crossSection.getInputParts().setObjects(sim.domainRegion, sim.radiatorRegion);
        if (sim.dualRadFlag)
            sim.crossSection.getInputParts().addObjects(sim.dualRadiatorRegion);
        sim.crossSection.getSingleValue().getValueQuantity().setValue(0);
    }

}
