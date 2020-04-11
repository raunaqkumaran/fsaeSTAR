import star.common.StarMacro;

public class exportMesh extends StarMacro {

    public void execute()
    {
        exportScenes obj = new exportScenes();
        simComponents sim = new simComponents(getActiveSimulation());
        exportScenes.exportMesh(sim);
    }
}
