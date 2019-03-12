import simComponents.simComponents;
import star.common.StarMacro;

/* TODO
Use the sys env to pick the new filename
 */
// DEPRECATED

public class save extends StarMacro {

    public void execute()
    {
        // Reusing the saveSim code from run.java
        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.saveSim();
    }
}
