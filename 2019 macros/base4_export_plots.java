// Exports all plots in the simulation. Does not alter any of the plot settings or axis limits.

import star.common.Simulation;
import star.common.StarMacro;
import star.common.StarPlot;

import java.io.File;
import java.util.Collection;

public class base4_export_plots extends StarMacro {

  public void execute()
  {
    exportPlots();
  }

  private void exportPlots() {

    Simulation baseSim = getActiveSimulation();
    String sep = File.separator;
    String Directory = baseSim.getSessionDir() + sep + baseSim.getPresentationName() + sep + "Plots";
    new File(resolvePath(Directory)).mkdirs();


    Collection<StarPlot> monitors = baseSim.getPlotManager().getPlots();

    for (StarPlot plot : monitors)
    {
      String plotName = plot.getPresentationName();
      plot.encode(resolvePath(Directory + sep + plotName + ".png"), "png", 4000, 2000);
    }

  }

}
