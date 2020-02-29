import star.common.StarMacro;

public class softRun extends StarMacro
{
    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        sim.activeSim.getSimulationIterator().step(1);

        return;
    }
}
