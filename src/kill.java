import simComponents.simComponents;
import star.common.StarMacro;

public class kill extends StarMacro {

    public void execute()
    {
        run obj = new run();
        obj.killSim(new simComponents(getActiveSimulation()));
    }

}
