import star.common.StarMacro;
import simComponents.*;

public class clearMesh extends StarMacro
{
    public void execute()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.clearMesh();
    }
}
