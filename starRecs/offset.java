// STAR-CCM+ macro: offset.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class offset extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("Plane Section"));

    SingleValue singleValue_0 = 
      planeSection_0.getSingleValue();

    singleValue_0.getValueQuantity().setValue(0.0);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    singleValue_0.getValueQuantity().setUnits(units_0);
  }
}
