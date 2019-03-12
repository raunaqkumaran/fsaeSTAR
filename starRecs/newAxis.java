// STAR-CCM+ macro: newAxis.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class newAxis extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CartesianCoordinateSystem cartesianCoordinateSystem_0 =
            simulation_0.getCoordinateSystemManager().getLabCoordinateSystem().getLocalCoordinateSystemManager().createLocalCoordinateSystem(CartesianCoordinateSystem.class, "lol");

    cartesianCoordinateSystem_0.setBasis0(new DoubleVector(new double[] {1.0, 1.0, 1.0}));

    cartesianCoordinateSystem_0.setBasis1(new DoubleVector(new double[] {1.0, 4.0, 1.0}));

    //Coordinate coordinate_0 =
      //cartesianCoordinateSystem_0.getOrigin();

    Units units_0 = 
      (simulation_0.getUnitsManager().getObject("m"));

    cartesianCoordinateSystem_0.getOrigin().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 4.0, 1.0}));
  }
}
