// STAR-CCM+ macro: setInterfaceType.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;

public class setInterfaceType extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan Interface"));

    BaffleInterface baffleInterface_0 = 
      ((BaffleInterface) simulation_0.get(ConditionTypeManager.class).get(BaffleInterface.class));

    boundaryInterface_0.setInterfaceType(baffleInterface_0);

    FanInterface fanInterface_0 = 
      ((FanInterface) simulation_0.get(ConditionTypeManager.class).get(FanInterface.class));

    boundaryInterface_0.setInterfaceType(fanInterface_0);
  }
}
