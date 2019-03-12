import simComponents.simComponents;
import star.common.StarMacro;
import star.common.StarScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class macroController extends StarMacro {
    public void execute()
    {
        // Macros run in the order they're defined in this string[]
        String[] macroName = {
                "domainSet.java",
                "rideHeight.java",
                "yawSet.java",
                "surfaceWrap.java",
                "subtract.java",
                "regions.java",
                "autoMesh.java",
                "meshRepair.java",
                "genReports.java",
                "run.java",
                "save.java",
                "exportReports.java",
                "kill.java"
        };

        String [] preprocessMacros = {
                "surfaceWrap.java",
                "subtract.java",
                "regions.java",
                "autoMesh.java",
                "meshRepair.java",
                "genReports.java",
                "save.java",
                "kill.java"
        };

        String [] processMacros = {
                "run.java",
                "save.java",
                "exportReports.java",
                "kill.java"
        };

        List<String> runMacros = new ArrayList<>();

        Boolean preprocess = simComponents.boolEnv("preprocess");
        Boolean process = simComponents.boolEnv("process");

        if (preprocess)
        {
            runMacros.addAll(Arrays.asList(preprocessMacros));
        }

        if (process)
        {
            runMacros.addAll(Arrays.asList(processMacros));
        }


        // Run the macros
        for (String macro : runMacros)
        {
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath(macro))).play();
        }
        
    }
}
