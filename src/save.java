import star.common.StarMacro;

public class save extends StarMacro {

    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        sim.saveSim();
    }

}
