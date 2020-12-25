// Simcenter STAR-CCM+ macro: update_part.java
// Written by Simcenter STAR-CCM+ 15.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;

public class update_part extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Freestream_C"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));
  }
}
