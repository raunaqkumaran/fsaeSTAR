// STAR-CCM+ macro: pickPhysics.java
// Written by STAR-CCM+ 14.02.012
package macro;

import java.util.*;

import star.common.*;

public class pickPhysics extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    physicsContinuum_0.erase(region_0);

    physicsContinuum_0.add(region_0);
  }
}
