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


public class base22_car_sweep extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {

        Simulation baseSim = getActiveSimulation();
        PlaneSection section = (PlaneSection) baseSim.getPartManager().getObject("Plane Section");
        Scene sectionScene = baseSim.getSceneManager().getScene("Plane section scenes");
        Scene scenes3D = baseSim.getSceneManager().getScene("3D scenes");
        ScalarDisplayer wallY = (ScalarDisplayer) scenes3D.getDisplayerManager().getDisplayer("Y+");
        VectorDisplayer velVector = (VectorDisplayer) sectionScene.getDisplayerManager().getDisplayer("Velocity vector");
        // velVector.setVisTransform(baseSim.getTransformManager().getObject("Freestream.Symmetry Plane 1"));
        ScalarDisplayer pressure2D = (ScalarDisplayer) sectionScene.getDisplayerManager().getDisplayer("Pressure");
        // pressure2D.setVisTransform(baseSim.getTransformManager().getObject("Freestream.Symmetry Plane 1"));
        Collection<Displayer> allDisplayers = sectionScene.getDisplayerManager().getObjects();
        String sep = File.separator;
        String Directory = baseSim.getSessionDir() + sep + baseSim.getPresentationName() + sep + "Car Scenes";
        FvRepresentation volMesh = (FvRepresentation) baseSim.getRepresentationManager().getObject("Volume Mesh");

        Region subtractRegion = baseSim.getRegionManager().getRegion("Subtract");
        Region radiatorRegion = baseSim.getRegionManager().getRegion("Radiator");

        Collection<Boundary> allParts = subtractRegion.getBoundaryManager().getBoundaries();
        allParts.addAll(radiatorRegion.getBoundaryManager().getBoundaries());
        velVector.setRepresentation(volMesh);
        pressure2D.setRepresentation(volMesh);
        wallY.setRepresentation(volMesh);

        section.getInputParts().setObjects(subtractRegion, radiatorRegion);

        for (Displayer disp : allDisplayers) {
            disp.setVisibilityOverrideMode(2);

            if (disp.getPresentationName().startsWith("Outline"))
                disp.toggleVisibility();
        }

        Collection<Displayer> disp3D = scenes3D.getDisplayerManager().getObjects();

        for (Displayer disp : disp3D)
        {
            disp.setVisibilityOverrideMode(2);
        }

        DoubleVector origin;

        GlyphSettings vector = velVector.getGlyphSettings();
        String fileName;

        Units inches = baseSim.getUnitsManager().getObject("in");

        VisView carRear = baseSim.getViewManager().getObject("Car Rear");

        DoubleVector rear = new DoubleVector(new double[]{0, 0, 1});

        velVector.getVectorDisplayQuantity().setRange(new DoubleVector(new double[]{0.0, 30.0}));
        pressure2D.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-500, 200}));

        Coordinate normalCoord =
                section.getOrientationCoordinate();

        Coordinate originCoords =
                section.getOriginCoordinate();

        String name;

        wallY.getInputParts().setObjects();

        for (Boundary prt : allParts)
        {
            name = prt.getPresentationName();
            if (!name.startsWith("Freestream"))
            {
                wallY.getInputParts().add(prt);
            }
        }



        wallY.toggleVisibility();
        VisView carStd = baseSim.getViewManager().getObject("Car standard");
        scenes3D.setCurrentView(carStd);
        wallY.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0, 5}));
        fileName = Directory + sep + "Wall Y+ 0 5.png";
        scenes3D.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);
        wallY.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {5, 15}));
        fileName = Directory + sep + "Wall Y+ 5 15.png";
        scenes3D.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);
        wallY.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {30, 100}));
        fileName = Directory + sep + "Wall Y+ 30 100.png";
        scenes3D.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);

        normalCoord.setCoordinate(inches, inches, inches, rear);
        sectionScene.setCurrentView(carRear);

        vector.setRelativeToModelLength(1);
        velVector.toggleVisibility();
        velVector.setDisplayMode(0);

        for (int i = -70; i <= 50; i += 5) {
            origin = new DoubleVector(new double[]{0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep + "Car_Rear_Velocity_Glyph_" + (i + 70) + ".png";
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }

        velVector.setDisplayMode(1);

        for (int i = -70; i <= 50; i += 5) {
            origin = new DoubleVector(new double[]{0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep + "Car_Rear_Velocity_LIC_" + (i + 70) + ".png";
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }

        pressure2D.toggleVisibility();
        velVector.toggleVisibility();

        for (int i = -70; i <= 50; i += 5) {
            origin = new DoubleVector(new double[]{0, 0, i});
            originCoords.setCoordinate(inches, inches, inches, origin);
            fileName = Directory + sep + "Car_Rear_Pressure_" + (i + 70) + ".png";
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }


    }
}