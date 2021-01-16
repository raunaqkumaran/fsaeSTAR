import star.base.neo.NeoObjectVector;
import star.common.*;
import star.meshing.MeshOperationPart;

public class Subtract extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        SimComponents simObject = new SimComponents(getActiveSimulation());

        MeshOperationPart surfaceWrap = (MeshOperationPart)
                simObject.activeSim.get(SimulationPartManager.class).getObject("Surface wrapper");

        GeometryObjectGroup subtractManager = simObject.subtract.getInputGeometryObjects();
        GeometryPart domain;
        if (!simObject.corneringFlag)
            domain = simObject.domain;
        else
        {
            domain = simObject.domain_c;
            simObject.activeSim.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {domain, simObject.farWakePart}));
        }
        subtractManager.setObjects(domain);
        subtractManager.addObjects(surfaceWrap);

        simObject.subtract.getTargetPartManager().setObjects(domain);
        simObject.subtract.execute();
    }

}
