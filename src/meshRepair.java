import star.base.neo.IntVector;
import star.base.neo.NeoObjectVector;
import star.base.neo.NeoProperty;
import star.common.*;
import simComponents.*;

// So for this macro I didn't really rewrite it. It's only 30 lines.

public class meshRepair extends StarMacro {

    public void execute()
    {
        execute0();
    }

    public void execute0()
    {
        Simulation simulation_0 =
                getActiveSimulation();

        Solution sol = simulation_0.getSolution();
        sol.clearSolution(Solution.Clear.History);

        // Might have to uncomment the lines bellow, depending on how badly fucked the sim is. Hopefully not. Would be nice to not have to clear sols

        // sol.clearSolution(Solution.Clear.Fields);
        // sol.clearSolution(Solution.Clear.Mesh);

        Units units_0 =
                simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

        Units units_1 =
                simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

        Units units_2 =
                simulation_0.getUnitsManager().hasPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

        Units units_3 =
                simulation_0.getUnitsManager().hasPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

        MeshManager meshManager_0 =
                simulation_0.getMeshManager();

        Region region_0 =
                simulation_0.getRegionManager().getRegion("Subtract");

        meshManager_0.removeInvalidCells(new NeoObjectVector(new Object[] {region_0}), NeoProperty.fromString("{\'minimumContiguousFaceArea\': 0.0, \'minimumCellVolumeEnabled\': true, \'minimumVolumeChangeEnabled\': true, \'functionOperator\': 1, \'minimumContiguousFaceAreaEnabled\': true, \'minimumFaceValidityEnabled\': true, \'functionValue\': 2000.0, \'functionEnabled\': false, \'function\': \'Pressure\', \'minimumVolumeChange\': 1.0E-10, \'minimumCellVolume\': 0.0, \'minimumCellQualityEnabled\': true, \'minimumCellQuality\': 1.0E-8, \'minimumDiscontiguousCells\': 100000, \'minimumDiscontiguousCellsEnabled\': true, \'minimumFaceValidity\': 0.51}"));

    }

}
