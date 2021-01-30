import star.base.neo.DoubleVector;
import star.common.Boundary;
import star.common.StarMacro;
import star.common.StarPlot;
import star.vis.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/*
This used to be a much nastier macro. It's not pretty now, but this used to be exponentially worse.
Goes through scenes and displayers to export reports, plots, and scenes for a sim. This is also a destructive macro. DO NOT save the result of the macro.
 */

public class PostProc extends StarMacro {

    public boolean isUnix;

    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        sim.activeSim.getSceneManager().setVerbose(true);
        try {
            isUnix = sim.isUnix();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Assign all regions to cross section derived part. Set origin to origin.
        sim.crossSection.getInputParts().setObjects(sim.activeSim.getRegionManager().getRegions());
        sim.crossSection.getOriginCoordinate().setCoordinate(sim.inches,
                sim.inches, sim.inches, new DoubleVector(new double[]{0, 0, 0}));
        if (sim.dualRadFlag) sim.crossSection.getInputParts().addObjects(sim.dualRadiatorRegion);
        setCrossSectionParts(sim);

        //Create displayer collections to ease use later. There's a naming convection to the scenes, views, and displayers.
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

        //Export mesh and 3D scenes before destroying the mesh.
        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                sim.inches, new DoubleVector(sim.profileDirection));
        sim.activeSim.println("---Exporting mesh---");
        postProc2D(sim, meshDisplayers, profileViews, sim.profileLimits, 1, 0.1, sim.meshScene);

        if (sim.activeSim.getSimulationIterator().getCurrentIteration() < 10)
            return;

        sim.activeSim.println("---Processing 3D---");
        postProc3D(sim, displayers3D, views3D);

        //Need to merge boundaries, otherwise it takes forever to get 2D data when you have 10k+ boundaries (thanks powertrain)
        sim.activeSim.println("---Merging boundaries---");
        Regions obj = new Regions();
        obj.mergeBoundaries(sim);

