// You NEED to run these via batch

import simComponents.simComponents;
import star.common.MonitorIterationStoppingCriterionMaxLimitType;
import star.common.StarMacro;
import star.common.StarScript;

import java.io.File;

public class run extends StarMacro {


    public void execute() {

        simComponents activeSim = new simComponents(getActiveSimulation());
        activeSim.activeSim.println("--- RUNNING SIMULATION ---");
        regions.setPhysics(activeSim);
        initial(activeSim);
        execute0(activeSim);
    }

    private void execute0(simComponents activeSim)
    {

        activeSim.maxVel.setIsUsed(false);
        activeSim.activeSim.println("Disable maxVel attempted");
        activeSim.activeSim.getSimulationIterator().step(4);
        activeSim.maxVel.setIsUsed(true);
        activeSim.activeSim.println("Enable maxVel attempted");
        activeSim.activeSim.getSimulationIterator().run();

        if (activeSim.maxVel.getIsSatisfied())
        {
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath("meshRepair.java"))).play();
            execute0(activeSim);
        }

        activeSim.saveSim();
    }
    private void initial(simComponents activeSim)
    {
        int currentIteration = activeSim.activeSim.getSimulationIterator().getCurrentIteration();
        ((MonitorIterationStoppingCriterionMaxLimitType) activeSim.maxStepStop.getCriterionType()).getLimit().setValue(currentIteration + activeSim.maxSteps);
        activeSim.maxStepStop.setInnerIterationCriterion(true);
        activeSim.maxStepStop.setIsUsed(true);
        activeSim.abortFile.setAbortFilePath(simComponents.valEnvString("PBS_O_WORKDIR") + File.separator + simComponents.valEnvString("PBS_JOBID"));

    }

}
