import star.common.StarMacro;

public class save extends StarMacro {

    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        sim.saveSim();
    }

}
