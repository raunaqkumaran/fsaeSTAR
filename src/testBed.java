import star.common.StarMacro;

/*
A nice little interface to test a macro chain without having to mess with macroController.
 */

public class testBed extends StarMacro {
    public void execute()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        //exportScenes.exportMesh(activeSim);
    }
}
