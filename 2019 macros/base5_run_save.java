
import java.io.File;
import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base5_run_save extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation base1_5 = getActiveSimulation();

    Solution sol = base1_5.getSolution();
    sol.clearSolution(Solution.Clear.History);

    base1_5.getPlotManager().getPlot("Residuals").open();

    base1_5.getSimulationIterator().run();

    String dir = base1_5.getSessionDir();
    String sep = File.separator;
    String filename = base1_5.getPresentationName();

    base1_5.saveState(dir + sep + filename + ".sim");

    base1_5.close(ServerConnection.CloseOption.Close);
  }
}
