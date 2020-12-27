import star.common.StarMacro;

public class ClearHistory extends StarMacro {

    public void execute() {

        SimComponents simObj = new SimComponents(getActiveSimulation());
        simObj.clearHistory();

    }
}
