import star.common.StarMacro;
import star.common.StarScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
This is the God macro that runs the other macros. Pull in the sys env flags, and based on those decide which macros need to be executed.
 */
public class macroController extends StarMacro {
    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        // Macros run in the order they're defined in this string[]

        String [] meshMacros = {
                "domainSet.java",
                "rideHeight.java",
                "steering.java",
                "surfaceWrap.java",
                "subtract.java",
                "regions.java",
                "autoMesh.java",
                "meshRepair.java",
                "meshRepair.java",
                "meshRepair.java",
                "save.java",
        };

        String [] processMacros = {
                "yawSet.java",
                "genReports.java",
                "softRun.java",                         //Sometimes STAR will find negative volume cells after first iteration, not before. If this happens, the macro crashes. I'd rather crash this sacrificial macro than crash the whole chain.
                "meshRepair.java",
                "softRun.java",
                "meshRepair.java",
                "run.java",
                "exportReports.java",
                "save.java"
        };


        String [] postprocessMacros = {
                "exportReports.java",
                "postProc.java"
        };

        List<String> runMacros = new ArrayList<>();

        //Get the sysenvs.
        boolean process = simComponents.boolEnv("process");
        boolean postprocess = simComponents.boolEnv("postprocess");
        boolean preprocess = simComponents.boolEnv("preprocess");

        //Set up run order based on which flags are true and which aren't.
        if (preprocess)
            runMacros.addAll(Arrays.asList(meshMacros));

        if (process)
            runMacros.addAll(Arrays.asList(processMacros));

        if (postprocess)
            runMacros.addAll(Arrays.asList(postprocessMacros));

        getActiveSimulation().println("Macros to run: \n" + runMacros);

        // Run the macros
        for (String macro : runMacros)
        {
            getActiveSimulation().println("-----" + macro + "-----");
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath(macro))).play();
        }

        //Generate a report file for the sim, then kill the sim.
        new star.common.SimulationSummaryReporter().report(getActiveSimulation(), resolvePath(sim.activeSim.getSessionPath() + " report.html"));
        new StarScript(getActiveRootObject(), new java.io.File(resolvePath("kill.java"))).play();

        //There's a lot of back and forth about whether or not macroController should handle save events, or if the individual macros should. I'll let that be your problem.
       
        
    }
}
