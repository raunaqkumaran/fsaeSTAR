// Simcenter STAR-CCM+ macro: setTranslation.java
// Written by Simcenter STAR-CCM+ 15.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.motion.*;

public class setTranslation extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    UserRotatingAndTranslatingReferenceFrame userRotatingAndTranslatingReferenceFrame_0 = 
      ((UserRotatingAndTranslatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Rotating And Translating"));

    userRotatingAndTranslatingReferenceFrame_0.getTranslationVelocity().setComponents(-1.0, 0.0, 0.0);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m/s"));

    userRotatingAndTranslatingReferenceFrame_0.getTranslationVelocity().setUnits(units_0);
  }
}
