// STAR-CCM+ macro: moreNoSave.java
// Written by STAR-CCM+ 14.06.013
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class moreNoSave extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    AutoSave autoSave_0 = 
      simulation_0.getSimulationIterator().getAutoSave();

    autoSave_0.setAutoSaveMesh(false);
  }
}
