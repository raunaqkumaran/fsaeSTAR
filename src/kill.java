import simComponents.simComponents;
import star.common.StarMacro;

public class kill extends StarMacro {

    public void execute()
    {
        simComponents.killSim(getActiveSimulation());
    }

}
