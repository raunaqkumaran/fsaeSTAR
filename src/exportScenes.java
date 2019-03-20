// Despite what the name says, this will also export plots. Uses (or will use) sysenvs to selectively sweep through.
// This macro is messier than some of the others. There's a lot going on, and not a lot of configurability
// I know what booleans are, but I completely forgot they were a thing when I wrote this (rip).
// There may be some oddities with some of the logic operations here because of that.



import star.base.neo.DoubleVector;
import star.common.StarMacro;
import star.common.StarPlot;
import simComponents.*;
import star.vis.*;

import java.io.File;
import java.util.Collection;

public class exportScenes extends StarMacro {

    public void execute()
    {
        execute0();
    }

    public void execute0()
    {
        simComponents sim;
        sim = new simComponents(getActiveSimulation());

        int rws;            // Rear wing sweep flag
        int fws;            // Front wing sweep flag
        int cs;             // Car sweep flag
        int uts;            // UT sweep flag
        int ps;             // Plot sweep flag
        String pressurePath;
        String velocityPath;
        String nearWallPath;
        String farWallPath;
        String plotsPath;
        double multiplier;


        rws = assignFlag("rws");
        fws = assignFlag("fws");
        cs = assignFlag("cs");
        uts = assignFlag("uts");
        ps = assignFlag("ps");
        multiplier = getMultiplier();

        // Exports all plots to a folder
        if (ps == 1)
        {
            for (StarPlot plot : sim.plots)
            {
                String plotName = plot.getPresentationName().replaceAll("[\\/]", "");
                plotsPath = sim.dir + sim.separator + sim.simName + sim.separator + "Plots";
                makeDir(plotsPath);
                plot.export(plotsPath + sim.separator + plotName + ".txt");
                plot.encode(plotsPath + sim.separator + plotName + ".png", "png", 4000, 2000);
                sim.activeSim.println(this.getClass().getName() + " plot printed");
            }
        }

        // Sweeps through the geometry if at least something is non zero

        if (rws + fws + uts + cs != 0)
        {
            // Make sure everything's hidden

            hideDisps(sim.scene3D);


            // Start exporting pressures

            pressurePath = sim.dir + sim.separator
                    + sim.simName + sim.separator + "Pressure";
            makeDir(pressurePath);
            sim.pressure3D.setRepresentation(sim.finiteVol);
            sim.pressure3D.getInputParts().setObjects();
            sim.pressure3D.getInputParts().addParts(sim.domainBounds);
            sim.pressure3D.getInputParts().removeParts(sim.freestreamBounds);
            sim.pressure3D.getScalarDisplayQuantity().setRange(sim.pressRange);
            sim.pressure3D.
                    setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
            sweep3D(cs, rws, uts, fws, pressurePath, sim);
            sim.pressure3D.
                    setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
            sim.crossSection.getInputParts().setObjects(sim.domainRegion, sim.radiatorRegion);

            // Export Wall Y+ for far wall ranges

            farWallPath = sim.dir + sim.separator + sim.simName
                    + sim.separator + "Wall Y+ far wall";
            makeDir(farWallPath);
            sim.wallY.getInputParts().setObjects();
            sim.wallY.getInputParts().addParts(sim.domainBounds);
            sim.wallY.getInputParts().removeParts(sim.freestreamBounds);
            sim.wallY.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
            sim.wallY.setRepresentation(sim.finiteVol);
            sim.wallY.getScalarDisplayQuantity().setRange(sim.wallYRange);
            sweep3D(cs, rws, uts, fws, farWallPath, sim);

            // Export Wall Y+ for near wall ranges
            nearWallPath = sim.dir + sim.separator + sim.simName
                    + sim.separator + "Wall Y+ near wall";
            makeDir(nearWallPath);
            sim.wallY.getScalarDisplayQuantity().setRange(sim.wallYRangeNearWall);
            sweep3D(cs, rws, uts, fws, nearWallPath, sim);
            sim.wallY.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);

            // Iterate through vertical sections
            hideDisps(sim.planeSectionScene);
            sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches, sim.inches,
                    sim.inches, new DoubleVector(sim.topBottomDirection));
            sim.velVector2D.setRepresentation(sim.finiteVol);           // These finite vol statements make sure
                                                                        // the scene is reading the volume mesh,
                                                                        // and not something else
            sim.velVector2D.getGlyphSettings().setRelativeToModelLength(0.1);
            sim.velVector2D.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_GLYPH);
            sim.pressure2D.getScalarDisplayQuantity().setRange(sim.pressRange);
            sim.pressure2D.setRepresentation(sim.finiteVol);
            Double i = sim.utTopBottom[0];
            velocityPath = sim.dir + sim.separator + sim.simName + sim.separator + "Velocity";
            makeDir(velocityPath);

            // Push i above the range for fine ut increments
            if (uts == 0)
                i = sim.utTopBottom[1];

            while (i <= sim.topBottomLimits[1])
            {
                double [] loc = simComponents.vectorScale(i, sim.topBottomDirection);
                sim.crossSection.getOriginCoordinate().setCoordinate(sim.inches, sim.inches,
                        sim.inches, new DoubleVector(loc));

                // Don't waste time if there isn't a flag enabled for exports.
                if (uts + cs != 0)
                {
                    sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                    sim.velVector2D.
                            setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_GLYPH);
                    saveFile(sim.utBottom, sim.planeSectionScene, sim,
                            velocityPath, Double.toString(i) + " Glyph");
                    sim.velVector2D.
                            setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
                    saveFile(sim.utBottom, sim.planeSectionScene, sim,
                            velocityPath, Double.toString(i) + " LIC");
                    sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
                    sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                    saveFile(sim.utBottom, sim.planeSectionScene, sim,
                            pressurePath, Double.toString(i) + " Pressure");
                    sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
                }

                // Iterate i counter. End loop after fine UT increments if car flag is off
                if (i <= sim.utTopBottom[1])
                    i += (0.25 * multiplier);
                else
                {
                    if (cs == 0)
                        i = sim.topBottomLimits[1];
                    i += (3 * multiplier);
                }
            }

            i = sim.profileLimits[0];

            while (i <= sim.profileLimits[1])
            {
                double [] loc = simComponents.vectorScale(i, sim.profileDirection);
                sim.crossSection.getOriginCoordinate().setCoordinate(sim.inches,
                        sim.inches, sim.inches, new DoubleVector(loc));
                sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches,
                        sim.inches, sim.inches, new DoubleVector(sim.profileDirection));

                // Export all LIC scenes

                sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                sim.velVector2D.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
                if (fws != 0)
                    saveFile(sim.fwProfile, sim.planeSectionScene, sim, velocityPath, Double.toString(i) + " LIC");
                if (rws != 0)
                    saveFile(sim.rwProfile, sim.planeSectionScene, sim, velocityPath, Double.toString(i) + " LIC");
                if (uts != 0)
                    saveFile(sim.utProfile, sim.planeSectionScene, sim, velocityPath, Double.toString(i) + " LIC");

                // Export all glyph scenes

                sim.velVector2D.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_GLYPH);
                if (fws != 0)
                    saveFile(sim.fwProfile, sim.planeSectionScene, sim,
                            velocityPath, Double.toString(i) + " Glyph");
                if (rws != 0)
                    saveFile(sim.rwProfile, sim.planeSectionScene, sim,
                            velocityPath, Double.toString(i) + " Glyph");
                if (uts != 0)
                    saveFile(sim.utProfile, sim.planeSectionScene, sim,
                            velocityPath, Double.toString(i) + " Glyph");
                sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);

                // Export all pressure scenes


                sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);
                if (fws != 0)
                    saveFile(sim.fwProfile, sim.planeSectionScene, sim,
                            pressurePath, Double.toString(i) + " Pressure");
                if (rws != 0)
                    saveFile(sim.rwProfile, sim.planeSectionScene, sim,
                            pressurePath, Double.toString(i) + " Pressure");
                if (uts != 0)
                    saveFile(sim.utProfile, sim.planeSectionScene, sim,
                            pressurePath, Double.toString(i) + " Pressure");
                sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
                i += (1 * multiplier);
            }

            i = sim.aftForeLimits[0];

            while (i <= sim.aftForeLimits[1])
            {
                double [] loc = simComponents.vectorScale(i, sim.foreAftDirection);
                sim.crossSection.getOrientationCoordinate().setCoordinate(sim.inches,
                        sim.inches, sim.inches, new DoubleVector(sim.foreAftDirection));
                sim.crossSection.getOriginCoordinate().setCoordinate(sim.inches,
                        sim.inches, sim.inches, new DoubleVector(loc));
                sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);

                sim.velVector2D.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_GLYPH);
                if (cs != 0)
                    saveFile(sim.carRear, sim.planeSectionScene, sim, velocityPath, Double.toString(i) + " Glyph");
                if (uts != 0)
                    saveFile(sim.utRear, sim.planeSectionScene, sim, velocityPath,Double.toString(i) + " Glyph" );

                sim.velVector2D.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);

                if (cs != 0)
                    saveFile(sim.carRear, sim.planeSectionScene, sim, velocityPath, Double.toString(i) + " LIC");
                if (uts != 0)
                    saveFile(sim.utRear, sim.planeSectionScene, sim, velocityPath,Double.toString(i) + " LIC" );

                sim.velVector2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
                sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.SHOW_ALL_PARTS);

                if (cs != 0)
                    saveFile(sim.carRear, sim.planeSectionScene, sim, pressurePath,
                            Double.toString(i) + " Pressure");
                if (uts != 0)
                    saveFile(sim.utRear, sim.planeSectionScene, sim, pressurePath,
                            Double.toString(i) + " Pressure" );
                sim.pressure2D.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);

                i += (1 * multiplier);
            }


        }

        sim.saveSim();
    }

    private int assignFlag(String envName)
    {

        if (System.getenv(envName) != null)
            return Integer.parseInt(System.getenv(envName));
        else
            return 0;

    }

    private double getMultiplier()
    {
        if (System.getenv("sceneMultiplier") != null)
            return Double.parseDouble(System.getenv("sceneMultiplier"));
        else
            return 1;
    }

    public void makeDir(String pathName)
    {
        File newFol = new File(resolvePath(pathName));
        if (!newFol.exists())
            newFol.mkdirs();
    }

    // Hide all the displayers associated with the scene, so you only get what the macro is programmed for.

    private void hideDisps(Scene scn)
    {
        Collection<Displayer> disps = scn.getDisplayerManager().getNonDummyObjects();
        for (Displayer dis : disps)
            dis.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
    }

    // Give it a scene, a displayer, an i index (useful for planar sections), and it'll save the scene

    private void saveFile (VisView view, Scene scn, simComponents sim, String folPath, String i)
    {
        String filePath = folPath + sim.separator + view.getPresentationName() + " " + i + ".png";
        scn.setCurrentView(view);
        scn.printAndWait(resolvePath(filePath),1, 4000, 2000, true, false);
        getActiveSimulation().println(this.getClass().getName() + "- " + filePath + " printed");
    }

    private void sweep3D(int cs, int rws, int uts, int fws, String folPath, simComponents sim)
    {
        // Checks flags to skip exporting undesired scenes
        if (cs != 0)
            saveFile(sim.carStd, sim.scene3D, sim, folPath, Double.toString(0));
        if (rws != 0) {
            saveFile(sim.rwBottom, sim.scene3D, sim, folPath, Double.toString(0));
            saveFile(sim.rwTop, sim.scene3D, sim, folPath, Double.toString(0));
            saveFile(sim.rwBottom, sim.scene3D, sim, folPath, Double.toString(0));
        }
        if (uts != 0) {
            saveFile(sim.utBottom, sim.scene3D, sim, folPath, Double.toString(0));
        }

        if (fws != 0) {
            saveFile(sim.fwBottom, sim.scene3D, sim, folPath, Double.toString(0));
            saveFile(sim.fwTop, sim.scene3D, sim, folPath, Double.toString(0));
        }

    }
    


}
