
import java.io.File;
import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.coremodule.actions.ActiveSimulationAction;
import star.flow.*;
import star.post.actions.ClearSolutionHistoryAction;

import java.lang.String;

public class base23_fix_mesh_save extends StarMacro {

  public void execute()
  {
    getScenes();
  }

  private void getScenes()
  {
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base23_fix_mesh.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
  }
}
