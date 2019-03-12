// STAR-CCM+ macro: blockResize.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;

public class blockResize extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Freestream"));

    Coordinate coordinate_0 = 
      simpleBlockPart_0.getCorner1();

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {55.0, 55.0, 55.0}));

    Coordinate coordinate_1 = 
      simpleBlockPart_0.getCorner2();

    coordinate_1.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {100.0, 100.0, 100.0}));
  }
}
