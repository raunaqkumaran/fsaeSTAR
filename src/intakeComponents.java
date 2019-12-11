import star.common.*;
import star.meshing.AutoMeshOperation;
import star.meshing.MeshOperationManager;

import java.util.ArrayList;
import java.util.Collection;

class intakeComponents
{
    //Simulation declaration
    public final Simulation activeSim;

    //Physics objects
    private final String kwName = "K-w";
    private final PhysicsContinuum kwPhysics;

    //Intake part
    private final String intakeName = "INTAKE";
    public final GeometryPart intakePart;

    //Intake region
    public final Region intakeRegion;

    //Boundary names
    public final String runner1 = "RUNNER 1";
    public final String runner2 = "RUNNER 2";
    public final String runner3 = "RUNNER 3";
    public final String runner4 = "RUNNER 4";
    public final String inlet = "Inlet";
    public final String freeOutlet = "Free outlet";
    public final Collection<Boundary> regionBounds;
    private final Collection<Region> volRegions;

    //Meshers
    private final String autoMeshName = "Automated Mesh";
    public final AutoMeshOperation autoMesh;

    //Parameters
    final intakeConfig parameterObj = new intakeConfig();

    public intakeComponents(Simulation sim)
    {
        activeSim = sim;
        volRegions = sim.getRegionManager().getRegions();
        regionBounds = new ArrayList<>();

        if (!volRegions.isEmpty()) {
            for (Region reg : volRegions) {
                regionBounds.addAll(reg.getBoundaryManager().getBoundaries());
            }
        }

        intakePart = activeSim.getGeometryPartManager().getPart(intakeName);
        intakeRegion = activeSim.getRegionManager().getRegion(intakeName);
        kwPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum(kwName);
        autoMesh = (AutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(autoMeshName);
    }
}


