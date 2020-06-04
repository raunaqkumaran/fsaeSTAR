import star.base.neo.DoubleVector;
import star.common.StarMacro;

import java.util.Map;

public class domainSet extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {

        simComponents sim = new simComponents(getActiveSimulation());
        // Figure out if you want full car or half car

        boolean fullCar = simComponents.boolEnv("domainSet");

        if (!fullCar)
            return;

        // Set the block coordinates

        if (fullCar) {
            (sim.domain).getCorner1().setCoordinate(sim.meters, sim.meters, sim.inches, new DoubleVector(new double[]{-16.0, 0.0, 0.35}));
            (sim.domain).getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(new double[]{32.0, -6.0, 6.0}));
        }

    }

}
