import star.common.GeometryPart;
import star.common.Simulation;
import star.common.StarMacro;
import star.meshing.MesherParallelModeOption;
import star.meshing.SurfaceCustomMeshControl;
import star.surfacewrapper.SurfaceWrapperAutoMeshOperation;


public class SurfaceWrap extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        // Instantiate SimComponents object
        Simulation simFile = getActiveSimulation();
        SimComponents simObject = new SimComponents(simFile);
        simObject.removeAllRegions();
        simObject = new SimComponents(simFile);


        // Set up controls. Unlike AutoMesh which depends on the user to define which controls are enabled. SurfaceWrap automatically enables everything.

        surfaceWrapSetup(simObject, simObject.surfaceWrapOperationPPM, simObject.aeroSurfaceWrapperPPM);

        simObject.surfaceWrapOperationPPM.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.CONCURRENT);
        simObject.surfaceWrapOperationPPM.setMeshPartByPart(true);
        //simObject.surfaceWrapOperationPPM.getInputGeometryObjects().removePart(simObject.radPart);
        //if (simObject.dualRadFlag) simObject.surfaceWrapOperationPPM.getInputGeometryObjects().removePart(simObject.dualRadPart);
        simObject.surfaceWrapOperationPPM.getInputGeometryObjects().setObjects();
        simObject.surfaceWrapOperationPPM.execute();

        surfaceWrapSetup(simObject, simObject.surfaceWrapOperation, simObject.aeroSurfaceWrapper);

        simObject.surfaceWrapOperation.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.SERIAL);
        simObject.surfaceWrapOperation.setMeshPartByPart(false);
        simObject.surfaceWrapOperation.execute();
        simObject.saveSim();

    }

    private void surfaceWrapSetup(SimComponents simObject, SurfaceWrapperAutoMeshOperation sWrap, SurfaceCustomMeshControl aeroSurface) {
        sWrap.getInputGeometryObjects().setObjects(simObject.nonAeroParts);
        sWrap.getInputGeometryObjects().addObjects(simObject.wheels);
        sWrap.getInputGeometryObjects().addObjects(simObject.aeroParts);
        aeroSurface.getGeometryObjects().setObjects(simObject.aeroParts);
        aeroSurface.setEnableControl(true);
        for (GeometryPart x : simObject.aeroParts)
        {
            aeroSurface.getGeometryObjects().addObjects(x.getPartSurfaces());
        }
    }

}
