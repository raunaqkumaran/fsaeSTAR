// Sweeps through RW 2D profile scenes in glyph velocity, line integral velocity, and pressure. Generates 3D pressure view at predefined RW views.
package macro;

import java.io.File;
import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import java.lang.String;

public class base12_rw_scene_sweep extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {

        Simulation baseSim = getActiveSimulation();
        PlaneSection section = (PlaneSection) baseSim.getPartManager().getObject("Plane Section");
        Scene sectionScene = baseSim.getSceneManager().getScene("Plane section scenes");
        Scene pressure = baseSim.getSceneManager().getScene("3D scenes");
        VectorDisplayer velVector = (VectorDisplayer) sectionScene.getDisplayerManager().getDisplayer("Velocity vector");
        // velVector.setVisTransform(baseSim.getTransformManager().getObject("Freestream.Symmetry Plane 1"));
        ScalarDisplayer pressure2D = (ScalarDisplayer) sectionScene.getDisplayerManager().getDisplayer("Pressure");
        // pressure2D.setVisTransform(baseSim.getTransformManager().getObject("Freestream.Symmetry Plane 1"));
        Collection<Displayer> allDisplayers = sectionScene.getDisplayerManager().getObjects();
        ScalarDisplayer pressure3D = (ScalarDisplayer) pressure.getDisplayerManager().getDisplayer("Pressure");
        Displayer outline3D = pressure.getDisplayerManager().getDisplayer("Outline 1");
        String sep = File.separator;
        String Directory = baseSim.getSessionDir() + sep + baseSim.getPresentationName() + sep + "RW Scenes";
        new File(resolvePath(Directory)).mkdir();
        FvRepresentation volMesh = (FvRepresentation) baseSim.getRepresentationManager().getObject("Volume Mesh");

        Region subtractRegion = baseSim.getRegionManager().getRegion("Subtract");
        Region radiatorRegion = baseSim.getRegionManager().getRegion("Radiator");

        Collection<Boundary> allParts = subtractRegion.getBoundaryManager().getBoundaries();
        allParts.addAll(radiatorRegion.getBoundaryManager().getBoundaries());

        pressure3D.getInputParts().setObjects();

        section.getInputParts().setObjects(subtractRegion, radiatorRegion);
        outline3D.getInputParts().setObjects(subtractRegion, radiatorRegion);
        pressure2D.setRepresentation(volMesh);
        pressure3D.setRepresentation(volMesh);
        velVector.setRepresentation(volMesh);

        for (Boundary prt : allParts)
        {
            String prtName = prt.getPresentationName();
            if (prtName.startsWith("RW", 54))
                pressure3D.getInputParts().addPart(prt);
        }


        for (Displayer disp : allDisplayers) {
            disp.setVisibilityOverrideMode(2);

            if (disp.getPresentationName().startsWith("Outline"))
                disp.toggleVisibility();
        }

        GlyphSettings vector = velVector.getGlyphSettings();
        String fileName;

        Units inches = baseSim.getUnitsManager().getObject("in");

        VisView rwRear = baseSim.getViewManager().getObject("RW Rear");
        VisView rwProfile = baseSim.getViewManager().getObject("RW Profile");
        VisView rwTop = baseSim.getViewManager().getObject("RW Top");
        VisView rwBottom = baseSim.getViewManager().getObject("RW Bottom");

        DoubleVector profile = new DoubleVector(new double[] {0, 1, 0});
        DoubleVector loc;

        Coordinate normal = section.getOrientationCoordinate();
        Coordinate origin = section.getOriginCoordinate();

        normal.setCoordinate(inches, inches, inches, profile);
        sectionScene.setCurrentView(rwProfile);
        vector.setRelativeToModelLength(0.1);

        velVector.toggleVisibility();
        velVector.getVectorDisplayQuantity().setRange(new DoubleVector(new double[] {0.0, 30.0}));
        velVector.setDisplayMode(0);
        for (int i = 0; i <= 20; i += 2)
        {
            fileName = Directory + sep + "RW_Velocity_Glyph_" + i + ".png";
            loc = new DoubleVector(new double[] {0, i, 0});
            origin.setCoordinate(inches, inches, inches, loc);
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }
        velVector.setDisplayMode(1);
        for (int i = 0; i <= 20; i += 2)
        {
            fileName = Directory + sep + "RW_Velocity_LIC_" + i + ".png";
            loc = new DoubleVector(new double[] {0, i, 0});
            origin.setCoordinate(inches, inches, inches, loc);
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }

        velVector.toggleVisibility();
        pressure2D.toggleVisibility();
        pressure2D.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-500, 200}));
        for (int i = 0; i <= 20; i += 2)
        {
            fileName = Directory + sep + "RW_Pressure_" + i + ".png";
            loc = new DoubleVector(new double[] {0, i, 0});
            origin.setCoordinate(inches, inches, inches, loc);
            sectionScene.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
            baseSim.println(fileName);
        }

        Collection<Displayer> allDisplayers1 = pressure.getDisplayerManager().getObjects();


        for (Displayer disp : allDisplayers1) {
            disp.setVisibilityOverrideMode(2);

            if (disp.getPresentationName().startsWith("Outline"))
                disp.toggleVisibility();
        }

        pressure3D.toggleVisibility();
        pressure3D.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-500, 200}));

        pressure.setCurrentView(rwRear);
        fileName = Directory + sep + "RW_Rear_Pressure.png";
        pressure.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);

        pressure.setCurrentView(rwTop);
        fileName = Directory + sep + "RW_Top_Pressure.png";
        pressure.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);

        pressure.setCurrentView(rwBottom);
        fileName = Directory + sep + "RW_Bottom_Pressure.png";
        pressure.printAndWait(resolvePath(fileName), 1, 4000, 2000, true, false);
        baseSim.println(fileName);

    }
}
