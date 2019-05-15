import star.common.StarMacro;
import simComponents.*;

public class secondPhasePhysics extends StarMacro {
    public void execute()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());

        activeSim.saFlag = false;
        activeSim.kwFlag = true;

        if (activeSim.saFlag) {
            activeSim.domainRegion.setPhysicsContinuum(activeSim.saPhysics);
            activeSim.radiatorRegion.setPhysicsContinuum(activeSim.saPhysics);
        }
        if (activeSim.kwFlag){
            activeSim.domainRegion.setPhysicsContinuum(activeSim.kwPhysics);
            activeSim.radiatorRegion.setPhysicsContinuum(activeSim.kwPhysics);
        }


        activeSim.maxSteps += activeSim.maxSteps;
    }
}
