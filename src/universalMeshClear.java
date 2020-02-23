import star.common.Simulation;
import star.common.StarMacro;

import java.io.File;

public class universalMeshClear extends StarMacro {

    public void execute()
    {
        Simulation activeSim = getActiveSimulation();
        activeSim.getMeshPipelineController().clearGeneratedMeshes();
        activeSim.clearSolution();
        activeSim.saveState(activeSim.getSessionDir() + File.separator + activeSim.getPresentationName() + ".sim");
    }

}
