// STAR-CCM+ macro: forceHistos.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class forceHistos extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    AccumulatedForceTable accumulatedForceTable_3 = 
      ((AccumulatedForceTable) simulation_0.getTableManager().getTable("RW Force HIstogram"));

    accumulatedForceTable_3.getParts().setQuery(null);

    accumulatedForceTable_3.getParts().setObjects();
  }
}
