import star.common.GeometryPart;
import star.common.PartSurface;
import star.common.StarMacro;
import simComponents.*;

public class autoMesh extends StarMacro {

    public void execute()
    {
        execute0();
    }

    public void execute0()
    {
        // Enable controls

        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.volControlWake.getGeometryObjects().setObjects(activeSim.volumetricWake);
        activeSim.volControlWake.setEnableControl(true);
        activeSim.volControlCar.getGeometryObjects().setObjects(activeSim.volumetricCar);
        activeSim.volControlCar.setEnableControl(true);
        activeSim.wingControl.setEnableControl(true);
        activeSim.groundControl.setEnableControl(true);
        activeSim.freestreamControl.setEnableControl(true);


        // Populate controls
        activeSim.autoMesh.getInputGeometryObjects().setObjects(activeSim.radPart, activeSim.subtractPart, activeSim.dualRadPart);

        activeSim.wingControl.getGeometryObjects().setObjects();                // Clears current population

        for (PartSurface prt : activeSim.subtractPart.getPartSurfaceManager().getPartSurfaces())
        {
            for (String surfName : activeSim.liftGeneratorPrefixes)
            {
                if (prt.getPresentationName().contains(surfName) && !prt.getPresentationName().toLowerCase().contains("suspension"))
                {
                    activeSim.wingControl.getGeometryObjects().add(prt);
                }
            }
        }

        activeSim.groundControl.getGeometryObjects().setObjects();
        activeSim.freestreamControl.getGeometryObjects().setObjects();

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
        }

        activeSim.volControlWake.getGeometryObjects().setObjects(activeSim.volumetricWake);
        activeSim.volControlCar.getGeometryObjects().setObjects(activeSim.volumetricCar);

        activeSim.autoMesh.execute();

    }
}
