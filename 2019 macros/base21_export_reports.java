// Exports all reports as a text file to a folder named reports in the same directory the macro is executed from
package macro;

import java.io.File;
import java.util.*;

import star.base.neo.DoubleVector;
import star.common.*;
import star.base.report.*;
import star.flow.*;
import star.vis.PointPart;

public class base21_export_reports extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation baseSim =
            getActiveSimulation();
    
    Collection<Report> reports = baseSim.getReportManager().getObjects();

    String currentDirectory = baseSim.getSessionDir();

    String sep = File.separator;
    String Directory = currentDirectory + sep + baseSim.getPresentationName() + sep + "Reports";
    new File(resolvePath(Directory)).mkdirs();

    String repName;
    
    for (Report rep : reports)
    {
      repName = rep.getPresentationName();

      if (!repName.equals("L/D")) {
        new File(resolvePath(Directory + sep + repName + ".txt"));
        rep.printReport(resolvePath(Directory + sep + repName + ".txt"), true);
      }
      else
      {
        new File(resolvePath(Directory + sep + "L_D.txt"));
        rep.printReport(resolvePath(Directory + sep + "L_D.txt"), true);
      }
    }

    PointPart point = (PointPart) baseSim.getPartManager().getObject("Point");

    point.getInputParts().setObjects();

    point.getPointCoordinate().setCoordinate(baseSim.getUnitsManager().getObject("in"),baseSim.getUnitsManager().getObject("in"),baseSim.getUnitsManager().getObject("in"), new DoubleVector(new double[] {1, 1, 1}));

    Region subtract = baseSim.getRegionManager().getRegion("Subtract");
    Region rad = baseSim.getRegionManager().getRegion("Radiator");

    Collection<Boundary> subtractBounda = subtract.getBoundaryManager().getBoundaries();
    Collection<Boundary> radBounds = rad.getBoundaryManager().getBoundaries();

    point.getInputParts().setObjects(subtractBounda);
    point.getInputParts().addParts(radBounds);


    XyzInternalTable reportTable = (XyzInternalTable) baseSim.getTableManager().getTable("Reports table");
    reportTable.setRepresentation(baseSim.getRepresentationManager().getObject("Volume Mesh"));
    reportTable.extract();
    reportTable.export(Directory + sep + "Reports.csv", ',');
  }
}
