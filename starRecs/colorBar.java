// STAR-CCM+ macro: colorBar.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class colorBar extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("3D scenes");

    ScalarDisplayer scalarDisplayer_1 = 
      ((ScalarDisplayer) scene_0.getDisplayerManager().getDisplayer("Y+"));

    Legend legend_0 = 
      scalarDisplayer_1.getLegend();

    legend_0.setWidth(0.4);

    legend_0.setWidth(0.6);
  }
}
