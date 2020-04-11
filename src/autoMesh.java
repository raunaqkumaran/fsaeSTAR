import star.common.PartSurface;
import star.common.StarMacro;

public class autoMesh extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        // Enable controls

        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.activeSim.println("--- AUTOMESHING ---");

        activeSim.activeSim.println("Assigning surface controls");
        // Populate controls
        if (activeSim.dualRadFlag)
            activeSim.autoMesh.getInputGeometryObjects().setObjects(activeSim.radPart, activeSim.subtractPart, activeSim.dualRadPart);
        else
            activeSim.autoMesh.getInputGeometryObjects().setObjects(activeSim.radPart, activeSim.subtractPart);

        activeSim.groundControl.getGeometryObjects().setObjects();
        activeSim.freestreamControl.getGeometryObjects().setObjects();
        activeSim.bodyworkControl.getGeometryObjects().setObjects();
        activeSim.sideWingControl.getGeometryObjects().setObjects();
        activeSim.rearWingControl.getGeometryObjects().setObjects();
        activeSim.undertrayControl.getGeometryObjects().setObjects();
        activeSim.frontWingControl.getGeometryObjects().setObjects();

        // There shouldn't be any reason to mess with the next few loops

        for (PartSurface surf :  activeSim.subtractPart.getPartSurfaceManager().getPartSurfaces())
        {
            if (surf.getPresentationName().contains(activeSim.freestreamPrefix))
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
            if (surf.getPresentationName().contains("RADIATOR"))
                activeSim.bodyworkControl.getGeometryObjects().add(surf);
        }

        activeSim.activeSim.println("Assigning volumetric controls");
        activeSim.volControlWake.getGeometryObjects().setObjects(activeSim.volumetricWake);
        activeSim.volControlWingWake.getGeometryObjects().setObjects(activeSim.volumetricWingWake);
        activeSim.volControlFrontWing.getGeometryObjects().setObjects(activeSim.volumetricFrontWing);
        activeSim.volControlUnderbody.getGeometryObjects().setObjects(activeSim.volumetricUnderbody);
        activeSim.volControlRearWing.getGeometryObjects().setObjects(activeSim.volumetricRearWing);
        activeSim.volControlCar.getGeometryObjects().setObjects(activeSim.volumetricCar, activeSim.radPart);
        if (activeSim.dualRadFlag) activeSim.volControlCar.getGeometryObjects().addObjects(activeSim.dualRadPart);
        activeSim.farWakeControl.getGeometryObjects().setObjects(activeSim.farWakePart);
        activeSim.autoMesh.setVerboseOutput(true);
        activeSim.autoMesh.execute();
        activeSim.clearHistory();
        activeSim.saveSim();
        exportScenes.exportMesh(activeSim);
    }
}
