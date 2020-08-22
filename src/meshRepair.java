import star.base.neo.NeoObjectVector;
import star.base.neo.NeoProperty;
import star.common.*;

import java.util.Vector;

// So for this macro I didn't really fully rewrite it. This is mostly what you get by recording the macro with STAR's recorder.
// This essentially scans the mesh for poor quality cells, high velocity cells, and removes them. This also splits the region by non-continguous, and proceedes to assign them with a null physics continuum to make sure they're essentially out of the solution.

public class meshRepair extends StarMacro {

    public void execute()
    {
        execute0();
    }

    private void execute0()
    {
        Simulation simulation_0 =
                getActiveSimulation();

        simComponents activeSim = new simComponents(simulation_0);

        activeSim.activeSim.println("--- Repairing Mesh ---");

        MeshManager meshManager_0 =
                simulation_0.getMeshManager();

        Region region_0 =
                simulation_0.getRegionManager().getRegion("Subtract");

        //Define which regions are going to be checked by the invalid cell removal tool.
        Object[] regArray;
        if (!activeSim.dualRadFlag)
            regArray = new Object[] {activeSim.domainRegion, activeSim.radiatorRegion};
        else
            regArray = new Object[] {activeSim.domainRegion, activeSim.radiatorRegion, activeSim.dualRadiatorRegion};

        //If the iteration count is higher than 0, then run the mesh repair with the maxVel
        if (activeSim.activeSim.getSimulationIterator().getCurrentIteration() > 0) {
            if (activeSim.maxVelocity.getReportMonitorValue() > ((MonitorIterationStoppingCriterionMaxLimitType) activeSim.maxVel.getCriterionType()).getLimit().evaluate())
            {
                meshManager_0.removeInvalidCells(new NeoObjectVector(regArray), NeoProperty.fromString("{\'minimumContiguousFaceArea\': 0.0, \'minimumCellVolumeEnabled\': true, \'minimumVolumeChangeEnabled\': true, \'functionOperator\': 1, \'minimumContiguousFaceAreaEnabled\': true, \'minimumFaceValidityEnabled\': true, \'functionValue\':" + (activeSim.maxVelocity.getReportMonitorValue() * 0.5) + ", \'functionEnabled\': true, \'function\': \'VelocityMagnitude\', \'minimumVolumeChange\': 1.0E-10, \'minimumCellVolume\': 0.0, \'minimumCellQualityEnabled\': true, \'minimumCellQuality\': 1.0E-5, \'minimumDiscontiguousCells\': 1, \'minimumDiscontiguousCellsEnabled\': true, \'minimumFaceValidity\': 0.51}"));
            }
            meshManager_0.removeInvalidCells(new NeoObjectVector(regArray), NeoProperty.fromString("{\'minimumContiguousFaceArea\': 0.0, \'minimumCellVolumeEnabled\': true, \'minimumVolumeChangeEnabled\': true, \'functionOperator\': 1, \'minimumContiguousFaceAreaEnabled\': true, \'minimumFaceValidityEnabled\': true, \'functionValue\':" + ((MonitorIterationStoppingCriterionMaxLimitType) activeSim.maxVel.getCriterionType()).getLimit().evaluate() + ", \'functionEnabled\': true, \'function\': \'VelocityMagnitude\', \'minimumVolumeChange\': 1.0E-10, \'minimumCellVolume\': 0.0, \'minimumCellQualityEnabled\': true, \'minimumCellQuality\': 1.0E-8, \'minimumDiscontiguousCells\': 1, \'minimumDiscontiguousCellsEnabled\': true, \'minimumFaceValidity\': 0.51}"));
        }
        else
        {
            meshManager_0.removeInvalidCells(new NeoObjectVector(regArray), NeoProperty.fromString("{\'minimumContiguousFaceArea\': 0.0, \'minimumCellVolumeEnabled\': true, \'minimumVolumeChangeEnabled\': true, \'functionOperator\': 1, \'minimumContiguousFaceAreaEnabled\': true, \'minimumFaceValidityEnabled\': true, \'functionValue\':" + (activeSim.maxVelocity.getReportMonitorValue() * 0.5) + ", \'functionEnabled\': false, \'function\': \'VelocityMagnitude\', \'minimumVolumeChange\': 1.0E-10, \'minimumCellVolume\': 0.0, \'minimumCellQualityEnabled\': true, \'minimumCellQuality\': 1.0E-5, \'minimumDiscontiguousCells\': 1, \'minimumDiscontiguousCellsEnabled\': true, \'minimumFaceValidity\': 0.51}"));
        }
        Vector<Region> contigSplitVector = new Vector<>();
        contigSplitVector.add(activeSim.domainRegion);
        for (Region reg : activeSim.activeSim.getRegionManager().getRegions())
        {
            if (!(reg == activeSim.domainRegion || reg == activeSim.radiatorRegion || (activeSim.dualRadFlag && reg == activeSim.dualRadiatorRegion)))
            {
                reg.setPhysicsContinuum(null);
            }
        }
        regions.setTurbulence(activeSim);
    }

}
