import simComponents.simComponents;
import star.base.neo.DoubleVector;
import star.common.StarMacro;

import java.util.Arrays;
import java.util.Collections;

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
