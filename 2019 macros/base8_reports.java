// STAR-CCM+ macro: base1_reports.java
// Written by Raunaq Kumaran
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.flow.*;
import java.lang.String;

public class base8_reports extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 =
            getActiveSimulation();

    ForceCoefficientReport ecDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("EC Drag"));
    ecDrag.getParts().setObjects();
    ForceCoefficientReport ecLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("EC Lift"));
    ecLift.getParts().setObjects();

    ForceCoefficientReport rwDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("RW Drag"));
    rwDrag.getParts().setObjects();
    ForceCoefficientReport rwLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("RW Lift"));
    rwLift.getParts().setObjects();

    ForceCoefficientReport fwDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("FW Drag"));
    fwDrag.getParts().setObjects();
    ForceCoefficientReport fwLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("FW Lift"));
    fwLift.getParts().setObjects();

    ForceCoefficientReport utDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("UT Drag"));
    utDrag.getParts().setObjects();
    ForceCoefficientReport utLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("UT Lift"));
    utLift.getParts().setObjects();

    ForceCoefficientReport swDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("SW Drag"));
    rwDrag.getParts().setObjects();
    ForceCoefficientReport swLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("SW Lift"));
    rwLift.getParts().setObjects();

    ForceCoefficientReport nsDrag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("NS Drag"));
    nsDrag.getParts().setObjects();
    ForceCoefficientReport nsLift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("NS Lift"));
    nsLift.getParts().setObjects();

    ForceCoefficientReport lift = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("Lift Coefficient"));
    ForceCoefficientReport drag = ((ForceCoefficientReport) simulation_0.getReportManager().getReport("Drag Coefficient"));
    MomentCoefficientReport pitch = ((MomentCoefficientReport) simulation_0.getReportManager().getReport("Pitch Moment Coefficient"));
    MassFlowReport radMassFlow = ((MassFlowReport) simulation_0.getReportManager().getReport("Radiator Mass Flow"));

    Region subtract = simulation_0.getRegionManager().getRegion("Subtract");

    Collection<Boundary> subtractBoundaries = subtract.getBoundaryManager().getBoundaries();

    Collection<Boundary> domainSurfaces = subtract.getBoundaryManager().getBoundaries();
    domainSurfaces.clear();
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Ground Plane"));
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Inlet Plane"));
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Symmetry Plane"));
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Outlet Plane"));
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Top Plane"));
    domainSurfaces.add(subtract.getBoundaryManager().getBoundary("Freestream.Left Plane"));

    lift.getParts().setObjects(subtractBoundaries);
    lift.getParts().removeParts(domainSurfaces);

    drag.getParts().setObjects(subtractBoundaries);
    drag.getParts().removeParts(domainSurfaces);

    pitch.getParts().setObjects(subtractBoundaries);
    pitch.getParts().removeParts(domainSurfaces);

    for (Boundary bound : subtractBoundaries)
    {
      String boundName = bound.getPresentationName();
      if (boundName.startsWith("RW", 54))
      {
        rwDrag.getParts().add(bound);
        rwLift.getParts().add(bound);
      }
      else if (boundName.startsWith("FW", 54))
      {
        fwDrag.getParts().add(bound);
        fwLift.getParts().add(bound);
      }
      else if (boundName.startsWith("SW", 54))
      {
        swDrag.getParts().add(bound);
        swLift.getParts().add(bound);
      }
      else if (boundName.startsWith("UT", 54))
      {
        utDrag.getParts().add(bound);
        utLift.getParts().add(bound);
      }
      else if (boundName.startsWith("EC", 54))
      {
        ecDrag.getParts().add(bound);
        ecLift.getParts().add(bound);
      }
      else if (boundName.startsWith("NS", 54) || boundName.startsWith("NC", 54))
      {
        nsDrag.getParts().add(bound);
        nsLift.getParts().add(bound);
      }
      else if (boundName.startsWith("CFD_RADIATOR.Inlet", 74))
      {
        radMassFlow.getParts().setObjects(subtract.getBoundaryManager().getBoundary(boundName));
      }
    }

  }
}
