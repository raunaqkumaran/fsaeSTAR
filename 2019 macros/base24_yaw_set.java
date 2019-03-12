//STAR-CCM+ Macro for BASESIM_4-1.
//Executes all macros required for simulation set-up, execution, periodic saves, and exports reports and scenes

import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.*;

import java.util.Collection;

public class base24_yaw_set extends StarMacro {

    public void execute()
    {
        setPitch();
    }

    private void setPitch()
    {
        Simulation sim = getActiveSimulation();
        Double yaw = Math.toRadians(promptUserForInput("Enter yaw value in degrees", 0.0));
        Collection<GeometryPart> allParts = sim.get(SimulationPartManager.class).getParts();
        Collection<GeometryPart> allParts2 = sim.get(SimulationPartManager.class).getParts();
        allParts.clear();
        LabCoordinateSystem labCoord = sim.getCoordinateSystemManager().getLabCoordinateSystem();
        String prtName;
        for (GeometryPart prt : allParts2)
        {
            prtName = prt.getPresentationName();
            if (prtName.startsWith("EC") || prtName.startsWith("CFD") || prtName.startsWith("RW") || prtName.startsWith("FW") || prtName.startsWith("NS") || prtName.startsWith("SW") || prtName.startsWith("MOUNT") || prtName.startsWith("UT") || prtName.startsWith("Front") || prtName.startsWith("Rear"))
            {
                allParts.add(prt);
                sim.println(prtName);
            }
        }
        Units noUnit = sim.getUnitsManager().getUnits("");
        sim.get(SimulationPartManager.class).rotateParts(allParts, new DoubleVector(new double[] {1, 0, 0}), new NeoObjectVector(new Object[] {noUnit, noUnit, noUnit}), yaw, labCoord);
        CartesianCoordinateSystem radCoord = (CartesianCoordinateSystem) labCoord.getLocalCoordinateSystemManager().getObject("Radiator Cartesian");
        radCoord.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(new NeoObjectVector(new Object[] {radCoord}), new DoubleVector( new double[] {1, 0, 0}), new NeoObjectVector( new Object[] {noUnit, noUnit, noUnit}), yaw, labCoord);
        CylindricalCoordinateSystem frontWheel = (CylindricalCoordinateSystem) labCoord.getLocalCoordinateSystemManager().getObject("Front Wheel Cylindrical");
        frontWheel.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(new NeoObjectVector(new Object[] {frontWheel}), new DoubleVector( new double[] {1, 0, 0}), new NeoObjectVector( new Object[] {noUnit, noUnit, noUnit}), yaw, labCoord);
        CylindricalCoordinateSystem rearWheel = (CylindricalCoordinateSystem) labCoord.getLocalCoordinateSystemManager().getObject("Rear Wheel Cylindrical");
        rearWheel.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(new NeoObjectVector(new Object[] {rearWheel}), new DoubleVector( new double[] {1, 0, 0}), new NeoObjectVector( new Object[] {noUnit, noUnit, noUnit}), yaw, labCoord);
    }
}
