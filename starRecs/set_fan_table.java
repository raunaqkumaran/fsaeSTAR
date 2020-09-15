// STAR-CCM+ macro: set_fan_table.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class set_fan_table extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan Interface"));

    FanCurveTable fanCurveTable_0 = 
      boundaryInterface_0.getValues().get(FanCurveTable.class);

    FanCurveTableLeaf fanCurveTableLeaf_0 = 
      fanCurveTable_0.getModelPartValue();

    AccumulatedForceTable accumulatedForceTable_0 = 
      ((AccumulatedForceTable) simulation_0.getTableManager().getTable("FW Force Histogram"));

    fanCurveTableLeaf_0.setVolumeFlowTable(accumulatedForceTable_0);

    FileTable fileTable_0 = 
      ((FileTable) simulation_0.getTableManager().getTable("fan_table_csv"));

    fanCurveTableLeaf_0.setVolumeFlowTable(fileTable_0);

    fanCurveTableLeaf_0.setVolumeFlowTableX("m^3/s");
  }
}
