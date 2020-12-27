import star.base.neo.DoubleVector;
import star.common.StarMacro;

//Hacked this together to force a change to a half-car domain if the domainSet flag is set appropriately. This doesn't need to be a part of the MacroController flow, but it's a nice tool to have.

public class DomainSet extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {

        SimComponents sim = new SimComponents(getActiveSimulation());

        // Figure out if you want full car or half car
        boolean fullCar = SimComponents.boolEnv("domainSet");

        if (!fullCar)
            return;

        // Set the block coordinates
        if (fullCar) {
            (sim.domain).getCorner1().setCoordinate(sim.meters, sim.meters, sim.inches, new DoubleVector(new double[]{-16.0, 0.0, 0.35}));
            (sim.domain).getCorner2().setCoordinate(sim.meters, sim.meters, sim.meters, new DoubleVector(new double[]{32.0, -6.0, 6.0}));
        }

    }

}
