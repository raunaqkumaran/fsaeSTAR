import star.base.neo.DoubleVector;
import star.base.report.PlotableMonitor;
import star.common.Boundary;
import star.common.MonitorPlot;
import star.common.StarMacro;
import star.common.StarPlot;
import star.flow.AccumulatedForceTable;
import star.screenplay.Screenplay;
import star.screenplay.ScreenplayDirector;
import star.screenplay.ScreenplaySelectorDescriptor;
import star.vis.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class postProc extends StarMacro {

    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        sim.crossSection.getInputParts().setObjects(sim.domainRegion, sim.radiatorRegion);
        if (sim.dualRadFlag) sim.crossSection.getInputParts().addObjects(sim.dualRadiatorRegion);
        exportPlots(sim);
        Collection<Displayer> displayers3D = sim.scene3D.getDisplayerManager().getNonDummyObjects();
        Collection<Displayer> displayers2D = sim.scene2D.getDisplayerManager().getNonDummyObjects();
        Collection<Displayer> meshDisplayers = sim.meshScene.getDisplayerManager().getNonDummyObjects();
        Collection<VisView> views3D = getViews("3D", sim);
        Collection<VisView> views2D = getViews("2D", sim);
        Collection<VisView> profileViews = new ArrayList<>();
        Collection<VisView> aftForeViews = new ArrayList<>();
        Collection<VisView> topBottomViews = new ArrayList<>();
        for (VisView view : views2D)
        {
            String key = getSecondKey(view)[0];
            if (key.equals("Profile"))
                profileViews.add(view);
            else if (key.equals("AftFore"))
                aftForeViews.add(view);
            else if (key.equals("TopBottom"))
                topBottomViews.add(view);
        }

        postProc3D(sim, displayers3D, views3D);
        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
            sim.inches, new DoubleVector(sim.profileDirection));
        postProc2D(sim, meshDisplayers, profileViews, sim.profileLimits, 1);
        postProc2D(sim, displayers2D, profileViews, sim.profileLimits, 1);

        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                sim.inches, new DoubleVector(sim.foreAftDirection));
        postProc2D(sim, displayers2D, aftForeViews, sim.aftForeLimits, 1);

        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                sim.inches, new DoubleVector(sim.topBottomDirection));
        postProc2D(sim, displayers2D, topBottomViews, sim.utLimits, 0.25);
        postProc2D(sim, displayers2D, topBottomViews, sim.topBottomLimits, 4);

    }

    private void postProc2D(simComponents sim, Collection<Displayer> displayers2D, Collection<VisView> views2D, double[] limits, double increment) {
        hideDisps(sim.scene2D);

        String displayerPath = getFolderPath(sim.scene2D.getPresentationName(), sim);
        makeDir(displayerPath);

        for (double i = limits[0]; i <= limits[1]; i += increment)
        {
            for (Displayer disp : displayers2D)
            {
                
                for (VisView view : views2D)
                {
                    String filename = generateFileName(displayerPath, sim.scene2D, disp, view, String.valueOf(i), ".png");
                    if (!fileExists(filename)) {
                        disp.setRepresentation(sim.finiteVol);
                        disp.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                        sim.crossSection.getSingleValue().setUnits(sim.inches);
                        sim.crossSection.getSingleValue().setValue(i);
                        sim.scene2D.setCurrentView(view);
                        saveFile(filename, sim.scene2D);
                    }
                    else
                    {
                        sim.activeSim.println(filename + " already exists");
                    }
                }
                hideDisps(sim.scene2D);
            }
        }
    }

    private boolean fileExists(String filepath)
    {
        String resolved = resolvePath(filepath);
        File f = new File(resolved);
        return f.exists();
    }

    private void postProc3D(simComponents sim, Collection<Displayer> displayers3D, Collection<VisView> views3D) {
        for (Displayer disp :  displayers3D)
        {
            hideDisps(sim.scene3D);
            disp.setRepresentation(sim.finiteVol);
            disp.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
            String displayerPath = getFolderPath(sim.scene3D.getPresentationName(), sim);
            for (VisView view : views3D)            //Unfiltered exports
            {
                String filepath = generateFileName(displayerPath, sim.scene3D, disp, view, "", ".png");
                if (!fileExists(filepath)) {
                    disp.getInputParts().setObjects(sim.partBounds);
                    disp.getInputParts().addObjects(sim.wheelBounds);
                    sim.scene3D.setCurrentView(view);
                    saveFile(filepath, sim.scene3D);
                }
            }

            for (VisView view : views3D)        //Filtered exports
            {
                String filepath = generateFileName(displayerPath, sim.scene3D, disp, view, "Filtered",".png");
                if (!fileExists(filepath)) {
                    disp.getInputParts().setObjects(getParts(sim, view));
                    sim.scene3D.setCurrentView(view);
                    saveFile(filepath, sim.scene3D);
                }
            }
        }
    }

    public String generateFileName(String folder, Scene scn, Displayer disp, VisView view, String append, String ext)
    {
        Double offset = null;
        return generateFileName(folder, scn, disp, view, offset, append, ext);
    }

    private void saveFile (String filePath, Scene scn)
    {
        scn.printAndWait((resolvePath(filePath)), 1, 4000, 2000, true, false);

    }

    public String generateFileName(String folder, Scene scn, Displayer disp, VisView view, Double offset, String append, String ext)
    {
        String output = folder + File.separator + scn.getPresentationName() + "_" + disp.getPresentationName() + "_" + view.getPresentationName();
        if (offset != null)
            output = output + "_" + String.valueOf(offset);
        if (append.length() > 0) output = output + "_" + append;
        output = output + ext;
        return output;
    }


    public Collection<Boundary> getParts(simComponents sim, VisView view)
    {
        Collection<Boundary> desiredParts = new ArrayList<>();
        String[] partArray = getSecondKey(view);
        for (String part : partArray)
        {
            if (sim.partSpecBounds.containsKey(part))
                desiredParts.addAll(sim.partSpecBounds.get(part));
        }

        return desiredParts;
    }

    private String[] getSecondKey(VisView view) {
        String viewName = view.getPresentationName();
        String partSegment = viewName.split("]", 0)[1];
        return partSegment.replaceAll("\\[", "").strip().split("[ ]");
    }

    public Collection<VisView> getViews (String key, simComponents sim)
    {
        Collection<VisView> desiredViews = new ArrayList<>();
        Collection<VisView> allViews = sim.activeSim.getViewManager().getViews();
        for (VisView x : allViews)
        {
            String[] splitString = x.getPresentationName().split("]", 0);
            String sceneType = splitString[0].strip().replaceAll("\\[", "");
            if (sceneType.equals(key))
                desiredViews.add(x);
        }

        return desiredViews;
    }

    public void exportPlots(simComponents sim) {
        String plotsPath;
        for (AccumulatedForceTable x : sim.forceTables.values())
            x.extract();
        for (StarPlot plot : sim.plots)
        {
            if (sim.DESFlag && !plot.getPresentationName().contains("Residuals"))
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
