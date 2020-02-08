import simComponents.simComponents;
import star.common.GeometryPart;
import star.common.Simulation;
import star.common.StarMacro;


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

        // Set up and run surface wrappers.
        // Each block adds appropriate parts and surfaces to the wrap.

        // I don't know why I need to do this, but it doesn't like it if I directly add the non-aero wrap to the aero wrap

        // Set up the aero wrap parts

        simObject.surfaceWrapOperation.getInputGeometryObjects().setObjects(simObject.nonAeroParts);
        simObject.surfaceWrapOperation.getInputGeometryObjects().addObjects(simObject.wheels);
        simObject.surfaceWrapOperation.getInputGeometryObjects().addObjects(simObject.aeroParts);
        simObject.aeroSurfaceWrapper.getGeometryObjects().setObjects(simObject.aeroParts);
        simObject.aeroSurfaceWrapper.setEnableControl(true);
        for (GeometryPart x : simObject.aeroParts)
        {
            simObject.aeroSurfaceWrapper.getGeometryObjects().addObjects(x.getPartSurfaces());
        }

        // Set up the wing custom control parts AND surfaces

        simObject.surfaceWrapOperation.execute();


    }

}
