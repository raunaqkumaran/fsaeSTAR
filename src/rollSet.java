import star.base.neo.DoubleVector;
import star.common.*;
import simComponents.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class rollSet extends StarMacro {

    public void execute()
    {
        rollSetter();
    }

    public void rollSetter()
    {
        double rollAngle;
        simComponents sim = new simComponents(getActiveSimulation());

        rollAngle = Math.toRadians(simComponents.valEnv("roll"));

        if (rollAngle!=0)
        {
            // This is one of those code blocks that makes me fucking hate STAR's API.
            // Why does this need to be so fucking ugly?

            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.nonAeroParts,
                    new DoubleVector(sim.foreAftDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    rollAngle, sim.rollAxis);
            sim.activeSim.get(SimulationPartManager.class).rotateParts(sim.aeroParts,
                    new DoubleVector(sim.foreAftDirection), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit),
                    rollAngle, sim.rollAxis);

            sim.radiatorCoord.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.radiatorCoord)),
                            new DoubleVector(sim.foreAftDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), rollAngle, sim.rollAxis);
            sim.rollAxis.getLocalCoordinateSystemManager().
                    rotateLocalCoordinateSystems(new ArrayList<CoordinateSystem>(Arrays.asList(sim.rollAxis)),
                            new DoubleVector(sim.foreAftDirection),
                            new Vector(Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit)), rollAngle, sim.rollAxis);

        }

        sim.activeSim.println(this.getClass().getName() + " - Roll change attempted " + Math.toDegrees(rollAngle));

    }
}
