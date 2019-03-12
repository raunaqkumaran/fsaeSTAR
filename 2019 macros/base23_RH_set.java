//STAR-CCM+ Macro for BASESIM_4-1.
//Executes all macros required for simulation set-up, execution, periodic saves, and exports reports and scenes

import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.*;

import java.util.Collection;
import java.util.List;

public class base23_RH_set extends StarMacro {

  public void execute()
  {
    setPitch();
  }

  private void setPitch()
  {
    Simulation baseSim = getActiveSimulation();
    Double rotate = promptUserForInput("Enter translation in inches", 0.0);
    LabCoordinateSystem labCoord = baseSim.getCoordinateSystemManager().getLabCoordinateSystem();
    Collection<GeometryPart> allParts = baseSim.get(SimulationPartManager.class).getParts();
    Collection<GeometryPart> allParts2 = baseSim.get(SimulationPartManager.class).getParts();
    allParts.clear();
    Units inches = baseSim.getUnitsManager().getUnits("in");
    for (GeometryPart prt : allParts2)
    {
      String prtName = prt.getPresentationName();
      if (prtName.startsWith("CFD") || prtName.startsWith("RW") || prtName.startsWith("FW") || prtName.startsWith("EC") || prtName.startsWith("UT") || prtName.startsWith("NS") || prtName.startsWith("MOUNT") || prtName.startsWith("SW"))
        allParts.add(prt);
    }
    for (GeometryPart prt : allParts)
    {
      String prtName = prt.getPresentationName();
      baseSim.println(prtName);
    }
    baseSim.get(SimulationPartManager.class).translateParts(allParts, new DoubleVector(new double[] {rotate, 0, 0}), new NeoObjectVector(new Object[] {inches, inches, inches}), labCoord);
    CartesianCoordinateSystem radCoord = (CartesianCoordinateSystem) labCoord.getLocalCoordinateSystemManager().getObject("Radiator Cartesian");
    radCoord.getLocalCoordinateSystemManager().translateLocalCoordinateSystems(new NeoObjectVector(new Object[] {radCoord}), new DoubleVector( new double[] {rotate, 0, 0}), new NeoObjectVector( new Object[] {inches, inches, inches}), labCoord);
    baseSim.println(radCoord.getPresentationName());
  }
}
