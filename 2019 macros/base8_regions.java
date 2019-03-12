// STAR-CCM+ for base1_5.
// Creates new subtract and radiator region, applies boundary conditions and subtract-radiator interfaces.

import java.util.*;

import star.cadmodeler.*;
import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.flow.*;
import star.meshing.*;

public class base8_regions extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_5 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    simulation_0.getRegionManager().removeRegions(new NeoObjectVector(new Object[] {region_5}));

    Region region_4 = 
      simulation_0.getRegionManager().getRegion("Radiator");

    simulation_0.getRegionManager().removeRegions(new NeoObjectVector(new Object[] {region_4}));

    SolidModelPart solidModelPart_6 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("CFD_RADIATOR"));

    MeshOperationPart meshOperationPart_4 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Subtract"));

    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {solidModelPart_6, meshOperationPart_4}),
            "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve",
            null, RegionManager.CreateInterfaceMode.BOUNDARY);

    Region region_6 = 
      simulation_0.getRegionManager().getRegion("CFD_RADIATOR");

    region_6.setPresentationName("Radiator");

    Region region_7 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    Boundary boundary_0 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Ground Plane");

    boundary_0.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.VECTOR);

    WallRelativeVelocityProfile wallRelativeVelocityProfile_0 = 
      boundary_0.getValues().get(WallRelativeVelocityProfile.class);

    wallRelativeVelocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0, 0, 15);

    Boundary boundary_1 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Inlet Plane");

    boundary_1.setBoundaryType(InletBoundary.class);

    VelocityMagnitudeProfile velocityMagnitudeProfile_0 = 
      boundary_1.getValues().get(VelocityMagnitudeProfile.class);

    velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(15.0);

    Boundary boundary_2 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Left Plane");

    boundary_2.setBoundaryType(SymmetryBoundary.class);

    Boundary boundary_3 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Outlet Plane");

    boundary_3.setBoundaryType(PressureBoundary.class);

    Boundary boundary_4 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Symmetry Plane");

    boundary_4.setBoundaryType(SymmetryBoundary.class);

    Boundary boundary_5 = 
      region_7.getBoundaryManager().getBoundary("Freestream.Top Plane");

    boundary_5.setBoundaryType(SymmetryBoundary.class);

    Collection<Boundary> bounds = region_7.getBoundaryManager().getBoundaries();

    String frontLeft = "Aero wrap (Non-aero wrap, wings, mounts and bodywork).Non-aero wrap (Car).Front Left.Default";
    String rearLeft = "Aero wrap (Non-aero wrap, wings, mounts and bodywork).Non-aero wrap (Car).Rear Left.Default";

    for (Boundary check : bounds)
    {
      String boundName = check.getPresentationName();
      if (boundName.startsWith("Front Left", 74 ))
        frontLeft = boundName;
      else if (boundName.startsWith("Rear Left", 74))
        rearLeft = boundName;
    }

    Boundary boundary_6 = 
      region_7.getBoundaryManager().getBoundary(frontLeft);

    boundary_6.setPresentationName("Front Left Wheel");

    Boundary boundary_7 = 
      region_7.getBoundaryManager().getBoundary(rearLeft);

    boundary_7.setPresentationName("Rear Left Wheel");

    boundary_6.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_0 = 
      boundary_6.getValues().get(ReferenceFrame.class);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Front Wheel Cylindrical"));

    referenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);

    WallRelativeRotationProfile wallRelativeRotationProfile_0 = 
      boundary_6.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(65.617);

    boundary_7.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_1 = 
      boundary_7.getValues().get(ReferenceFrame.class);

    CylindricalCoordinateSystem cylindricalCoordinateSystem_1 = 
      ((CylindricalCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Rear Wheel Cylindrical"));

    referenceFrame_1.setCoordinateSystem(cylindricalCoordinateSystem_1);

    WallRelativeRotationProfile wallRelativeRotationProfile_1 = 
      boundary_7.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(65.617);

    Boundary boundary_8 = 
      region_6.getBoundaryManager().getBoundary("Inlet");

    String radiatorInlet = "Aero wrap (Non-aero wrap, wings, mounts and bodywork).Non-aero wrap (Car).CFD_RADIATOR.Inlet";
    String radiatorOutlet = "Aero wrap (Non-aero wrap, wings, mounts and bodywork).Non-aero wrap (Car).CFD_RADIATOR.Outlet";

    for (Boundary check : bounds)
    {
      String boundName = check.getPresentationName();
      if (boundName.startsWith("CFD_RADIATOR.Inlet", 74 ))
        radiatorInlet = boundName;
      else if (boundName.startsWith("CFD_RADIATOR.Outlet", 74))
        radiatorOutlet = boundName;
    }

    Boundary boundary_9 = 
      region_7.getBoundaryManager().getBoundary(radiatorInlet);

    BoundaryInterface boundaryInterface_0 = 
      simulation_0.getInterfaceManager().createBoundaryInterface(boundary_8, boundary_9, "Interface");

    Boundary boundary_10 = 
      region_6.getBoundaryManager().getBoundary("Outlet");

    Boundary boundary_11 = 
      region_7.getBoundaryManager().getBoundary(radiatorOutlet);

    BoundaryInterface boundaryInterface_1 = 
      simulation_0.getInterfaceManager().createBoundaryInterface(boundary_10, boundary_11, "Interface");

    region_6.setRegionType(PorousRegion.class);

    PorousInertialResistance porousInertialResistance_0 = 
      region_6.getValues().get(PorousInertialResistance.class);

    VectorProfile vectorProfile_0 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getYAxis();

    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator Cartesian"));

    vectorProfile_0.setCoordinateSystem(cartesianCoordinateSystem_0);

    VectorProfile vectorProfile_1 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getXAxis();

    vectorProfile_1.setCoordinateSystem(cartesianCoordinateSystem_0);

    ScalarProfile scalarProfile_0 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(0);

    scalarProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000);

    ScalarProfile scalarProfile_1 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(1);

    scalarProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(70);

    ScalarProfile scalarProfile_2 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(2);

    scalarProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000);
  }
}
