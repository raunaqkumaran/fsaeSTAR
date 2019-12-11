import simComponents.simComponents;
import star.common.StarMacro;

public class walltimeKill extends StarMacro
{
    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        long walltime = getWalltime(simComponents.valEnvString("WALLTIME"));
        try {
            Thread.sleep((long) ((walltime - 180) * 1000));
            sim.saveSim();
        } catch (InterruptedException e) {
            sim.activeSim.println("Could not force save sim at 95% of walltime");
            e.printStackTrace();
        }

    }

    private long getWalltime(String str)
    {
        String [] splitString = str.split(":");
        splitString = (String[]) simComponents.reverseArr(splitString);
        long walltime = 0;
        for (int i = 0 ; i < splitString.length; i++)
        {
            long longVal = Long.valueOf(splitString[i]);
            if (i == 0)
                walltime += longVal;
            if (i == 1)
                walltime += longVal * 60;
            if (i == 2)
                walltime += longVal * 60 * 60;
            if (i == 3)
                walltime += longVal * 60 * 60 * 24;
        }

        return walltime;
    }
}
