import star.base.neo.DoubleVector;
import star.common.StarMacro;
import simComponents.*;


import java.util.Map;

public class domainSet extends StarMacro {

    public void execute()
    {
        execute0();
    }

    public void execute0()
    {

        simComponents sim = new simComponents(getActiveSimulation());
        Map<String, double[]> freestream;
        Map<String, double[]> wake;
        Map<String, double[]> car;

        // Figure out if you want full car or half car

        boolean fullCar = simComponents.boolEnv("domainSet");
        if (fullCar)
        {
            freestream = sim.freestreamSize;
            wake = sim.wakeSize;
            car = sim.carSize;
        }
        else
        {
            freestream = sim.halfFreestreamSize;
            wake = sim.halfWakeSize;
            car = sim.halfCarSize;
        }

        // Set the block coordinates

        sim.domain.getCorner1().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(freestream.get(sim.corner1)));
        sim.domain.getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(freestream.get(sim.corner2)));

        sim.car.getCorner1().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(car.get(sim.corner1)));
        sim.car.getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(car.get(sim.corner2)));

        sim.wake.getCorner1().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(wake.get(sim.corner1)));
        sim.wake.getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(wake.get(sim.corner2)));

    }

}