        //Export 2D scenes. Can figure out what the function arugements mean from the function definitions below.
        //The plane section must be correctly configured before handing control to the PostProc functions.
        sim.activeSim.println("---Processing 2D---");
        postProc2D(sim, displayers2D, profileViews, sim.profileLimits, 1, 0.1, sim.scene2D);

        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                sim.inches, new DoubleVector(sim.foreAftDirection));
        postProc2D(sim, displayers2D, aftForeViews, sim.aftForeLimits, 1, 1, sim.scene2D);

        sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                sim.inches, new DoubleVector(sim.topBottomDirection));
        postProc2D(sim, displayers2D, topBottomViews, sim.utLimits, 0.25, 0.1, sim.scene2D);
        postProc2D(sim, displayers2D, topBottomViews, sim.topBottomLimits, 4, 0.1, sim.scene2D);

    }

    /*
    exports a set of images for a 2D scene. Must pass all displayers, all views, limits for the cross section sweep, and the size of the increment between successive scenes.
     */

    private void postProc2D(SimComponents sim, Collection<Displayer> displayers2D, Collection<VisView> views2D, double[] limits, double increment, double glyph, Scene scn) {
        //Hide everything first, to make sure the macro operations from a consistent and well defined entry point.
        hideDisps(scn);

        //Generate a new folder for the scene. Every scene gets its own folder.
        String displayerPath = getFolderPath(scn.getPresentationName(), sim);
        makeDir(displayerPath);

        //Set the offset for the cross section. We're controling the scene by defining the plane section offset from its origin.
        sim.crossSection.getSingleValue().setUnits(sim.inches);
        double arrayMin = getMinimum(limits);
        //There's very little overhead to changing a view. There's a lot of overhead to change a displayer or move your cross section. Minimize the number of displayer and cross section changes you make.
        for (double section_val = limits[0]; section_val <= limits[1]; section_val += increment)
        {
            for (Displayer disp : displayers2D)
            {
                if (disp instanceof VectorDisplayer)
                {
                    VectorDisplayer dispV = (VectorDisplayer) disp;
                    dispV.getGlyphSettings().setRelativeToModelLength(glyph);
                }
                for (VisView view : views2D)
                {
                    String filename = generateFileName(displayerPath, scn, disp, view, String.valueOf(section_val + (int) Math.floor(arrayMin)), ".png");
                    //This will not generate a new exported scene if the file path already exists. This is a very dumb way to do it, but this is one of those things where going big brain isn't worth the time.
                    if (!doesFileExist(filename)) {
                        disp.setRepresentation(sim.finiteVol);
                        disp.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                        sim.crossSection.getSingleValue().setValue(section_val);
                        scn.setCurrentView(view);
                        saveFile(filename, scn);
                    }
                    else
                    {
                        sim.activeSim.println(filename + " already exists");
                    }
                }
                hideDisps(scn);
            }
        }
    }

    /*
    get minimum value of array
     */

    private double getMinimum(double[] array)
    {
        double minimum = array[0];

        for (double v : array) {
            if (v < minimum)
                minimum = v;
        }

        return minimum;
    }

    //Return true if a file already exists. Generally used to avoid regenerating scenes that have already been exported.
    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "BooleanMethodNameMustStartWithQuestion"})
    private boolean doesFileExist(String filepath)
    {
        String resolved = resolvePath(filepath);
        File f = new File(resolved);
        return f.exists();
    }

    //Basically the same thing as postProc2D, without the added complexity of the plane section derived part.
    private void postProc3D(SimComponents sim, Collection<Displayer> displayers3D, Collection<VisView> views3D) {
        //Again, keep displayers as your outer loop. Changing a displayer is a lot of overhead. Changing a view is very little overhead.
        for (Displayer disp :  displayers3D)
        {
            hideDisps(sim.scene3D);
            disp.setRepresentation(sim.finiteVol);
            disp.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
            String displayerPath = getFolderPath(sim.scene3D.getPresentationName(), sim);
            for (VisView view : views3D)            //Unfiltered exports
            {
                String filepath = generateFileName(displayerPath, sim.scene3D, disp, view, "", ".png");
                if (!doesFileExist(filepath)) {
                    disp.getInputParts().setObjects(sim.partBounds);
                    disp.getInputParts().addObjects(sim.wheelBounds);
                    sim.scene3D.setCurrentView(view);
                    saveFile(filepath, sim.scene3D);
                }
                else
                {
                    sim.activeSim.println(filepath + " already exists");
                }
            }

            for (VisView view : views3D)        //Filtered exports
            {
                String filepath = generateFileName(displayerPath, sim.scene3D, disp, view, "Filtered",".png");
                if (!doesFileExist(filepath)) {
                    disp.getInputParts().setObjects(getParts(sim, view));
                    sim.scene3D.setCurrentView(view);
                    saveFile(filepath, sim.scene3D);
                }
                else
                {
                    sim.activeSim.println(filepath + " already exists");
                }
            }
        }
    }

    //Essentially enforces a naming convection. The name of a given scene export is defined by the scene name,
    // the displayer name, view name, and any additional "appended" string. As well as the file extension
    public String generateFileName(String folder, Scene scn, Displayer disp, VisView view, String append, String ext)
    {
        Double offset = null;
        return generateFileName(folder, scn, disp, view, offset, append, ext);
    }

    //This is essentially enforcing a naming convention for a given combination of scene, displayer, view, offset, and any appended text. This should be used to ensure every exported image has a unique file name, otherwise you're going to miss scenes.
    public String generateFileName(String folder, Scene scn, Displayer disp, VisView view, Double offset, String append, String ext)
    {
        String output = folder + File.separator + scn.getPresentationName() + "_" + disp.getPresentationName() + "_" + view.getPresentationName();
        if (offset != null)
            output = output + "_" + offset;
        if (append.length() > 0) output = output + "_" + append;
        output = output + ext;
        return output;
    }

    private void saveFile (String filePath, Scene scn)
    {
        scn.printAndWait((resolvePath(filePath)), 1, 4000, 2000, true, false);

    }

    //Generates a collection of parts to assign to a given displayer. A RW displayer can use this function to ensure only RW surfaces are assigned to the displayer
    public Collection<Boundary> getParts(SimComponents sim, VisView view)
    {
        Collection<Boundary> desiredParts = new ArrayList<>();
        String[] partArray = getSecondKey(view);    //So the name of the view defines which part to filter. [3D] [RW] means you only want RW in the image. [3D] [RW SW] means you want RW and SW in the image.
        for (String part : partArray)
        {
            if (sim.partSpecBounds.containsKey(part))
                desiredParts.addAll(sim.partSpecBounds.get(part));
        }

        return desiredParts;
    }

    // This is parsing. If a view is called [2D] [AftFore] car, this extracts the [AftFore] part. Important so the plane section scene can be defined correctly.
    private String[] getSecondKey(VisView view) {
        String viewName = view.getPresentationName();
        String partSegment = viewName.split("]", 0)[1];
        return partSegment.replaceAll("\\[", "").strip().split("[ ]");
    }

    // Retuns every view of a given type. getViews("2D") will only return views that have the [2D] indicator.
    public Collection<VisView> getViews (String key, SimComponents sim)
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

    public void exportPlots(SimComponents sim) {
        String plotsPath;

        for (StarPlot plot : sim.plots)
        {
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

    public String getFolderPath(String folderName, SimComponents sim) {

        String prefix;

        if (isUnix)
            prefix = sim.separator + "tmp";
        else
            prefix = sim.dir;

        return prefix + sim.separator + sim.simName + sim.separator + folderName;
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

    //You want your cross section to be set so the origin is at 0,0,0 (for repeatability reasons), and want to have the volumes of your domain, and radiator(s).
    private static void setCrossSectionParts(SimComponents sim) {
        sim.crossSection.getInputParts().setObjects(sim.domainRegion, sim.radiatorRegion);
        if (sim.dualRadFlag)
            sim.crossSection.getInputParts().addObjects(sim.dualRadiatorRegion);
        sim.crossSection.getSingleValue().getValueQuantity().setValue(0);
        sim.crossSection.getOriginCoordinate().setCoordinate(sim.inches,
                sim.inches, sim.inches, new DoubleVector(new double[]{0, 0, 0}));
    }

}
