import simComponents.simComponents;
import star.common.*;
import star.meshing.MeshOperationPart;

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
        subtractManager.setObjects(simObject.domain);
        subtractManager.addObjects(surfaceWrap);

        simObject.subtract.getTargetPartManager().addObjects(simObject.domain);
        simObject.subtract.execute();
    }

}
