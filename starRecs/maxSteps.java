// STAR-CCM+ macro: maxSteps.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class maxSteps extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    StepStoppingCriterion stepStoppingCriterion_0 = 
      ((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));

    stepStoppingCriterion_0.setMaximumNumberSteps(1000);
  }
}
