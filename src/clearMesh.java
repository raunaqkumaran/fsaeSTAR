import star.common.StarMacro;

public class clearMesh extends StarMacro
{
    public void execute()
    {
        SimComponents activeSim = new SimComponents(getActiveSimulation());
        activeSim.clearMesh();
    }
}
