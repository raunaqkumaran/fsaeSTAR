import star.base.neo.DoubleVector;
import star.common.SimulationPartManager;
import star.common.StarMacro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class RollSet extends StarMacro {

    public void execute()
    {
        rollSetter();
    }

    private void rollSetter()
    {
        double rollAngle;
        SimComponents sim = new SimComponents(getActiveSimulation());

        rollAngle = Math.toRadians(sim.valEnv("roll"));

        //Debug
        //rollAngle = Math.toRadians(0);

        if (rollAngle!=0)
        {
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.nonAeroParts,
                    new DoubleVector(sim.foreAftDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    rollAngle, sim.rollAxis);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.aeroParts,
                    new DoubleVector(sim.foreAftDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    rollAngle, sim.rollAxis);

            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<>(Collections.singletonList(sim.radiatorCoord)),
                            new DoubleVector(sim.foreAftDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), rollAngle, sim.rollAxis);
            if (sim.dualRadFlag)
            {
                sim.dualRadCoord.getLocalCoordinateSystemManager().
                        rotateLocalCoordinateSystems(new ArrayList<>(Collections.singletonList(sim.radiatorCoord)),
                                new DoubleVector(sim.foreAftDirection),
                                new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), rollAngle, sim.rollAxis);
            }
            sim.rollAxis.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<>(Collections.singletonList(sim.rollAxis)),
                            new DoubleVector(sim.foreAftDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), rollAngle, sim.rollAxis);

        }

        sim.activeSim.println(this.getClass().getName() + " - Roll change attempted " + Math.toDegrees(rollAngle));

    }
}
