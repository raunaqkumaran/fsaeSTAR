import org.osgi.util.measurement.Unit;
import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.GeometryPart;
import star.common.SimulationPartManager;
import star.common.StarMacro;
import star.common.Units;

import java.util.Arrays;
import java.util.Collections;

public class steering extends StarMacro {

    public void execute() {
        simComponents sim = new simComponents(getActiveSimulation());
        double steerAngle = Math.toRadians(sim.valEnv(simComponents.STEERING));
        GeometryPart frontRight = null, frontLeft = null;
        for (GeometryPart x : sim.wheels)
        {
            if (x.getPresentationName().contains(simComponents.FRONT_LEFT))
                frontLeft = x;
            else if (x.getPresentationName().contains(simComponents.FRONT_RIGHT))
                frontRight = x;
        }
        sim.activeSim.get(SimulationPartManager.class).rotateParts(Collections.singletonList(frontLeft), new DoubleVector(new double[]{1, 0, 0}), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), steerAngle, sim.frontWheelCoord);
        sim.frontWheelCoord.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(Collections.singletonList(sim.frontWheelCoord), new DoubleVector(new double[] {1, 0, 0}), new NeoObjectVector(new Units[]{sim.noUnit, sim.noUnit, sim.noUnit}), steerAngle, sim.frontWheelCoord);
        sim.activeSim.get(SimulationPartManager.class).rotateParts(Collections.singletonList(frontRight), new DoubleVector(new double[]{1, 0, 0}), Arrays.asList(sim.noUnit, sim.noUnit, sim.noUnit), steerAngle, sim.frontWheelSteering);
        sim.frontWheelSteering.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(Collections.singletonList(sim.frontWheelSteering), new DoubleVector(new double[] {1, 0, 0}), new NeoObjectVector(new Units[]{sim.noUnit, sim.noUnit, sim.noUnit}), steerAngle, sim.frontWheelSteering);
    }



}
