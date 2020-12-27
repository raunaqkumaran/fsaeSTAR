import star.common.StarMacro;

/*
This is something i was playing around with. could be interesting if you want the sim to automatically kill itself based on walltime restrictions, rather than iteration restrictions. tie it into the zombie code for the abort file. never got this finished or tested.
 */

public class walltimeKill extends StarMacro
{
    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        long walltime = getWalltime(SimComponents.valEnvString("WALLTIME"));
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
        splitString = (String[]) SimComponents.reverseArr(splitString);
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
