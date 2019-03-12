// Saves and closes simulation

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base5_close extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation basesim = getActiveSimulation();
    basesim.kill();
  }
}
