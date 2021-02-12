import star.common.Simulation;
import star.common.Solution;
import star.common.StarMacro;

import java.io.File;

public class ClearSolution extends StarMacro {
    @Override
    public void execute() {

        Simulation sim = getActiveSimulation();
        sim.getSolution().clearSolution(Solution.Clear.Fields);
        sim.getSolution().clearSolution(Solution.Clear.History);
        sim.saveState(sim.getSessionDir() + File.separator + sim.getPresentationName() + ".sim");

    }
}
