import star.common.StarMacro;

/*
A nice little interface to test a macro chain without having to mess with MacroController.
 */

public class testBed extends StarMacro {
    public void execute()
    {
        SimComponents activeSim = new SimComponents(getActiveSimulation());
        //exportScenes.exportMesh(activeSim);
    }
}
