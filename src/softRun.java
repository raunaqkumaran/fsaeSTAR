import star.common.StarMacro;
import simComponents.simComponents;

public class softRun extends StarMacro
{
    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        sim.activeSim.getSimulationIterator().step(1);

        return;
    }
}
