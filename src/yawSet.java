import star.base.neo.DoubleVector;
import star.common.*;
import simComponents.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class yawSet extends StarMacro {

    public void execute()
    {
        yawSetter();
    }

    public void yawSetter()
    {
        double yawAngle;
        simComponents sim = new simComponents(getActiveSimulation());

        yawAngle = Math.toRadians(simComponents.valEnv("yaw"));

        if (yawAngle!=0)
        {
            // This is one of those code blocks that makes me fucking hate STAR's API.
            // Why does this need to be so fucking ugly?

            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.nonAeroParts,
                    new DoubleVector(sim.topBottomDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    yawAngle, sim.labCoord);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.aeroParts,
                    new DoubleVector(sim.topBottomDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    yawAngle, sim.labCoord);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.wheels,
                    new DoubleVector(sim.topBottomDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    yawAngle, sim.labCoord);

            sim.frontWheelCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.frontWheelCoord)),
                            new DoubleVector(sim.topBottomDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), yawAngle, sim.labCoord);
            sim.rearWheelCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.rearWheelCoord)),
                            new DoubleVector(sim.topBottomDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), yawAngle, sim.labCoord);
            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.radiatorCoord)),
                            new DoubleVector(sim.topBottomDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), yawAngle, sim.labCoord);
            try {
                sim.rollAxis.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.rollAxis)),
                                new DoubleVector(sim.topBottomDirection),
                                new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), yawAngle, sim.labCoord);
            }
            catch (Exception e)
            {
                sim.activeSim.println(this.getClass().getName() + " Roll axis can't be rotated");
            }

            // Rotate refinement blocks

            sim.activeSim.get(SimulationPartManager.class).
                    rotateParts(Arrays.asList(sim.volumetricCar, sim.volumetricWake),
                            new DoubleVector(sim.topBottomDirection), Arrays.asList(sim.noUnit,
                                    sim.noUnit, sim.noUnit), yawAngle, sim.labCoord );

            sim.activeSim.println(this.getClass().getName() + " - Yaw change attampted");

        }
        sim.saveSim();
    }

    // Get the env flag for the yaw angle



}
