// Simcenter STAR-CCM+ macro: set_rotating_inlet.java
// Written by Simcenter STAR-CCM+ 15.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;
import star.motion.*;

public class set_rotating_inlet extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("In");

    boundary_0.getConditions().get(ReferenceFrameOption.class).setSelected(ReferenceFrameOption.Type.LAB_FRAME);

    boundary_0.getConditions().get(ReferenceFrameOption.class).setSelected(ReferenceFrameOption.Type.LOCAL_FRAME);

    BoundaryReferenceFrameSpecification boundaryReferenceFrameSpecification_0 = 
      boundary_0.getValues().get(BoundaryReferenceFrameSpecification.class);

    RotatingReferenceFrame rotatingReferenceFrame_0 = 
      ((RotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("ReferenceFrame for Rotation"));

    boundaryReferenceFrameSpecification_0.setReferenceFrame(rotatingReferenceFrame_0);

    UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Rotating"));

    boundaryReferenceFrameSpecification_0.setReferenceFrame(userRotatingReferenceFrame_0);

    RelativeVelocityMagnitudeProfile relativeVelocityMagnitudeProfile_0 = 
      boundary_0.getValues().get(RelativeVelocityMagnitudeProfile.class);

    relativeVelocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(1.0);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m/s"));

    relativeVelocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_0);
  }
}
