import star.common.StarMacro;

public class ClearSolution extends StarMacro {
    @Override
    public void execute() {
        SimComponents activeSim = new SimComponents(getActiveSimulation());
        activeSim.clearSoln();
        activeSim.saveSim();
    }
}
