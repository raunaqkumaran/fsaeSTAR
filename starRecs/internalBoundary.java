

// STAR-CCM+ macro: internalBoundary.java
// Written by STAR-CCM+ 14.06.013
package macro;

import java.util.*;

import star.common.*;

public class internalBoundary extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    InterfaceBoundary interfaceBoundary_0 = 
      ((InterfaceBoundary) region_0.getBoundaryManager().getBoundary("Freestream.Symmetry Plane [Yaw interface]"));

    InternalBoundary internalBoundary_0 = 
      ((InternalBoundary) simulation_0.get(ConditionTypeManager.class).get(InternalBoundary.class));

    interfaceBoundary_0.setBoundaryType(internalBoundary_0);
  }
}
