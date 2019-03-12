// STAR-CCM+ macro: freestreamSet.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class freestreamSet extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

    VelocityProfile velocityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);

    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0.0, 0.0, 20.0);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Freestream.Inlet Plane");

    VelocityMagnitudeProfile velocityMagnitudeProfile_0 = 
      boundary_0.getValues().get(VelocityMagnitudeProfile.class);

    velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(20.0);

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Freestream.Ground Plane");

    WallRelativeVelocityProfile wallRelativeVelocityProfile_0 = 
      boundary_1.getValues().get(WallRelativeVelocityProfile.class);

    wallRelativeVelocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0.0, 0.0, 20.0);

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Front Left Wheel");

    WallRelativeRotationProfile wallRelativeRotationProfile_0 = 
      boundary_2.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(80.0);

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Rear Left Wheel");

    WallRelativeRotationProfile wallRelativeRotationProfile_1 = 
      boundary_3.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(80.0);
  }
}
