// You NEED to run these via batch

import star.common.StarMacro;
import simComponents.*;

public class run extends StarMacro {


    public void execute() {
        execute0();
    }

    public void execute0()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.activeSim.getSimulationIterator().run();
        activeSim.saveSim();
    }
}
