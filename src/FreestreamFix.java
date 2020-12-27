import star.common.*;
import star.base.neo.*;
import star.meshing.*;

//This looks like a one-off. Not important.

public class FreestreamFix extends StarMacro {

    public void execute() {


        Simulation simulation_0 =
                getActiveSimulation();

        SimComponents sim = new SimComponents(simulation_0);

        SimpleBlockPart simpleBlockPart_0 =
                ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Freestream"));

        Units units_0 =
                ((Units) simulation_0.getUnitsManager().getObject("m"));

        simpleBlockPart_0.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[]{-16.0, 0.0, 0.00889}));

        sim.saveSim();

    }
}
