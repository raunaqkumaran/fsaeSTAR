// STAR-CCM+ macro: base1_reports.java

import java.util.*;
import java.io.File;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base12_continue_save extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation base1_5 = getActiveSimulation();

    base1_5.getPlotManager().getPlot("Residuals").open();

    base1_5.getSimulationIterator().run();

    String dir = base1_5.getSessionDir();
    String sep = File.separator;
    String filename = base1_5.getPresentationName();

    base1_5.saveState(dir + sep + filename + ".sim");

    base1_5.close(ServerConnection.CloseOption.Close);
  }
}
