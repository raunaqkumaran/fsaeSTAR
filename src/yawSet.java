import star.common.StarMacro;

public class yawSet extends StarMacro {

    public void execute()
    {
        yawSetter();
    }

    private void yawSetter()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        regions obj = new regions();
        obj.yawInterfaces(sim);
    }
}
