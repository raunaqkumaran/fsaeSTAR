//STAR-CCM+ Macro for BASESIM_4-1.
//Executes all macros required for simulation set-up, execution, periodic saves, and exports reports and scenes

import star.common.StarMacro;
import star.common.StarScript;

public class base23_run_all_macros_and_scenes_reports extends StarMacro {

  public void execute()
  {
    getScenes();
  }

  private void getScenes()
  {
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base4_surface_wrap.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base19_regions.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base8_reports.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base7_automated_mesh.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base23_fix_mesh.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base4_run.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base3_save.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base21_export_reports.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base4_export_plots.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base12_rw_scene_sweep.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base23_ut_scene_sweep.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base12_fw_scene_sweep.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base12_car_sweep.java"))).play();
    new StarScript(getActiveRootObject(), new java.io.File(resolvePath("base5_close.java"))).play();
  }
}
