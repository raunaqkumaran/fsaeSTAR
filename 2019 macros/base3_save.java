// STAR-CCM+ macro for BASESIM_3-1
// Saves simulation (overwriting original file)

import java.io.File;
import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base3_save extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation base1_5 = getActiveSimulation();

    String dir = base1_5.getSessionDir();
    String sep = File.separator;
    String filename = base1_5.getPresentationName();

    base1_5.saveState(dir + sep + filename + ".sim");

  }
}
