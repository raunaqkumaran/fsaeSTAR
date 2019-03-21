// This is a fun one. THIS NEEDS TO BE RUN FROM A BATCH SCRIPT. There's probably a way to modify this so it doesn't
// need to run off of a batch script, but that's too much work.
// -frh = front ride height
// -rrh = rear ride height


import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.*;
import simComponents.*;

import java.util.Arrays;

public class rideHeight extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        double frontRot;
        double rearRot;
        double frh;
        double rrh;

        frh = simComponents.valEnv("frh");
        rrh = simComponents.valEnv("rrh");

        frontRot = Math.atan(rrh / sim.wheelBase);
        rearRot = -Math.atan(frh / sim.wheelBase);

        if (frontRot != 0) {
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.aeroParts, Arrays.asList(0, 0, 1),
                    Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), frontRot, sim.frontWheelCoord);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.nonAeroParts, Arrays.asList(0, 0, 1),
                    Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), frontRot, sim.frontWheelCoord);
            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(Arrays.asList(sim.radiatorCoord),
                            new DoubleVector(new double[]{0.0, 0.0, 1.0}),
                            new NeoObjectVector(new Units[]{sim.noUnit,
                                    sim.noUnit, sim.noUnit}), frontRot, sim.frontWheelCoord);
            try {
                sim.rollAxis.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(Arrays.asList(sim.rollAxis),
                                new DoubleVector(new double[]{0.0, 0.0, 1.0}),
                                new NeoObjectVector(new Units[]{sim.noUnit,
                                        sim.noUnit, sim.noUnit}), frontRot, sim.frontWheelCoord);
            }
            catch (Exception e)
            {
                sim.activeSim.println(this.getClass().getName() + " - No roll axis");
            }
        }

        if (rearRot != 0) {
            sim.activeSim.get(SimulationPartManager.class).
                    rotateParts(sim.aeroParts, Arrays.asList(0, 0, 1),
                            Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), rearRot, sim.rearWheelCoord);
            sim.activeSim.get(SimulationPartManager.class).
                    rotateParts(sim.nonAeroParts, Arrays.asList(0, 0, 1),
                            Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), rearRot, sim.rearWheelCoord);
            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(Arrays.asList(sim.radiatorCoord),
                            new DoubleVector(new double[]{0.0, 0.0, 1.0}),
                            new NeoObjectVector(new Units[]{sim.noUnit,
                                    sim.noUnit, sim.noUnit}), rearRot, sim.rearWheelCoord);
            try {
                sim.rollAxis.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(Arrays.asList(sim.rollAxis),
                                new DoubleVector(new double[]{0.0, 0.0, 1.0}),
                                new NeoObjectVector(new Units[]{sim.noUnit,
                                        sim.noUnit, sim.noUnit}), rearRot, sim.rearWheelCoord);
            }
            catch (Exception e)
            {
                sim.activeSim.println(this.getClass().getName() + " - No roll axis");
            }
        }
    }
}
