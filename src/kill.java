import star.common.StarMacro;

public class kill extends StarMacro {

    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        sim.saveSim();
        sim.killSim();
    }

}
