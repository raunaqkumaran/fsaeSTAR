// Simcenter STAR-CCM+ macro: rot.java
// Written by Simcenter STAR-CCM+ 15.06.008
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;

public class rot extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    MeshPart meshPart_690 = 
      ((MeshPart) simulation_0.get(SimulationPartManager.class).getPart("CFD_Driver"));

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Front Wheel Cylindrical"));

    simulation_0.get(SimulationPartManager.class).rotateParts(new NeoObjectVector(new Object[] {meshPart_690}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new NeoObjectVector(new Object[] {units_2, units_1, units_2}), 0.5235987755982989, cylindricalCoordinateSystem_0);

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("3D scenes");

    CurrentView currentView_0 = 
      scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {0.24518300615479893, -0.2493439679976035, 1.0257529669032366}), new DoubleVector(new double[] {0.24518300615479893, -101.15017020338385, 1.0257529669032366}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.20721073338291163, 0.16101368183211073, 0.869223256116396}), new DoubleVector(new double[] {16.387963079576263, -73.7998350054245, 67.56969442038822}), new DoubleVector(new double[] {-0.0611699683762653, 0.6610498014846931, 0.7478444991613543}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.23366319081615705, -0.15972561667307425, 0.9045490795391078}), new DoubleVector(new double[] {6.000609434362352, -101.03021599572651, -2.809895810942776}), new DoubleVector(new double[] {0.026300462565287508, -0.035284579938216154, 0.9990311727305805}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.23366319081615705, -0.15972561667307425, 0.9045490795391078}), new DoubleVector(new double[] {6.000609434362352, -101.03021599572651, -2.809895810942776}), new DoubleVector(new double[] {0.026300462565287508, -0.035284579938216154, 0.9990311727305805}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.23354721631826902, -0.15957005966098858, 0.9001436715593154}), new DoubleVector(new double[] {6.000493457768907, -101.03006040206073, -2.8143012175728366}), new DoubleVector(new double[] {0.026300462565287508, -0.035284579938216154, 0.9990311727305805}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.23521067867368473, -0.19024691963336834, 0.9002554696548195}), new DoubleVector(new double[] {5.4078664705543344, -101.06320970540479, 5.349929905102016}), new DoubleVector(new double[] {0.021937196169390227, 0.045180909294793785, 0.9987379260143907}), 1.940258692758122, 1, 30.0);

    currentView_0.setInput(new DoubleVector(new double[] {0.23521067867368473, -0.19024691963336834, 0.9002554696548195}), new DoubleVector(new double[] {5.4078664705543344, -101.06320970540479, 5.349929905102016}), new DoubleVector(new double[] {0.021937196169390227, 0.045180909294793785, 0.9987379260143907}), 1.940258692758122, 1, 30.0);

    scene_0.setViewOrientation(new DoubleVector(new double[] {0.0, -1.0, 0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));
  }
}
