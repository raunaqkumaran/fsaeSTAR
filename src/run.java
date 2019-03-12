// You NEED to run these via batch

import star.common.Simulation;
import star.common.Solution;
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

    public void killSim(simComponents activeSim)
    {
        activeSim.activeSim.kill();
    }

    public void clearHist(simComponents activeSim)
    {
        activeSim.activeSim.getSolution().clearSolution(Solution.Clear.History);
    }

}
