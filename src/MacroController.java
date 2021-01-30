import star.common.StarMacro;
import star.common.StarScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
This is the God macro that runs the other macros. Pull in the sys env flags, and based on those decide which macros need to be executed.
 */
public class MacroController extends StarMacro {
    public void execute()
    {
        SimComponents sim = new SimComponents(getActiveSimulation());
        long startTime = System.currentTimeMillis();
        // Macros run in the order they're defined in this string[]

        String [] meshMacros = {
                "DomainSet.java",
                "RideHeight.java",
                "RollSet.java",
                "Steering.java",
                "SurfaceWrap.java",
                "Subtract.java",
                "Regions.java",
                "AutoMesh.java",
                "MeshRepair.java",
                "MeshRepair.java",
                "MeshRepair.java",
                "save.java",
        };

        String [] processMacros = {
                "MeshRepair.java",
                "yawSet.java",
                "GenReports.java",
                "SoftRun.java",                         //Sometimes STAR will find negative volume cells after first iteration, not before. If this happens, the macro crashes. I'd rather crash this sacrificial macro than crash the whole chain.
                "MeshRepair.java",
                "SoftRun.java",
                "MeshRepair.java",
                "run.java",
        };


        String [] postprocessMacros = {
                "ExportReports.java",
                "PostProc.java"
        };

        List<String> runMacros = new ArrayList<>();

        //Get the sysenvs.
        boolean process = SimComponents.boolEnv("process");
        boolean postprocess = SimComponents.boolEnv("postprocess");
        boolean preprocess = SimComponents.boolEnv("preprocess");

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

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        sim.activeSim.println("Time taken to run MacroController pipeline : " + totalTime / (1000 * 60) + " minutes");

        new StarScript(getActiveRootObject(), new java.io.File(resolvePath("kill.java"))).play();

        //There's a lot of back and forth about whether or not MacroController should handle save events, or if the individual macros should. I'll let that be your problem.
       
        
    }
}
