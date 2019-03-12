// Distributes parts to appropriate mesh controls. Executes mesher.
package macro;

import java.util.*;

import star.cadmodeler.*;
import star.common.*;
import star.base.neo.*;
import star.meshing.*;
import java.lang.String;

public class base7_automated_mesh extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    AutoMeshOperation automatedMesh = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

    SolidModelPart solidModelPart_7 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("CFD_RADIATOR"));

    MeshOperationPart subtractPart = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Subtract"));

    automatedMesh.getInputGeometryObjects().setObjects(solidModelPart_7, subtractPart);

    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
      ((SurfaceCustomMeshControl) automatedMesh.getCustomMeshControls().getObject("Surface Control Freestream"));

    PartSurface partSurface_7 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Inlet Plane"));

    PartSurface partSurface_8 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Left Plane"));

    PartSurface partSurface_9 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Outlet Plane"));

    PartSurface partSurface_10 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Symmetry Plane"));

    PartSurface partSurface_11 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Top Plane"));

    surfaceCustomMeshControl_1.getGeometryObjects().setObjects(partSurface_7, partSurface_8, partSurface_9, partSurface_10, partSurface_11);

    SurfaceCustomMeshControl surfaceCustomMeshControl_2 = 
      ((SurfaceCustomMeshControl) automatedMesh.getCustomMeshControls().getObject("Surface Control Ground"));

    PartSurface partSurface_12 = 
      ((PartSurface) subtractPart.getPartSurfaceManager().getPartSurface("Freestream.Ground Plane"));

    surfaceCustomMeshControl_2.getGeometryObjects().setObjects(partSurface_12);

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      ((SurfaceCustomMeshControl) automatedMesh.getCustomMeshControls().getObject("Surface Control Wings"));

    surfaceCustomMeshControl_0.getGeometryObjects().setObjects();

   Collection<GeometryPart> wings = subtractPart.getPartManager().getParts();

   for (GeometryPart prt : wings)
   {
     String prtName = prt.getPresentationName();
     if (prtName.startsWith("FW") || prtName.startsWith("RW") || prtName.startsWith("SW") || prtName.startsWith("FC") || prtName.startsWith("UT"))
       subtractPart.getPartManager().getPart(prtName);
   }

   Collection<PartSurface> surfaceControl = subtractPart.getPartSurfaceManager().getPartSurfaces();

   for (PartSurface surf : surfaceControl)
   {
     String surfName = surf.getPresentationName();
     if (surfName.startsWith("FW", 54) || surfName.startsWith("RW", 54) || surfName.startsWith("SW", 54) || surfName.startsWith("FC", 54) || surfName.startsWith("UT", 54))
       surfaceCustomMeshControl_0.getGeometryObjects().add(surf);
   }

   VolumeCustomMeshControl car = ((VolumeCustomMeshControl) automatedMesh.getCustomMeshControls().getObject("Volumetric Control Car"));
   VolumeCustomMeshControl wake = ((VolumeCustomMeshControl) automatedMesh.getCustomMeshControls().getObject("Volumetric Control Wake"));
   car.setEnableControl(true);
   wake.setEnableControl(true);
   car.getGeometryObjects().setObjects(simulation_0.get(SimulationPartManager.class).getPart("Volumetric Control Car"));
   wake.getGeometryObjects().setObjects(simulation_0.get(SimulationPartManager.class).getPart("Volumetric Control Wake"));

   automatedMesh.execute();
  }
}
