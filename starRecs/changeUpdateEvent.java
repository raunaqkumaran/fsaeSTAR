// STAR-CCM+ macro: changeUpdateEvent.java
// Written by STAR-CCM+ 14.04.013
package macro;

import java.util.*;

import star.common.*;
import star.base.report.*;

public class changeUpdateEvent extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    ReportMonitor reportMonitor_0 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("L/D Monitor"));

    StarUpdate starUpdate_0 = 
      reportMonitor_0.getStarUpdate();

    starUpdate_0.getUpdateModeOption().setSelected(StarUpdateModeOption.Type.TIMESTEP);
  }
}
