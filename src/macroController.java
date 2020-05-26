import star.common.StarMacro;
import star.common.StarScript;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class macroController extends StarMacro {
    public void execute()
    {
        simComponents sim = new simComponents(getActiveSimulation());
        // Macros run in the order they're defined in this string[]

        String [] meshMacros = {
                "rideHeight.java",
                "surfaceWrap.java",
                "subtract.java",
                "regions.java",
                "autoMesh.java",
                "meshRepair.java",
                "meshRepair.java",
                "meshRepair.java",
                "exportMesh.java"
        };

        String [] processMacros = {
                "yawSet.java",
                "genReports.java",
                "softRun.java",                         //Sometimes STAR will find negative volume cells after first iteration, not before. If this happens, the macro crashes. I'd rather crash this sacrificial macro than crash the whole chain.
                "meshRepair.java",
                "run.java",
                "exportReports.java",
        };


        String [] postprocessMacros = {
                "exportReports.java",
                "exportScenes.java"
        };

        List<String> runMacros = new ArrayList<>();

        boolean process = simComponents.boolEnv("process");
        boolean postprocess = simComponents.boolEnv("postprocess");
        boolean preprocess = simComponents.boolEnv("preprocess");

        if (preprocess)
            runMacros.addAll(Arrays.asList(meshMacros));

        if (process)
            runMacros.addAll(Arrays.asList(processMacros));

        if (postprocess)
            runMacros.addAll(Arrays.asList(postprocessMacros));

        getActiveRootObject().println(runMacros);

        // Run the macros
        for (String macro : runMacros)
        {
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath(macro))).play();
        }

        new star.common.SimulationSummaryReporter().report(getActiveSimulation(), resolvePath(sim.activeSim.getSessionPath() + " report.html"));
        new StarScript(getActiveRootObject(), new java.io.File(resolvePath("kill.java"))).play();

       
        
    }
}
