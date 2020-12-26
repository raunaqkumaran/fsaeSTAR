// Simcenter STAR-CCM+ macro: setPropertyForTable.java
// Written by Simcenter STAR-CCM+ 15.06.008
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class setPropertyForTable extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    AccumulatedForceTable accumulatedForceTable_0 = 
      ((AccumulatedForceTable) simulation_0.getTableManager().getTable("FW Force Histogram"));

    AccumulatedForceHistogram accumulatedForceHistogram_0 = 
      ((AccumulatedForceHistogram) accumulatedForceTable_0.getHistogram());

    accumulatedForceHistogram_0.getBinDirection().setComponents(0.0, 0.0, 1.0);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject(""));

    accumulatedForceHistogram_0.getBinDirection().setUnits(units_0);

    accumulatedForceHistogram_0.getBinDirection().setComponents(0.0, 1.0, 0.0);

    accumulatedForceHistogram_0.getBinDirection().setUnits(units_0);
  }
}
