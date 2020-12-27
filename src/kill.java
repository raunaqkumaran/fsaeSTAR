import star.common.StarMacro;

public class kill extends StarMacro {

    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        sim.killSim();
    }

}
