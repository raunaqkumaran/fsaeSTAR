import star.common.StarMacro;

public class clearMesh extends StarMacro
{
    public void execute()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.clearMesh();
    }
}
