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

        String [] geometryManipMacros ={
                "yawSet.java",
                "rideHeight.java",
                "save.java,"
        };

        String [] domainSetMacros = {
                "domainSet.java"
        };

        List<String> runMacros = new ArrayList<>();

        Boolean preprocess = simComponents.boolEnv("preprocess");
        Boolean process = simComponents.boolEnv("process");
        Boolean geometryManip = simComponents.boolEnv("geometryManip");
        Boolean domainSet = simComponents.boolEnv("domainSet");

        if (domainSet)
            runMacros.addAll(Arrays.asList(domainSetMacros));

        if (geometryManip)
            runMacros.addAll(Arrays.asList(geometryManipMacros));

        if (preprocess)
            runMacros.addAll(Arrays.asList(preprocessMacros));

        if (process)
            runMacros.addAll(Arrays.asList(processMacros));

        getActiveRootObject().println(runMacros);

        // Run the macros
        simComponents.printSystemEnvs();

        for (String macro : runMacros)
        {
            new StarScript(getActiveRootObject(), new java.io.File(resolvePath(macro))).play();
        }
        
    }
}
