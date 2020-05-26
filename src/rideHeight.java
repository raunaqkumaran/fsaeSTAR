// This is a fun one. THIS NEEDS TO BE RUN FROM A BATCH SCRIPT. There's probably a way to modify this so it doesn't
// need to run off of a batch script, but that's too much work.
// -frh = front ride height
// -rrh = rear ride height


import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.common.Units;

import java.util.Arrays;
import java.util.Collections;

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

        frh = sim.valEnv("frh");
        rrh = sim.valEnv("rrh");

        if (frh == 0 && rrh == 0)
            return;

        //DEBUG
        /*
        frh = -5.5;
        rrh = -5.5;
        */
        frontRot = -Math.atan(rrh / sim.wheelBase);
        rearRot = Math.atan(frh / sim.wheelBase);

        sim.activeSim.println("Front, rear pitch angle change attempted " + frontRot + " " + rearRot);

        if (frontRot != 0) {
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.aeroParts,
                    new DoubleVector(new double[] {0, 0, 1}), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), frontRot, sim.frontWheelCoord);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.nonAeroParts, new DoubleVector(new double[] {0, 0, 1}),
                    Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), frontRot, sim.frontWheelCoord);
            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(Collections.singletonList(sim.radiatorCoord),
                            new DoubleVector(new double[] {0, 0, 1}),
                            new NeoObjectVector(new Units[]{sim.noUnit,
                                    sim.noUnit, sim.noUnit}), frontRot, sim.frontWheelCoord);
            if (sim.dualRadFlag)
            {
                try {
                    sim.dualRadCoord.getLocalCoordinateSystemManager().
                            rotateLocalCoordinateSystems(Collections.singletonList(sim.dualRadCoord),
                                    new DoubleVector(new double[] {0, 0, 1}),
                                    new NeoObjectVector(new Units[]{sim.noUnit,
                                            sim.noUnit, sim.noUnit}), frontRot, sim.frontWheelCoord);
                }
                catch (Exception e)
                {
                    sim.activeSim.println(this.getClass().getName() + "Dual rad rotation failure");
                }
            }
            try {
                sim.rollAxis.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(Collections.singletonList(sim.rollAxis),
                                new DoubleVector(new double[] {0, 0, 1}),
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
                    rotateParts(sim.aeroParts, new DoubleVector(new double[] {0, 0, 1}),
                            Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), rearRot, sim.rearWheelCoord);
            sim.activeSim.get(SimulationPartManager.class).
                    rotateParts(sim.nonAeroParts, new DoubleVector(new double[] {0, 0, 1}),
                            Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), rearRot, sim.rearWheelCoord);
            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(Collections.singletonList(sim.radiatorCoord),
                            new DoubleVector(new double[]{0.0, 0, 1}),
                            new NeoObjectVector(new Units[]{sim.noUnit,
                                    sim.noUnit, sim.noUnit}), rearRot, sim.rearWheelCoord);
            if (sim.dualRadFlag)
            {
                try {
                    sim.dualRadCoord.getLocalCoordinateSystemManager().
                            rotateLocalCoordinateSystems(Collections.singletonList(sim.dualRadCoord),
                                    new DoubleVector(new double[] {0, 0, 1}),
                                    new NeoObjectVector(new Units[]{sim.noUnit,
                                            sim.noUnit, sim.noUnit}), rearRot, sim.rearWheelCoord);
                }
                catch (Exception e)
                {
                    sim.activeSim.println(this.getClass().getName() + "Dual rad rotation failure");
                }
            }
            try {
                sim.rollAxis.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(Collections.singletonList(sim.rollAxis),
                                new DoubleVector(new double[] {0, 0, 1}),
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
