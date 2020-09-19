// STAR-CCM+ macro: base23_mesh_settings.java
// Written by STAR-CCM+ 13.04.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.prismmesher.*;
import star.meshing.*;

public class base23_mesh_settings extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Double baseSize = 0.3;
    int prismLayers = 4;
    Double prismWallThickness = 0.002;
    Double prismTotalThickness = 0.016;
    Double wingPrismThickness = 0.005;

    Simulation simulation_0 = 
      getActiveSimulation();

    AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

    autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(baseSize);

    NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);

    IntegerValue integerValue_0 = 
      numPrismLayers_0.getNumLayersValue();

    integerValue_0.getQuantity().setValue(prismLayers);

    autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setValue(prismWallThickness);

    PrismThickness prismThickness_0 = 
      autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);

    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setValue(prismTotalThickness);

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      ((SurfaceCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("Surface Control Wings"));

    PrismThickness prismThickness_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);

    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setValue(wingPrismThickness);
  }
}
