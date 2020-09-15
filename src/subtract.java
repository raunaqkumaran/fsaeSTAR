import star.common.*;
import star.meshing.MeshOperationPart;

import java.util.Collection;

public class subtract extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        simComponents simObject = new simComponents(getActiveSimulation());

        MeshOperationPart surfaceWrap = (MeshOperationPart)
                simObject.activeSim.get(SimulationPartManager.class).getObject("Surface wrapper");

        GeometryObjectGroup subtractManager = simObject.subtract.getInputGeometryObjects();
        GeometryPart domain;
        if (!simObject.corneringFlag)
            domain = simObject.domain;
        else
            domain = simObject.domain_c;
        subtractManager.setObjects(domain);
        subtractManager.addObjects(surfaceWrap);

        simObject.subtract.getTargetPartManager().setObjects(domain);
        simObject.subtract.execute();
    }

}
