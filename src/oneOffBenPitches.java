// STAR-CCM+ macro: oneOffBenPitches.java
// Written by STAR-CCM+ 14.06.013

import java.io.File;

import star.common.*;
import star.base.report.*;
import star.flow.*;

public class oneOffBenPitches extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    MomentCoefficientReport momentCoefficientReport_0 = 
      ((MomentCoefficientReport) simulation_0.getReportManager().getReport("Pitch Moment Coefficient"));

    momentCoefficientReport_0.getOrigin().setComponents(0.0, 0.0, 0.35);

    momentCoefficientReport_0.getDirection().setComponents(1.0, 0.0, 0.0);

    momentCoefficientReport_0.getReferenceRadius().setValue(1.0);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    momentCoefficientReport_0.getReferenceRadius().setUnits(units_1);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("in"));

    momentCoefficientReport_0.getOrigin().setUnits(units_0);

    momentCoefficientReport_0.getOrigin().setComponents(0.0, 0.0, 0.35);

    Report rep = momentCoefficientReport_0;
    rep.printReport(resolvePath(simulation_0.getSessionDir() + File.separator + rep.getPresentationName().replaceAll("[\\/]", "") + ".txt"), false);

  }
}
