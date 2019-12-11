import simComponents.simComponents;
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
        Map<String, double[]> freestream;
        // Figure out if you want full car or half car

        boolean fullCar = simComponents.boolEnv("domainSet");

        //Debug
        //fullCar = true;

        if (fullCar)
        {
            freestream = sim.freestreamSize;
            sim.activeSim.println(this.getClass().getName() + " - Attempting domain set to full");
        }
        else
        {
            freestream = sim.halfFreestreamSize;
            sim.activeSim.println(this.getClass().getName() + " - Attempting domain set to half");
        }

        // Set the block coordinates

        (sim.domain).getCorner1().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(freestream.get(sim.corner1)));
        (sim.domain).getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(freestream.get(sim.corner2)));

    }

}
