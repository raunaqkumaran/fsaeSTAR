// STAR-CCM+ macro: fan_interface.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class fan_interface extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan Interface"));

    boundaryInterface_0.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);

    boundaryInterface_0.getTopology().setSelected(InterfaceConfigurationOption.Type.IN_PLACE);

    FanCurveTable fanCurveTable_0 = 
      boundaryInterface_0.getValues().get(FanCurveTable.class);

    FanCurveTableLeaf fanCurveTableLeaf_0 = 
      fanCurveTable_0.getModelPartValue();

    fanCurveTableLeaf_0.setVolumeFlowTableP("\uFEFFm^3/s");

    fanCurveTableLeaf_0.setVolumeFlowTableP("dP");
  }
}
