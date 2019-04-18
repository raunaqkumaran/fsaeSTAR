import star.common.*;
import star.meshing.MeshOperationPart;
import star.meshing.SurfaceCustomMeshControl;
import simComponents.*;


public class surfaceWrap extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {

        // Instantiate simComponents.simComponents object
        Simulation simFile = getActiveSimulation();
        simComponents simObject = new simComponents(simFile);

        //Configuration flags - Eventually these will be argvs. Can expand further later.

        int runNonAero = 1;     // Run non aero surface wrap
        int runAero = 1;        // Run aero surface wrap

        // Set up  mesh controllers

        String wingControlName = "Wing Surface Control";
        SurfaceCustomMeshControl wingSurface =
                (SurfaceCustomMeshControl) simObject.aeroWrap.getCustomMeshControls().getObject(wingControlName);

        // Set up and run surface wrappers.
        // Each block adds appropriate parts and surfaces to the wrap.

        if (runNonAero == 1)
        {
            simObject.nonAeroWrap.getInputGeometryObjects().setObjects(simObject.nonAeroParts);
            simObject.nonAeroWrap.getInputGeometryObjects().addObjects(simObject.wheels);
            simObject.nonAeroWrap.execute();
        }

        if (runAero == 1)
        {
            // I don't know why I need to do this, but it doesn't like it if I directly add the non-aero wrap to the aero wrap
            MeshOperationPart nonAero = (MeshOperationPart)
                    simObject.activeSim.get(SimulationPartManager.class).getPart(simObject.nonAeroWrapName);

            // Set up the aero wrap parts

            simObject.aeroWrap.getInputGeometryObjects().setObjects(nonAero);
            simObject.aeroWrap.getInputGeometryObjects().addObjects(simObject.aeroParts);

            // Set up the wing custom control parts AND surfaces

            wingSurface.getGeometryObjects().addObjects(simObject.liftGenerators);
            for (GeometryPart prt: simObject.liftGenerators)
            {
                wingSurface.getGeometryObjects().addObjects(prt.getPartSurfaces());
            }

            simObject.aeroWrap.execute();
        }

    }

}
