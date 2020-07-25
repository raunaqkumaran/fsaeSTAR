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

        // Instantiate simComponents object
        Simulation simFile = getActiveSimulation();
        simComponents simObject = new simComponents(simFile);


        // Set up controls. Unlike autoMesh which depends on the user to define which controls are enabled. surfaceWrap automatically enables everything.

        simObject.surfaceWrapOperation.getInputGeometryObjects().setObjects(simObject.nonAeroParts);
        simObject.surfaceWrapOperation.getInputGeometryObjects().addObjects(simObject.wheels);
        simObject.surfaceWrapOperation.getInputGeometryObjects().addObjects(simObject.aeroParts);
        simObject.aeroSurfaceWrapper.getGeometryObjects().setObjects(simObject.aeroParts);
        simObject.aeroSurfaceWrapper.setEnableControl(true);
        for (GeometryPart x : simObject.aeroParts)
        {
            simObject.aeroSurfaceWrapper.getGeometryObjects().addObjects(x.getPartSurfaces());
        }


        simObject.surfaceWrapOperation.execute();


    }

}
