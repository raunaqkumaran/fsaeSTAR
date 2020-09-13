// STAR-CCM+ macro: surfaceMerge.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class surfaceMerge extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    MeshManager meshManager_0 = 
      simulation_0.getMeshManager();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Subtract");

    Boundary boundary_2731 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.Low_Mount; refset MODEL.Assembly 1.Body1.Faces");

    Boundary boundary_2732 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.Low_Mount; refset MODEL.Assembly 1.Body2.Faces");

    Boundary boundary_2733 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_2021_M2; refset MODEL.Body.Faces");

    Boundary boundary_2734 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_EP_EXT; refset MODEL.Assembly 1.Body1.Faces");

    Boundary boundary_2735 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_EP_EXT; refset MODEL.Assembly 1.Body2.Faces");

    Boundary boundary_2736 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_EP_EXT; refset MODEL.Assembly 1.Body3.Faces");

    Boundary boundary_2737 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_EP_EXT; refset MODEL.Assembly 1.Body4.Faces");

    Boundary boundary_2738 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body1.Faces");

    Boundary boundary_2739 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body2.Faces");

    Boundary boundary_2740 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body3.Faces");

    Boundary boundary_2741 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body4.Faces");

    Boundary boundary_2742 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body5.Faces");

    Boundary boundary_2743 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M1_Twist_6; refset Entire Part.Assembly 1.Body6.Faces");

    Boundary boundary_2744 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M3_GURNEY; refset MODEL.Assembly 1.Body1.Faces");

    Boundary boundary_2745 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_M3_GURNEY; refset MODEL.Assembly 1.Body3.Faces");

    Boundary boundary_2746 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_Twist_6_Mount; refset Entire Part.Assembly 1.Body1.Faces");

    Boundary boundary_2747 = 
      region_0.getBoundaryManager().getBoundary("Surface wrapper.RW_TWIST_6_EP_EXT.RW_Twist_6_Mount; refset Entire Part.Assembly 1.Body2.Faces");

    meshManager_0.combineBoundaries(new NeoObjectVector(new Object[] {boundary_2731, boundary_2732, boundary_2733, boundary_2734, boundary_2735, boundary_2736, boundary_2737, boundary_2738, boundary_2739, boundary_2740, boundary_2741, boundary_2742, boundary_2743, boundary_2744, boundary_2745, boundary_2746, boundary_2747}));
  }
}
