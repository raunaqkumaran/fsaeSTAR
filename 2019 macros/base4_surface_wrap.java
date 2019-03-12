// STAR-CCM+ Macro for BASESIM_4-1.
// Prepares parameters required for surface wrap and subtract. Executes surface wrap and subtract
package macro;

import java.util.*;

import star.cadmodeler.*;
import star.common.*;
import star.base.neo.*;
import star.surfacewrapper.*;
import star.meshing.*;
import java.lang.String;

public class base4_surface_wrap extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SurfaceWrapperAutoMeshOperation aeroWrap = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Aero wrap (Non-aero wrap, wings, mounts and bodywork)"));

    aeroWrap.getInputGeometryObjects().setObjects();
    Collection<GeometryPart> col = simulation_0.get(SimulationPartManager.class).getParts();

    MeshOperationPart meshOperationPart_0 =
            ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Non-aero wrap (Car)"));

    aeroWrap.getInputGeometryObjects().setObjects(meshOperationPart_0);

    //PartsOneGroupContactPreventionSet UT_SW = (PartsOneGroupContactPreventionSet) aeroWrap.getContactPreventionSet().getObject("SW UT contact prevention");
    //UT_SW.getPartSurfaceGroup().setObjects();
    //UT_SW.getPartGroup().setObjects();

    for (GeometryPart prt : col) {
      String partName = prt.getPresentationName();
      if (partName.startsWith("EC") || partName.startsWith("FW") || partName.startsWith("NS") || partName.startsWith("NC") || partName.startsWith("RW") || partName.startsWith("UT") || partName.startsWith("SW") || partName.startsWith("MOUNT") || partName.startsWith("FC"))  // FIX THIS SO THE SWAN NECK FILES START WITH SWAN
        aeroWrap.getInputGeometryObjects().add(prt);
    }


    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      ((SurfaceCustomMeshControl) aeroWrap.getCustomMeshControls().getObject("Wing Surface Control"));

    surfaceCustomMeshControl_0.getGeometryObjects().setObjects();

    for (GeometryPart prt : col) {
      String partName = prt.getPresentationName();
      if (partName.startsWith("FW")  || partName.startsWith("RW") || partName.startsWith("SW") || partName.startsWith("FC") || partName.startsWith("UT") || partName.startsWith("EC"))
      {
        surfaceCustomMeshControl_0.getGeometryObjects().add(prt);
        surfaceCustomMeshControl_0.getGeometryObjects().addObjects(prt.getPartSurfaces());

        //if (partName.startsWith("SW") || partName.startsWith("UT"))
        {
         // UT_SW.getPartSurfaceGroup().addObjects(prt.getPartSurfaces());
         // UT_SW.getPartGroup().add(prt);
        }
      }

    }


    SubtractPartsOperation subtractPartsOperation_0 = 
      ((SubtractPartsOperation) simulation_0.get(MeshOperationManager.class).getObject("Subtract"));

    MeshOperationPart meshOperationPart_gazillion =
            ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Aero wrap (Non-aero wrap, wings, mounts and bodywork)"));

    SimpleBlockPart simpleBlockPart_bazillion =
            ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Freestream"));

    subtractPartsOperation_0.getInputGeometryObjects().setObjects(meshOperationPart_gazillion, simpleBlockPart_bazillion);

    subtractPartsOperation_0.setTargetPart(simpleBlockPart_bazillion);

    subtractPartsOperation_0.execute();
  }
}
