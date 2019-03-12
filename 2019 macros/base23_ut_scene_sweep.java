// Sweeps through UT 2D profile scenes and along wheelbase sections in glyph velocity, line integral velocity, and pressure.
// Generates 3D pressure scene of the bottom of the undertray.
// Generates glyph and line integral velocity scenes for horizontal cross sections.

package macro;

import java.io.File;
import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import java.lang.String;


public class base23_ut_scene_sweep extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {

        Simulation simulation_0 =
                getActiveSimulation();

        FvRepresentation volMesh = (FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh");

        Scene planeSections =
                simulation_0.getSceneManager().getScene("Plane section scenes");

        VectorDisplayer velocityVectors =
                ((VectorDisplayer) planeSections.getDisplayerManager().getDisplayer("Velocity vector"));

        // velocityVectors.setVisTransform(simulation_0.getTransformManager().getObject("Freestream.Symmetry Plane 1"));

        GlyphSettings vectorGlyph =
                velocityVectors.getGlyphSettings();

        vectorGlyph.setRelativeToModelLength(0.1);

        ScalarDisplayer pressure2D = (ScalarDisplayer) planeSections.getDisplayerManager().getDisplayer("Pressure");
        pressure2D.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-500, 200}));

        // pressure2D.setVisTransform(simulation_0.getTransformManager().getObject("Freestream.Symmetry Plane 1"));

        Scene scenes3D =
                simulation_0.getSceneManager().getScene("3D scenes");

        ScalarDisplayer pressure3D = (ScalarDisplayer) scenes3D.getDisplayerManager().getDisplayer("Pressure");
        pressure3D.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-500, 200}));

        // pressure3D.setVisTransform(simulation_0.getTransformManager().getObject("Freestream.Symmetry Plane 1"));

        PlaneSection planeSection =
                ((PlaneSection) simulation_0.getPartManager().getObject("Plane Section"));

        Region subtractReg = simulation_0.getRegionManager().getRegion("Subtract");
        Region radReg = simulation_0.getRegionManager().getRegion("Radiator");
        planeSection.getInputParts().setObjects(radReg, subtractReg);

        Collection<Boundary> allBounds = subtractReg.getBoundaryManager().getBoundaries();
        allBounds.addAll(radReg.getBoundaryManager().getBoundaries());

        pressure3D.getInputParts().setObjects();
        String sep = File.separator;
        String Directory = simulation_0.getSessionDir() + sep + simulation_0.getPresentationName() + sep + "UT Scenes";
        new File(resolvePath(Directory)).mkdir();

        pressure2D.setRepresentation(volMesh);
        pressure3D.setRepresentation(volMesh);
        velocityVectors.setRepresentation(volMesh);

        pressure3D.getInputParts().setObjects();


        for (Boundary prt : allBounds)
        {
            String prtName = prt.getPresentationName();
            if (prtName.startsWith("UT", 54))
                pressure3D.getInputParts().addPart(prt);
        }

        Coordinate normalCoord =
                planeSection.getOrientationCoordinate();

        Coordinate originCoords =
                planeSection.getOriginCoordinate();

        Units inches =
                ((Units) simulation_0.getUnitsManager().getObject("in"));

        Units meters =
                ((Units) simulation_0.getUnitsManager().getObject("m"));

        VisView ut_rear =
                ((VisView) simulation_0.getViewManager().getObject("UT Rear"));

        VisView ut_bottom =
                ((VisView) simulation_0.getViewManager().getObject("UT Bottom"));

        VisView ut_profile =
                ((VisView) simulation_0.getViewManager().getObject("UT Profile"));

        DoubleVector profile = new DoubleVector(new double[] {0, 1, 0});
        DoubleVector rear = new DoubleVector(new double[] {0, 0, 1});
        DoubleVector top = new DoubleVector(new double[] {1, 0, 0});
        DoubleVector origin;
        String fileName;

        normalCoord.setCoordinate(inches, inches, inches, profile);

        planeSections.setCurrentView(ut_profile);

        Collection<Displayer> allDisplayers = planeSections.getDisplayerManager().getObjects();


        for (Displayer disp : allDisplayers) {
            disp.setVisibilityOverrideMode(2);

            if (disp.getPresentationName().startsWith("Outline"))
                disp.toggleVisibility();
        }

        velocityVectors.toggleVisibility();
        velocityVectors.getVectorDisplayQuantity().setRange(new DoubleVector(new double[] {0.0, 30.0}));
        velocityVectors.setDisplayMode(0);

        for (int i = 0; i <= 20; i = i + 2)
        {
            origin = new DoubleVector(new double[] {0, i, 0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Profile_Velocity_Glyph_" + i + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        velocityVectors.setDisplayMode(1);

        for (int i = 0; i <= 20; i = i + 2)
        {
            origin = new DoubleVector(new double[] {0, i, 0.0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Profile_Velocity_LIC_" + i + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        velocityVectors.toggleVisibility();
        pressure2D.toggleVisibility();

        for (int i = 0; i <= 20; i = i + 2)
        {
            origin = new DoubleVector(new double[] {0, i, 0.0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Profile_Pressure" + i + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        pressure2D.toggleVisibility();

        Collection<Displayer> allDisplayers_1 = scenes3D.getDisplayerManager().getObjects();


        for (Displayer disp : allDisplayers_1) {
            disp.setVisibilityOverrideMode(2);

            if (disp.getPresentationName().startsWith("Pressure"))
                disp.toggleVisibility();
        }

        scenes3D.setCurrentView(ut_bottom);
        fileName = Directory + sep +  "UT_Bottom_Pressure.png";
        scenes3D.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);


        normalCoord.setCoordinate(inches, inches, inches, top);
        planeSections.setCurrentView(ut_bottom);

        velocityVectors.toggleVisibility();
        velocityVectors.setDisplayMode(0);

        for (double i = 0; i<=2; i = i + 0.25)
        {
            origin = new DoubleVector(new double[] {i, 0, 0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Bottom_Velocity_Glyph_" + (i * 100) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        velocityVectors.setDisplayMode(1);

        for (double i = 0; i<=2; i = i + 0.25)
        {
            origin = new DoubleVector(new double[] {i, 0, 0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Bottom_Velocity_LIC_" + (i * 100) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        velocityVectors.toggleVisibility();
        pressure2D.toggleVisibility();

        for (double i = 0; i<=2; i = i + 0.25)
        {
            origin = new DoubleVector(new double[] {i, 0, 0});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Bottom_Pressure" + (i * 100) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        pressure2D.toggleVisibility();
        velocityVectors.toggleVisibility();

        normalCoord.setCoordinate(inches, inches, inches, rear);
        planeSections.setCurrentView(ut_rear);

        vectorGlyph.setRelativeToModelLength(1);
        velocityVectors.setDisplayMode(0);

        /*

        for (int i = -70; i <= 50; i += 4)
        {
            origin = new DoubleVector(new double[] {0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Rear_Velocity_Glyph_" + (i + 70) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        velocityVectors.setDisplayMode(1);

        for (int i = -70; i <= 50; i += 4)
        {
            origin = new DoubleVector(new double[] {0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Rear_Velocity_LIC_" + (i + 70) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }

        pressure2D.toggleVisibility();
        velocityVectors.toggleVisibility();

        for (int i = -70; i <= 50; i += 4)
        {
            origin = new DoubleVector(new double[] {0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep +  "UT_Rear_Pressure_" + (i + 70) + ".png";
            planeSections.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            simulation_0.println(fileName);
        }
        */
    }
}
