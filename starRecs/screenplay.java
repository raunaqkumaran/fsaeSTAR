// STAR-CCM+ macro: screenplay.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.screenplay.operations.neo.*;
import star.base.neo.*;
import star.screenplay.operations.*;
import star.screenplay.*;
import star.vis.*;

public class screenplay extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Screenplay screenplay_0 = 
      ((Screenplay) simulation_0.get(ScreenplayManager.class).getObject("Screenplay 1"));

    Action action_0 = 
      ((Action) screenplay_0.getActionManager().getObject("Action 1"));

    ScalarKeyframeSequence scalarKeyframeSequence_0 = 
      ((ScalarKeyframeSequence) action_0.getOperationManager().getObject("Offset"));

    ScalarKeyframe scalarKeyframe_0 = 
      ((ScalarKeyframe) scalarKeyframeSequence_0.getKeyframeManager().getKeyframe(1));

    ((ScalarPhysicalQuantity) scalarKeyframe_0.getSource()).setValue(30.0);

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Mesh");

    screenplay_0.setAssociatedViewObject(scene_0);

    ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    screenplay_0.setAssociatedViewObject(residualPlot_0);

    Scene scene_1 = 
      simulation_0.getSceneManager().getScene("2D scenes");

    screenplay_0.setAssociatedViewObject(scene_1);

    ((ScalarPhysicalQuantity) scalarKeyframe_0.getSource()).setValue(35.0);

    simulation_0.get(ScreenplayManager.class).setActiveScreenplay(screenplay_0);

    screenplay_0.openEditor();

    ScreenplayDirector screenplayDirector_0 = 
      screenplay_0.getScreenplayDirector();

    screenplayDirector_0.setIsRecording(true);

    screenplayDirector_0.record(4000, 2000, 1.0, 0.0, 10.0, resolvePath("C:\\Users\\rauna\\OneDrive\\Formula SAE\\CFD\\Working sim\\test.avi"), 0, true, false, VideoEncodingQualityEnum.Q1);

    screenplayDirector_0.setIsRecording(false);

    CurrentView currentView_0 = 
      scene_1.getCurrentView();

    VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("RW Profile"));

    currentView_0.setView(visView_0);
  }
}
