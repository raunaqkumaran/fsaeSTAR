import star.common.StarMacro;

/*
This is a fun one. Sometimes STAR will mesh, and MeshRepair won't find negative volume cells. Then you start iterating thinking your mesh is good, then STAR magically realizes there's a negative volume cell and crashes.
This macro makes sure if that happens, you can make sure only this macro crashes, rather than the whole macro suite. This is some really jank sandboxing, but it works.
Run this a few times, then run meshrepair, then run run.java. That way if anything crashes, it's this and not run.java.
 */

public class SoftRun extends StarMacro
{
    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        Regions obj = new Regions();
        obj.initFans(sim);
        sim.activeSim.getSimulationIterator().step(1);

        return;
    }
}
