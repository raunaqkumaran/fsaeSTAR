import star.common.StarMacro;

public class testBed extends StarMacro {
    public void execute()
    {
        simComponents activesim = new simComponents(getActiveSimulation());
        exportScenes.exportMesh(activesim);
    }
}
