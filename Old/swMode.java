// STAR-CCM+ macro: swMode.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.meshing.*;
import star.surfacewrapper.*;

public class swMode extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Surface wrapper"));

    surfaceWrapperAutoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.SERIAL);
  }
}
