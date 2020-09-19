// Clears monitor history. Begins simulation iterations. 

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base4_run extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation base1_5 = getActiveSimulation();

    Solution sol = base1_5.getSolution();
    sol.clearSolution(Solution.Clear.History);

    base1_5.getPlotManager().getPlot("Residuals").open();

    base1_5.getSimulationIterator().run();
  }
}
