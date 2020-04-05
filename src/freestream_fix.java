// STAR-CCM+ macro: freestream_fix.java
// Written by STAR-CCM+ 15.02.007
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;

public class freestream_fix extends StarMacro {

  private void execute() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Freestream"));

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    simpleBlockPart_0.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-16.0, 0.0, 0.00889}));

  }

  public void saveSim() {
        String newName = System.getenv("newName");

        if (newName != null)
            activeSim.saveState(activeSim.getSessionDir() + File.separator + newName);
        else
            activeSim.saveState(activeSim.getSessionDir() + File.separator + activeSim.getPresentationName() + ".sim");
    }
}
