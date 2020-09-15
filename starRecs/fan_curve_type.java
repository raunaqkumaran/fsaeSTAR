// STAR-CCM+ macro: fan_curve_type.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.flow.*;

public class fan_curve_type extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan Interface"));

    InterfaceFanCurveSpecification interfaceFanCurveSpecification_0 = 
      boundaryInterface_0.getConditions().get(InterfaceFanCurveSpecification.class);

    interfaceFanCurveSpecification_0.getFanCurveTypeOption().setSelected(FanCurveTypeOption.Type.POLYNOMIAL);

    interfaceFanCurveSpecification_0.getFanCurveTypeOption().setSelected(FanCurveTypeOption.Type.TABLE);
  }
}
