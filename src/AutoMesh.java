import star.common.PartSurface;
import star.common.StarMacro;

/*
This prepares the automated mesher, and runs it. Sets up custom controls, but does not define whether or not the control is enabled. Custom controls must be manually enabled in the sim file.
 */
public class AutoMesh extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {

        SimComponents activeSim = new SimComponents(getActiveSimulation());
        activeSim.activeSim.println("--- AUTOMESHING ---");

        activeSim.activeSim.println("Assigning surface controls");
        // Set input objects for auto mehser
        if (activeSim.dualRadFlag)
            activeSim.autoMesh.getInputGeometryObjects().setObjects(activeSim.radPart, activeSim.subtractPart, activeSim.dualRadPart);
        else
            activeSim.autoMesh.getInputGeometryObjects().setObjects(activeSim.radPart, activeSim.subtractPart);

        //Clear all existing control nodes. Want to make sure the macros start from a well-defined entry point.
        activeSim.groundControl.getGeometryObjects().setObjects();
        activeSim.freestreamControl.getGeometryObjects().setObjects();
        activeSim.bodyworkControl.getGeometryObjects().setObjects();
        activeSim.sideWingControl.getGeometryObjects().setObjects();
        activeSim.rearWingControl.getGeometryObjects().setObjects();
        activeSim.undertrayControl.getGeometryObjects().setObjects();
        activeSim.frontWingControl.getGeometryObjects().setObjects();
        activeSim.radiatorControlVolume.getGeometryObjects().setObjects();

        //It might make sense to move this over the SimComponents, but that's some refactoring that right now has limited benefit for non-zero work.
        //This iterates through all the surfaces in the subtract node, and assigns them to the correct surface mesh controls based on our naming convention. Try to avoid changing the naming convection if you can.

        for (PartSurface surf :  activeSim.subtractPart.getPartSurfaceManager().getPartSurfaces())
        {
            if (surf.getPresentationName().contains(activeSim.FREESTREAM_PREFIX))
            {
                if (surf.getPresentationName().contains("Ground"))
                    activeSim.groundControl.getGeometryObjects().add(surf);
                else
                    activeSim.freestreamControl.getGeometryObjects().add(surf);
            }

            if (surf.getPresentationName().contains("RW"))
                activeSim.rearWingControl.getGeometryObjects().add(surf);
            if (surf.getPresentationName().contains("FW"))
                activeSim.frontWingControl.getGeometryObjects().add(surf);
            if (surf.getPresentationName().contains("SW"))
                activeSim.sideWingControl.getGeometryObjects().add((surf));
            if (surf.getPresentationName().contains("NS") || surf.getPresentationName().contains("EC") || surf.getPresentationName().contains("CHASSIS"))
                activeSim.bodyworkControl.getGeometryObjects().add(surf);
            if (surf.getPresentationName().contains("UT"))
                activeSim.undertrayControl.getGeometryObjects().add(surf);
            //if (surf.getPresentationName().contains("RADIATOR"))
                //activeSim.radiatorControlSurface.getGeometryObjects().add(surf);
        }

        //activeSim.radiatorControlSurface.getGeometryObjects().addObjects(activeSim.radPart.getPartSurfaces());
        //if (activeSim.dualRadFlag) activeSim.radiatorControlSurface.getGeometryObjects().addObjects(activeSim.dualRadPart.getPartSurfaces());

        //Assign block parts to the associated volume controls. Whether or not these are used is dependent on whether or not the nodes are enabled in the sim file.
        activeSim.activeSim.println("Assigning volumetric controls");
        activeSim.volControlWake.getGeometryObjects().setObjects(activeSim.volumetricWake);
        activeSim.volControlWingWake.getGeometryObjects().setObjects(activeSim.volumetricWingWake);
        activeSim.volControlFrontWing.getGeometryObjects().setObjects(activeSim.volumetricFrontWing);
        activeSim.volControlUnderbody.getGeometryObjects().setObjects(activeSim.volumetricUnderbody);
        activeSim.volControlRearWing.getGeometryObjects().setObjects(activeSim.volumetricRearWing);
        activeSim.volControlCar.getGeometryObjects().setObjects(activeSim.volumetricCar);
        activeSim.radiatorControlVolume.getGeometryObjects().setObjects(activeSim.radPart);
        if (activeSim.dualRadFlag) activeSim.radiatorControlVolume.getGeometryObjects().add(activeSim.dualRadPart);
        activeSim.farWakeControl.getGeometryObjects().setObjects(activeSim.farWakePart);

        //Don't need these next two lines. I like them though, so kept them.
        activeSim.autoMesh.setVerboseOutput(true);
        activeSim.clearHistory();

        activeSim.autoMesh.execute();
        activeSim.saveSim();
    }
}
