import star.common.StarMacro;

public class yawSet extends StarMacro {

    public void execute()
    {
        yawSetter();
    }

    private void yawSetter()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        regions obj = new regions();
        obj.yawInterfaces(sim);
    }
}
