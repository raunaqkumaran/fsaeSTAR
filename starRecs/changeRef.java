// STAR-CCM+ macro: changeRef.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.flow.*;

public class changeRef extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    ForceCoefficientReport forceCoefficientReport_0 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("Drag Coefficient"));

    forceCoefficientReport_0.getReferenceVelocity().setValue(500.0);
  }
}
