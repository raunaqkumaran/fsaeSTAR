// Simcenter STAR-CCM+ macro: groundRotationAgain.java
// Written by Simcenter STAR-CCM+ 15.04.010
package macro;

import java.util.*;

import star.common.*;
import star.motion.*;

public class groundRotationAgain extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Freestream_C.Ground Plane");

    BoundaryReferenceFrameSpecification boundaryReferenceFrameSpecification_0 = 
      boundary_0.getValues().get(BoundaryReferenceFrameSpecification.class);

    UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Rotating"));

    boundaryReferenceFrameSpecification_0.setReferenceFrame(userRotatingReferenceFrame_0);
  }
}
