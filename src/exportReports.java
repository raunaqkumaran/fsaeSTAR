import star.base.report.Report;
import star.common.StarMacro;

import java.io.File;

public class exportReports extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        String path;

        // Set file path for reports
        path = activeSim.dir + activeSim.separator + activeSim.simName + activeSim.separator + "Reports";
        File repFolder = new File(resolvePath(path));

        // Create folder if it doesn't exist

        if (!repFolder.exists())
            repFolder.mkdirs();

        for (Report rep : activeSim.reports)
        {
            rep.printReport(resolvePath(path + activeSim.separator + rep.getPresentationName().replaceAll("[\\/]", "") + ".txt"), false);
        }

        activeSim.repTable.setRepresentation(activeSim.finiteVol);
        activeSim.repTable.extract();
        activeSim.repTable.export(path + activeSim.separator + "Reports.csv");

        postProc esObj = new postProc();
        esObj.exportPlots(activeSim);
    }

}
