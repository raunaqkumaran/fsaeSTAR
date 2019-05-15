import simComponents.simComponents;
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
        String[] twoPhase = {
                "secondPhasePhysics.java",
                "run.java",
                "exportReports.java"
        };

        String [] preprocessMacros = {
                "surfaceWrap.java",
                "subtract.java",
                "regions.java",
                "autoMesh.java",
                "meshRepair.java",
                "genReports.java",
        };

        String [] processMacros = {
                "run.java",
                "exportReports.java",
        };

        String [] geometryManipMacros ={
                "yawSet.java",
                "rideHeight.java",
                "rollSet.java"
        };

        String [] domainSetMacros = {
                "domainSet.java",
        };

        String [] postprocessMacros = {
                "exportReports.java",
                "exportScenes.java"
        };

        List<String> runMacros = new ArrayList<>();

        Boolean preprocess = simComponents.boolEnv("preprocess");
        Boolean process = simComponents.boolEnv("process");
        Boolean geometryManip = simComponents.boolEnv("geometryManip");
        Boolean domainSet = simComponents.boolEnv("domainSet");
        Boolean postprocess = simComponents.boolEnv("postprocess");
        Boolean kw = simComponents.boolEnv("KW");
        Boolean sa = simComponents.boolEnv("SA");

        if (domainSet)
            runMacros.addAll(Arrays.asList(domainSetMacros));

        if (geometryManip)
            runMacros.addAll(Arrays.asList(geometryManipMacros));

        if (preprocess)
            runMacros.addAll(Arrays.asList(preprocessMacros));

        if (process)
            runMacros.addAll(Arrays.asList(processMacros));

        if (postprocess)
            runMacros.addAll(Arrays.asList(postprocessMacros));

        if (kw || sa)
            runMacros.addAll(Arrays.asList(twoPhase));

        runMacros.add("kill.java");

        getActiveRootObject().println(runMacros);

        //Print environment vars
        simComponents.printSystemEnvs();

        // Run the macros
        for (String macro : runMacros)
        {
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath(macro))).play();
            sim.saveSim();
        }
        
    }
}
