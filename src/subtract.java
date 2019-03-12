import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.meshing.MeshOperationPart;
import star.meshing.SimpleBlockPart;
import simComponents.*;

public class subtract extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        simComponents simObject = new simComponents(getActiveSimulation());
        MeshOperationPart surfaceWrap = (MeshOperationPart)
                simObject.activeSim.get(SimulationPartManager.class).getObject(simObject.aeroWrapName);
        simObject.subtract.getInputGeometryObjects().setObjects(simObject.domain, surfaceWrap);
        simObject.subtract.setTargetPart(simObject.domain);
        simObject.subtract.execute();

        simObject.saveSim();
    }

}
