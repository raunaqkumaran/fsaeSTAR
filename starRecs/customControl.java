// STAR-CCM+ macro: customControl.java
// Written by STAR-CCM+ 14.06.013
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;
import star.surfacewrapper.*;

public class customControl extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Plane section scenes");

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    HardcopyProperties hardcopyProperties_0 = 
      sceneUpdate_0.getHardcopyProperties();

    hardcopyProperties_0.setCurrentResolutionWidth(793);

    hardcopyProperties_0.setCurrentResolutionHeight(473);

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Surface wrapper"));

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      ((SurfaceCustomMeshControl) surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().getObject("Aero Control"));

    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(null);

    CompositePart compositePart_2 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("FW 2"));

    CompositePart compositePart_3 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_0002_Tierod_Bracket_Universal_COPY; refset PF20"));

    MeshPart meshPart_4 = 
      ((MeshPart) compositePart_3.getChildParts().getPart("Body 1647"));

    PartSurface partSurface_0 = 
      ((PartSurface) meshPart_4.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_4 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_0002_Tierod_Bracket_Universal_COPY; refset PF20 2"));

    MeshPart meshPart_5 = 
      ((MeshPart) compositePart_4.getChildParts().getPart("Body 1648"));

    PartSurface partSurface_1 = 
      ((PartSurface) meshPart_5.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_5 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1201_FW_Main_Center_COPY; refset PF20"));

    CompositePart compositePart_6 = 
      ((CompositePart) compositePart_5.getChildParts().getPart("Body Group 1523"));

    MeshPart meshPart_6 = 
      ((MeshPart) compositePart_6.getChildParts().getPart("Body 1631"));

    PartSurface partSurface_2 = 
      ((PartSurface) meshPart_6.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_7 = 
      ((MeshPart) compositePart_6.getChildParts().getPart("Body 1632"));

    PartSurface partSurface_3 = 
      ((PartSurface) meshPart_7.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_7 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1202_FW_Main_Outer_COPY; refset PF20"));

    CompositePart compositePart_8 = 
      ((CompositePart) compositePart_7.getChildParts().getPart("Body Group 1521"));

    MeshPart meshPart_8 = 
      ((MeshPart) compositePart_8.getChildParts().getPart("Body 1629"));

    PartSurface partSurface_4 = 
      ((PartSurface) meshPart_8.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_9 = 
      ((MeshPart) compositePart_8.getChildParts().getPart("Body 1630"));

    PartSurface partSurface_5 = 
      ((PartSurface) meshPart_9.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_9 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1203_FW_Flap_1_Outer_COPY; refset PF20"));

    MeshPart meshPart_10 = 
      ((MeshPart) compositePart_9.getChildParts().getPart("Body 1622"));

    PartSurface partSurface_6 = 
      ((PartSurface) meshPart_10.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_10 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1203_FW_Flap_1_Outer_COPY; refset PF20 2"));

    MeshPart meshPart_11 = 
      ((MeshPart) compositePart_10.getChildParts().getPart("Body 1623"));

    PartSurface partSurface_7 = 
      ((PartSurface) meshPart_11.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_11 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1203_FW_Flap_1_Outer_COPY; refset PF20 3"));

    MeshPart meshPart_12 = 
      ((MeshPart) compositePart_11.getChildParts().getPart("Body 1624"));

    PartSurface partSurface_8 = 
      ((PartSurface) meshPart_12.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_12 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1203_FW_Flap_1_Outer_COPY; refset PF20 4"));

    MeshPart meshPart_13 = 
      ((MeshPart) compositePart_12.getChildParts().getPart("Body 1628"));

    PartSurface partSurface_9 = 
      ((PartSurface) meshPart_13.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_13 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1204_FW_Flap_2_Outer_COPY; refset PF20"));

    MeshPart meshPart_14 = 
      ((MeshPart) compositePart_13.getChildParts().getPart("Body 1619"));

    PartSurface partSurface_10 = 
      ((PartSurface) meshPart_14.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_14 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1204_FW_Flap_2_Outer_COPY; refset PF20 2"));

    MeshPart meshPart_15 = 
      ((MeshPart) compositePart_14.getChildParts().getPart("Body 1620"));

    PartSurface partSurface_11 = 
      ((PartSurface) meshPart_15.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_15 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1204_FW_Flap_2_Outer_COPY; refset PF20 3"));

    MeshPart meshPart_16 = 
      ((MeshPart) compositePart_15.getChildParts().getPart("Body 1621"));

    PartSurface partSurface_12 = 
      ((PartSurface) meshPart_16.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_16 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1204_FW_Flap_2_Outer_COPY; refset PF20 4"));

    MeshPart meshPart_17 = 
      ((MeshPart) compositePart_16.getChildParts().getPart("Body 1627"));

    PartSurface partSurface_13 = 
      ((PartSurface) meshPart_17.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_17 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1205_FW_Flap_1_Inner_COPY; refset PF20"));

    MeshPart meshPart_18 = 
      ((MeshPart) compositePart_17.getChildParts().getPart("Body 1616"));

    PartSurface partSurface_14 = 
      ((PartSurface) meshPart_18.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_18 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1205_FW_Flap_1_Inner_COPY; refset PF20 2"));

    MeshPart meshPart_19 = 
      ((MeshPart) compositePart_18.getChildParts().getPart("Body 1617"));

    PartSurface partSurface_15 = 
      ((PartSurface) meshPart_19.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_19 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1205_FW_Flap_1_Inner_COPY; refset PF20 3"));

    MeshPart meshPart_20 = 
      ((MeshPart) compositePart_19.getChildParts().getPart("Body 1618"));

    PartSurface partSurface_16 = 
      ((PartSurface) meshPart_20.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_20 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1205_FW_Flap_1_Inner_COPY; refset PF20 4"));

    MeshPart meshPart_21 = 
      ((MeshPart) compositePart_20.getChildParts().getPart("Body 1626"));

    PartSurface partSurface_17 = 
      ((PartSurface) meshPart_21.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_21 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1206_FW_Flap_2_Inner_COPY; refset PF20"));

    MeshPart meshPart_22 = 
      ((MeshPart) compositePart_21.getChildParts().getPart("Body 1613"));

    PartSurface partSurface_18 = 
      ((PartSurface) meshPart_22.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_22 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1206_FW_Flap_2_Inner_COPY; refset PF20 2"));

    MeshPart meshPart_23 = 
      ((MeshPart) compositePart_22.getChildParts().getPart("Body 1614"));

    PartSurface partSurface_19 = 
      ((PartSurface) meshPart_23.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_23 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1206_FW_Flap_2_Inner_COPY; refset PF20 3"));

    MeshPart meshPart_24 = 
      ((MeshPart) compositePart_23.getChildParts().getPart("Body 1615"));

    PartSurface partSurface_20 = 
      ((PartSurface) meshPart_24.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_24 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1206_FW_Flap_2_Inner_COPY; refset PF20 4"));

    MeshPart meshPart_25 = 
      ((MeshPart) compositePart_24.getChildParts().getPart("Body 1625"));

    PartSurface partSurface_21 = 
      ((PartSurface) meshPart_25.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_25 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1207_FW_Swans_COPY; refset PF20"));

    CompositePart compositePart_26 = 
      ((CompositePart) compositePart_25.getChildParts().getPart("Body Group 1503"));

    MeshPart meshPart_26 = 
      ((MeshPart) compositePart_26.getChildParts().getPart("Body 1611"));

    PartSurface partSurface_22 = 
      ((PartSurface) meshPart_26.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_27 = 
      ((MeshPart) compositePart_26.getChildParts().getPart("Body 1612"));

    PartSurface partSurface_23 = 
      ((PartSurface) meshPart_27.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_27 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1208_FW_Swan_Tabs_COPY; refset PF20"));

    CompositePart compositePart_28 = 
      ((CompositePart) compositePart_27.getChildParts().getPart("Body Group 1501"));

    MeshPart meshPart_28 = 
      ((MeshPart) compositePart_28.getChildParts().getPart("Body 1609"));

    PartSurface partSurface_24 = 
      ((PartSurface) meshPart_28.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_29 = 
      ((MeshPart) compositePart_28.getChildParts().getPart("Body 1610"));

    PartSurface partSurface_25 = 
      ((PartSurface) meshPart_29.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_29 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1208_FW_Tierod_COPY; refset PF20"));

    CompositePart compositePart_30 = 
      ((CompositePart) compositePart_29.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20"));

    CompositePart compositePart_31 = 
      ((CompositePart) compositePart_30.getChildParts().getPart("Body Group 1526"));

    MeshPart meshPart_30 = 
      ((MeshPart) compositePart_31.getChildParts().getPart("CHAMFER1 5"));

    PartSurface partSurface_26 = 
      ((PartSurface) meshPart_30.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_31 = 
      ((MeshPart) compositePart_31.getChildParts().getPart("CUT-EXTRUDE2 5"));

    PartSurface partSurface_27 = 
      ((PartSurface) meshPart_31.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_32 = 
      ((CompositePart) compositePart_29.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20 2"));

    CompositePart compositePart_33 = 
      ((CompositePart) compositePart_32.getChildParts().getPart("Body Group 1528"));

    MeshPart meshPart_32 = 
      ((MeshPart) compositePart_33.getChildParts().getPart("CHAMFER1 6"));

    PartSurface partSurface_28 = 
      ((PartSurface) meshPart_32.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_33 = 
      ((MeshPart) compositePart_33.getChildParts().getPart("CUT-EXTRUDE2 6"));

    PartSurface partSurface_29 = 
      ((PartSurface) meshPart_33.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_34 = 
      ((CompositePart) compositePart_29.getChildParts().getPart("PF20_AER_1209_FW_CarbonTube_COPY; refset PF20"));

    MeshPart meshPart_34 = 
      ((MeshPart) compositePart_34.getChildParts().getPart("Body 1639"));

    PartSurface partSurface_30 = 
      ((PartSurface) meshPart_34.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_35 = 
      ((CompositePart) compositePart_29.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20"));

    MeshPart meshPart_35 = 
      ((MeshPart) compositePart_35.getChildParts().getPart("Body 1637"));

    PartSurface partSurface_31 = 
      ((PartSurface) meshPart_35.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_36 = 
      ((CompositePart) compositePart_29.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20 2"));

    MeshPart meshPart_36 = 
      ((MeshPart) compositePart_36.getChildParts().getPart("Body 1638"));

    PartSurface partSurface_32 = 
      ((PartSurface) meshPart_36.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_37 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1208_FW_Tierod_COPY; refset PF20 2"));

    CompositePart compositePart_38 = 
      ((CompositePart) compositePart_37.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20"));

    CompositePart compositePart_39 = 
      ((CompositePart) compositePart_38.getChildParts().getPart("Body Group 1534"));

    MeshPart meshPart_37 = 
      ((MeshPart) compositePart_39.getChildParts().getPart("CHAMFER1 7"));

    PartSurface partSurface_33 = 
      ((PartSurface) meshPart_37.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_38 = 
      ((MeshPart) compositePart_39.getChildParts().getPart("CUT-EXTRUDE2 7"));

    PartSurface partSurface_34 = 
      ((PartSurface) meshPart_38.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_40 = 
      ((CompositePart) compositePart_37.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20 2"));

    CompositePart compositePart_41 = 
      ((CompositePart) compositePart_40.getChildParts().getPart("Body Group 1536"));

    MeshPart meshPart_39 = 
      ((MeshPart) compositePart_41.getChildParts().getPart("CHAMFER1 8"));

    PartSurface partSurface_35 = 
      ((PartSurface) meshPart_39.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_40 = 
      ((MeshPart) compositePart_41.getChildParts().getPart("CUT-EXTRUDE2 8"));

    PartSurface partSurface_36 = 
      ((PartSurface) meshPart_40.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_42 = 
      ((CompositePart) compositePart_37.getChildParts().getPart("PF20_AER_1209_FW_CarbonTube_COPY; refset PF20"));

    MeshPart meshPart_41 = 
      ((MeshPart) compositePart_42.getChildParts().getPart("Body 1646"));

    PartSurface partSurface_37 = 
      ((PartSurface) meshPart_41.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_43 = 
      ((CompositePart) compositePart_37.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20"));

    MeshPart meshPart_42 = 
      ((MeshPart) compositePart_43.getChildParts().getPart("Body 1644"));

    PartSurface partSurface_38 = 
      ((PartSurface) meshPart_42.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_44 = 
      ((CompositePart) compositePart_37.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20 2"));

    MeshPart meshPart_43 = 
      ((MeshPart) compositePart_44.getChildParts().getPart("Body 1645"));

    PartSurface partSurface_39 = 
      ((PartSurface) meshPart_43.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_45 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("PF20_AER_1210_FW_Tab_Assembly_COPY; refset PF20"));

    CompositePart compositePart_46 = 
      ((CompositePart) compositePart_45.getChildParts().getPart("PF20_AER_1211_Tab_Tierod_Lat_COPY; refset MODEL"));

    MeshPart meshPart_44 = 
      ((MeshPart) compositePart_46.getChildParts().getPart("Body 1649"));

    PartSurface partSurface_40 = 
      ((PartSurface) meshPart_44.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_47 = 
      ((CompositePart) compositePart_45.getChildParts().getPart("PF20_AER_1211_Tab_Tierod_Lat_COPY; refset MODEL 2"));

    MeshPart meshPart_45 = 
      ((MeshPart) compositePart_47.getChildParts().getPart("Body 1650"));

    PartSurface partSurface_41 = 
      ((PartSurface) meshPart_45.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_48 = 
      ((CompositePart) compositePart_45.getChildParts().getPart("PF20_AER_1211_Tab_Tierod_Lat_COPY; refset MODEL 3"));

    MeshPart meshPart_46 = 
      ((MeshPart) compositePart_48.getChildParts().getPart("Body 1651"));

    PartSurface partSurface_42 = 
      ((PartSurface) meshPart_46.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_49 = 
      ((CompositePart) compositePart_45.getChildParts().getPart("PF20_AER_1211_Tab_Tierod_Lat_COPY; refset MODEL 4"));

    MeshPart meshPart_47 = 
      ((MeshPart) compositePart_49.getChildParts().getPart("Body 1652"));

    PartSurface partSurface_43 = 
      ((PartSurface) meshPart_47.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_50 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("FW 3"));

    CompositePart compositePart_51 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1001_FW_MainElement_COPY; refset PF20"));

    CompositePart compositePart_52 = 
      ((CompositePart) compositePart_51.getChildParts().getPart("Body Group 1574"));

    MeshPart meshPart_48 = 
      ((MeshPart) compositePart_52.getChildParts().getPart("Body 1659"));

    PartSurface partSurface_44 = 
      ((PartSurface) meshPart_48.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_49 = 
      ((MeshPart) compositePart_52.getChildParts().getPart("Body 1660"));

    PartSurface partSurface_45 = 
      ((PartSurface) meshPart_49.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_53 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1002_FW_EP_COPY; refset PF20"));

    CompositePart compositePart_54 = 
      ((CompositePart) compositePart_53.getChildParts().getPart("Body Group 1580"));

    MeshPart meshPart_50 = 
      ((MeshPart) compositePart_54.getChildParts().getPart("Body 1665"));

    PartSurface partSurface_46 = 
      ((PartSurface) meshPart_50.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_51 = 
      ((MeshPart) compositePart_54.getChildParts().getPart("Body 1666"));

    PartSurface partSurface_47 = 
      ((PartSurface) meshPart_51.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_55 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1003_FW_OuterFlaps_1_COPY; refset MODEL"));

    CompositePart compositePart_56 = 
      ((CompositePart) compositePart_55.getChildParts().getPart("Body Group 1568"));

    MeshPart meshPart_52 = 
      ((MeshPart) compositePart_56.getChildParts().getPart("Body 1653"));

    PartSurface partSurface_48 = 
      ((PartSurface) meshPart_52.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_53 = 
      ((MeshPart) compositePart_56.getChildParts().getPart("Body 1654"));

    PartSurface partSurface_49 = 
      ((PartSurface) meshPart_53.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_57 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1004_FW_OuterFlaps_2_COPY; refset MODEL"));

    CompositePart compositePart_58 = 
      ((CompositePart) compositePart_57.getChildParts().getPart("Body Group 1570"));

    MeshPart meshPart_54 = 
      ((MeshPart) compositePart_58.getChildParts().getPart("Body 1655"));

    PartSurface partSurface_50 = 
      ((PartSurface) meshPart_54.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_55 = 
      ((MeshPart) compositePart_58.getChildParts().getPart("Body 1656"));

    PartSurface partSurface_51 = 
      ((PartSurface) meshPart_55.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_59 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1005_FW_InnerFlaps_1_COPY; refset MODEL"));

    CompositePart compositePart_60 = 
      ((CompositePart) compositePart_59.getChildParts().getPart("Body Group 1576"));

    MeshPart meshPart_56 = 
      ((MeshPart) compositePart_60.getChildParts().getPart("Body 1661"));

    PartSurface partSurface_52 = 
      ((PartSurface) meshPart_56.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_57 = 
      ((MeshPart) compositePart_60.getChildParts().getPart("Body 1662"));

    PartSurface partSurface_53 = 
      ((PartSurface) meshPart_57.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_61 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1006_FW_InnerFlaps_2_COPY; refset MODEL"));

    CompositePart compositePart_62 = 
      ((CompositePart) compositePart_61.getChildParts().getPart("Body Group 1572"));

    MeshPart meshPart_58 = 
      ((MeshPart) compositePart_62.getChildParts().getPart("Body 1657"));

    PartSurface partSurface_54 = 
      ((PartSurface) meshPart_58.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_59 = 
      ((MeshPart) compositePart_62.getChildParts().getPart("Body 1658"));

    PartSurface partSurface_55 = 
      ((PartSurface) meshPart_59.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_63 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1007_FW_InnerEP_COPY; refset PF20"));

    CompositePart compositePart_64 = 
      ((CompositePart) compositePart_63.getChildParts().getPart("Body Group 1582"));

    MeshPart meshPart_60 = 
      ((MeshPart) compositePart_64.getChildParts().getPart("Body 1667"));

    PartSurface partSurface_56 = 
      ((PartSurface) meshPart_60.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_61 = 
      ((MeshPart) compositePart_64.getChildParts().getPart("Body 1668"));

    PartSurface partSurface_57 = 
      ((PartSurface) meshPart_61.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_65 = 
      ((CompositePart) compositePart_50.getChildParts().getPart("PF20_AER_1008_FW_Outer_Endplates_COPY; refset PF20"));

    CompositePart compositePart_66 = 
      ((CompositePart) compositePart_65.getChildParts().getPart("Body Group 1578"));

    MeshPart meshPart_62 = 
      ((MeshPart) compositePart_66.getChildParts().getPart("Body 1663"));

    PartSurface partSurface_58 = 
      ((PartSurface) meshPart_62.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_63 = 
      ((MeshPart) compositePart_66.getChildParts().getPart("Body 1664"));

    PartSurface partSurface_59 = 
      ((PartSurface) meshPart_63.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_67 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("RW"));

    CompositePart compositePart_68 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20"));

    CompositePart compositePart_69 = 
      ((CompositePart) compositePart_68.getChildParts().getPart("Body Group 1472"));

    MeshPart meshPart_64 = 
      ((MeshPart) compositePart_69.getChildParts().getPart("CHAMFER1"));

    PartSurface partSurface_60 = 
      ((PartSurface) meshPart_64.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_65 = 
      ((MeshPart) compositePart_69.getChildParts().getPart("CUT-EXTRUDE2"));

    PartSurface partSurface_61 = 
      ((PartSurface) meshPart_65.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_70 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20 2"));

    CompositePart compositePart_71 = 
      ((CompositePart) compositePart_70.getChildParts().getPart("Body Group 1478"));

    MeshPart meshPart_66 = 
      ((MeshPart) compositePart_71.getChildParts().getPart("CHAMFER1 2"));

    PartSurface partSurface_62 = 
      ((PartSurface) meshPart_66.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_67 = 
      ((MeshPart) compositePart_71.getChildParts().getPart("CUT-EXTRUDE2 2"));

    PartSurface partSurface_63 = 
      ((PartSurface) meshPart_67.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_72 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20 3"));

    CompositePart compositePart_73 = 
      ((CompositePart) compositePart_72.getChildParts().getPart("Body Group 1486"));

    MeshPart meshPart_68 = 
      ((MeshPart) compositePart_73.getChildParts().getPart("CHAMFER1 3"));

    PartSurface partSurface_64 = 
      ((PartSurface) meshPart_68.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_69 = 
      ((MeshPart) compositePart_73.getChildParts().getPart("CUT-EXTRUDE2 3"));

    PartSurface partSurface_65 = 
      ((PartSurface) meshPart_69.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_74 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("60645K111_STEEL BALL JOINT ROD END_step_COPY; refset PF20 4"));

    CompositePart compositePart_75 = 
      ((CompositePart) compositePart_74.getChildParts().getPart("Body Group 1491"));

    MeshPart meshPart_70 = 
      ((MeshPart) compositePart_75.getChildParts().getPart("CHAMFER1 4"));

    PartSurface partSurface_66 = 
      ((PartSurface) meshPart_70.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_71 = 
      ((MeshPart) compositePart_75.getChildParts().getPart("CUT-EXTRUDE2 4"));

    PartSurface partSurface_67 = 
      ((PartSurface) meshPart_71.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_76 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20"));

    CompositePart compositePart_77 = 
      ((CompositePart) compositePart_76.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_72 = 
      ((MeshPart) compositePart_77.getChildParts().getPart("BOSS-EXTRUDE1 17"));

    PartSurface partSurface_68 = 
      ((PartSurface) meshPart_72.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_78 = 
      ((CompositePart) compositePart_76.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_73 = 
      ((MeshPart) compositePart_78.getChildParts().getPart("EXTRUDE1 21"));

    PartSurface partSurface_69 = 
      ((PartSurface) meshPart_73.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_79 = 
      ((CompositePart) compositePart_76.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_74 = 
      ((MeshPart) compositePart_79.getChildParts().getPart("REVOLVE2 19"));

    PartSurface partSurface_70 = 
      ((PartSurface) meshPart_74.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_80 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 2"));

    CompositePart compositePart_81 = 
      ((CompositePart) compositePart_80.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_75 = 
      ((MeshPart) compositePart_81.getChildParts().getPart("BOSS-EXTRUDE1 18"));

    PartSurface partSurface_71 = 
      ((PartSurface) meshPart_75.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_82 = 
      ((CompositePart) compositePart_80.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_76 = 
      ((MeshPart) compositePart_82.getChildParts().getPart("EXTRUDE1 22"));

    PartSurface partSurface_72 = 
      ((PartSurface) meshPart_76.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_83 = 
      ((CompositePart) compositePart_80.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_77 = 
      ((MeshPart) compositePart_83.getChildParts().getPart("REVOLVE2 20"));

    PartSurface partSurface_73 = 
      ((PartSurface) meshPart_77.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_84 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 3"));

    CompositePart compositePart_85 = 
      ((CompositePart) compositePart_84.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_78 = 
      ((MeshPart) compositePart_85.getChildParts().getPart("BOSS-EXTRUDE1 19"));

    PartSurface partSurface_74 = 
      ((PartSurface) meshPart_78.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_86 = 
      ((CompositePart) compositePart_84.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_79 = 
      ((MeshPart) compositePart_86.getChildParts().getPart("EXTRUDE1 23"));

    PartSurface partSurface_75 = 
      ((PartSurface) meshPart_79.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_87 = 
      ((CompositePart) compositePart_84.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_80 = 
      ((MeshPart) compositePart_87.getChildParts().getPart("REVOLVE2 21"));

    PartSurface partSurface_76 = 
      ((PartSurface) meshPart_80.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_88 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 4"));

    CompositePart compositePart_89 = 
      ((CompositePart) compositePart_88.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_81 = 
      ((MeshPart) compositePart_89.getChildParts().getPart("BOSS-EXTRUDE1 20"));

    PartSurface partSurface_77 = 
      ((PartSurface) meshPart_81.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_90 = 
      ((CompositePart) compositePart_88.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_82 = 
      ((MeshPart) compositePart_90.getChildParts().getPart("EXTRUDE1 24"));

    PartSurface partSurface_78 = 
      ((PartSurface) meshPart_82.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_91 = 
      ((CompositePart) compositePart_88.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_83 = 
      ((MeshPart) compositePart_91.getChildParts().getPart("REVOLVE2 22"));

    PartSurface partSurface_79 = 
      ((PartSurface) meshPart_83.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_92 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 5"));

    CompositePart compositePart_93 = 
      ((CompositePart) compositePart_92.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_84 = 
      ((MeshPart) compositePart_93.getChildParts().getPart("BOSS-EXTRUDE1 21"));

    PartSurface partSurface_80 = 
      ((PartSurface) meshPart_84.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_94 = 
      ((CompositePart) compositePart_92.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_85 = 
      ((MeshPart) compositePart_94.getChildParts().getPart("EXTRUDE1 25"));

    PartSurface partSurface_81 = 
      ((PartSurface) meshPart_85.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_95 = 
      ((CompositePart) compositePart_92.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_86 = 
      ((MeshPart) compositePart_95.getChildParts().getPart("REVOLVE2 23"));

    PartSurface partSurface_82 = 
      ((PartSurface) meshPart_86.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_96 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 6"));

    CompositePart compositePart_97 = 
      ((CompositePart) compositePart_96.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_87 = 
      ((MeshPart) compositePart_97.getChildParts().getPart("BOSS-EXTRUDE1 22"));

    PartSurface partSurface_83 = 
      ((PartSurface) meshPart_87.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_98 = 
      ((CompositePart) compositePart_96.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_88 = 
      ((MeshPart) compositePart_98.getChildParts().getPart("EXTRUDE1 26"));

    PartSurface partSurface_84 = 
      ((PartSurface) meshPart_88.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_99 = 
      ((CompositePart) compositePart_96.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_89 = 
      ((MeshPart) compositePart_99.getChildParts().getPart("REVOLVE2 24"));

    PartSurface partSurface_85 = 
      ((PartSurface) meshPart_89.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_100 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 7"));

    CompositePart compositePart_101 = 
      ((CompositePart) compositePart_100.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_90 = 
      ((MeshPart) compositePart_101.getChildParts().getPart("BOSS-EXTRUDE1 23"));

    PartSurface partSurface_86 = 
      ((PartSurface) meshPart_90.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_102 = 
      ((CompositePart) compositePart_100.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_91 = 
      ((MeshPart) compositePart_102.getChildParts().getPart("EXTRUDE1 27"));

    PartSurface partSurface_87 = 
      ((PartSurface) meshPart_91.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_103 = 
      ((CompositePart) compositePart_100.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_92 = 
      ((MeshPart) compositePart_103.getChildParts().getPart("REVOLVE2 25"));

    PartSurface partSurface_88 = 
      ((PartSurface) meshPart_92.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_104 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 8"));

    CompositePart compositePart_105 = 
      ((CompositePart) compositePart_104.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_93 = 
      ((MeshPart) compositePart_105.getChildParts().getPart("BOSS-EXTRUDE1 24"));

    PartSurface partSurface_89 = 
      ((PartSurface) meshPart_93.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_106 = 
      ((CompositePart) compositePart_104.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_94 = 
      ((MeshPart) compositePart_106.getChildParts().getPart("EXTRUDE1 28"));

    PartSurface partSurface_90 = 
      ((PartSurface) meshPart_94.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_107 = 
      ((CompositePart) compositePart_104.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_95 = 
      ((MeshPart) compositePart_107.getChildParts().getPart("REVOLVE2 26"));

    PartSurface partSurface_91 = 
      ((PartSurface) meshPart_95.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_108 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 9"));

    CompositePart compositePart_109 = 
      ((CompositePart) compositePart_108.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_96 = 
      ((MeshPart) compositePart_109.getChildParts().getPart("BOSS-EXTRUDE1 25"));

    PartSurface partSurface_92 = 
      ((PartSurface) meshPart_96.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_110 = 
      ((CompositePart) compositePart_108.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_97 = 
      ((MeshPart) compositePart_110.getChildParts().getPart("EXTRUDE1 29"));

    PartSurface partSurface_93 = 
      ((PartSurface) meshPart_97.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_111 = 
      ((CompositePart) compositePart_108.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_98 = 
      ((MeshPart) compositePart_111.getChildParts().getPart("REVOLVE2 27"));

    PartSurface partSurface_94 = 
      ((PartSurface) meshPart_98.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_112 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 10"));

    CompositePart compositePart_113 = 
      ((CompositePart) compositePart_112.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_99 = 
      ((MeshPart) compositePart_113.getChildParts().getPart("BOSS-EXTRUDE1 26"));

    PartSurface partSurface_95 = 
      ((PartSurface) meshPart_99.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_114 = 
      ((CompositePart) compositePart_112.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_100 = 
      ((MeshPart) compositePart_114.getChildParts().getPart("EXTRUDE1 30"));

    PartSurface partSurface_96 = 
      ((PartSurface) meshPart_100.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_115 = 
      ((CompositePart) compositePart_112.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_101 = 
      ((MeshPart) compositePart_115.getChildParts().getPart("REVOLVE2 28"));

    PartSurface partSurface_97 = 
      ((PartSurface) meshPart_101.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_116 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 11"));

    CompositePart compositePart_117 = 
      ((CompositePart) compositePart_116.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_102 = 
      ((MeshPart) compositePart_117.getChildParts().getPart("BOSS-EXTRUDE1 27"));

    PartSurface partSurface_98 = 
      ((PartSurface) meshPart_102.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_118 = 
      ((CompositePart) compositePart_116.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_103 = 
      ((MeshPart) compositePart_118.getChildParts().getPart("EXTRUDE1 31"));

    PartSurface partSurface_99 = 
      ((PartSurface) meshPart_103.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_119 = 
      ((CompositePart) compositePart_116.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_104 = 
      ((MeshPart) compositePart_119.getChildParts().getPart("REVOLVE2 29"));

    PartSurface partSurface_100 = 
      ((PartSurface) meshPart_104.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_120 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 12"));

    CompositePart compositePart_121 = 
      ((CompositePart) compositePart_120.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_105 = 
      ((MeshPart) compositePart_121.getChildParts().getPart("BOSS-EXTRUDE1 28"));

    PartSurface partSurface_101 = 
      ((PartSurface) meshPart_105.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_122 = 
      ((CompositePart) compositePart_120.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_106 = 
      ((MeshPart) compositePart_122.getChildParts().getPart("EXTRUDE1 32"));

    PartSurface partSurface_102 = 
      ((PartSurface) meshPart_106.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_123 = 
      ((CompositePart) compositePart_120.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_107 = 
      ((MeshPart) compositePart_123.getChildParts().getPart("REVOLVE2 30"));

    PartSurface partSurface_103 = 
      ((PartSurface) meshPart_107.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_124 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 13"));

    CompositePart compositePart_125 = 
      ((CompositePart) compositePart_124.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_108 = 
      ((MeshPart) compositePart_125.getChildParts().getPart("BOSS-EXTRUDE1 29"));

    PartSurface partSurface_104 = 
      ((PartSurface) meshPart_108.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_126 = 
      ((CompositePart) compositePart_124.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_109 = 
      ((MeshPart) compositePart_126.getChildParts().getPart("EXTRUDE1 33"));

    PartSurface partSurface_105 = 
      ((PartSurface) meshPart_109.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_127 = 
      ((CompositePart) compositePart_124.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_110 = 
      ((MeshPart) compositePart_127.getChildParts().getPart("REVOLVE2 31"));

    PartSurface partSurface_106 = 
      ((PartSurface) meshPart_110.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_128 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 14"));

    CompositePart compositePart_129 = 
      ((CompositePart) compositePart_128.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_111 = 
      ((MeshPart) compositePart_129.getChildParts().getPart("BOSS-EXTRUDE1 30"));

    PartSurface partSurface_107 = 
      ((PartSurface) meshPart_111.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_130 = 
      ((CompositePart) compositePart_128.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_112 = 
      ((MeshPart) compositePart_130.getChildParts().getPart("EXTRUDE1 34"));

    PartSurface partSurface_108 = 
      ((PartSurface) meshPart_112.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_131 = 
      ((CompositePart) compositePart_128.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_113 = 
      ((MeshPart) compositePart_131.getChildParts().getPart("REVOLVE2 32"));

    PartSurface partSurface_109 = 
      ((PartSurface) meshPart_113.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_132 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 15"));

    CompositePart compositePart_133 = 
      ((CompositePart) compositePart_132.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_114 = 
      ((MeshPart) compositePart_133.getChildParts().getPart("BOSS-EXTRUDE1 31"));

    PartSurface partSurface_110 = 
      ((PartSurface) meshPart_114.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_134 = 
      ((CompositePart) compositePart_132.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_115 = 
      ((MeshPart) compositePart_134.getChildParts().getPart("EXTRUDE1 35"));

    PartSurface partSurface_111 = 
      ((PartSurface) meshPart_115.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_135 = 
      ((CompositePart) compositePart_132.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_116 = 
      ((MeshPart) compositePart_135.getChildParts().getPart("REVOLVE2 33"));

    PartSurface partSurface_112 = 
      ((PartSurface) meshPart_116.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_136 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 16"));

    CompositePart compositePart_137 = 
      ((CompositePart) compositePart_136.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_117 = 
      ((MeshPart) compositePart_137.getChildParts().getPart("BOSS-EXTRUDE1 32"));

    PartSurface partSurface_113 = 
      ((PartSurface) meshPart_117.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_138 = 
      ((CompositePart) compositePart_136.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_118 = 
      ((MeshPart) compositePart_138.getChildParts().getPart("EXTRUDE1 36"));

    PartSurface partSurface_114 = 
      ((PartSurface) meshPart_118.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_139 = 
      ((CompositePart) compositePart_136.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_119 = 
      ((MeshPart) compositePart_139.getChildParts().getPart("REVOLVE2 34"));

    PartSurface partSurface_115 = 
      ((PartSurface) meshPart_119.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_140 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0002_Tierod_Bracket_Universal_COPY; refset PF20"));

    MeshPart meshPart_120 = 
      ((MeshPart) compositePart_140.getChildParts().getPart("Body 1594"));

    PartSurface partSurface_116 = 
      ((PartSurface) meshPart_120.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_141 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_0002_Tierod_Bracket_Universal_COPY; refset PF20 2"));

    MeshPart meshPart_121 = 
      ((MeshPart) compositePart_141.getChildParts().getPart("Body 1608"));

    PartSurface partSurface_117 = 
      ((PartSurface) meshPart_121.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_142 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3001_FW_MainElement_COPY; refset PF20"));

    CompositePart compositePart_143 = 
      ((CompositePart) compositePart_142.getChildParts().getPart("Body Group 1350"));

    MeshPart meshPart_122 = 
      ((MeshPart) compositePart_143.getChildParts().getPart("Body 1515"));

    PartSurface partSurface_118 = 
      ((PartSurface) meshPart_122.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_123 = 
      ((MeshPart) compositePart_143.getChildParts().getPart("Body 1516"));

    PartSurface partSurface_119 = 
      ((PartSurface) meshPart_123.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_144 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3002_RW_Flap_1_Upper_COPY; refset PF20"));

    CompositePart compositePart_145 = 
      ((CompositePart) compositePart_144.getChildParts().getPart("Body Group 1352"));

    MeshPart meshPart_124 = 
      ((MeshPart) compositePart_145.getChildParts().getPart("Body 1517"));

    PartSurface partSurface_120 = 
      ((PartSurface) meshPart_124.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_125 = 
      ((MeshPart) compositePart_145.getChildParts().getPart("Body 1518"));

    PartSurface partSurface_121 = 
      ((PartSurface) meshPart_125.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_146 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3003_RW_Flap_2_COPY; refset PF20"));

    CompositePart compositePart_147 = 
      ((CompositePart) compositePart_146.getChildParts().getPart("Body Group 1358"));

    MeshPart meshPart_126 = 
      ((MeshPart) compositePart_147.getChildParts().getPart("Body 1523"));

    PartSurface partSurface_122 = 
      ((PartSurface) meshPart_126.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_127 = 
      ((MeshPart) compositePart_147.getChildParts().getPart("Body 1524"));

    PartSurface partSurface_123 = 
      ((PartSurface) meshPart_127.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_148 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3004_RW_Flap_3_COPY; refset PF20"));

    CompositePart compositePart_149 = 
      ((CompositePart) compositePart_148.getChildParts().getPart("Body Group 1354"));

    MeshPart meshPart_128 = 
      ((MeshPart) compositePart_149.getChildParts().getPart("Body 1519"));

    PartSurface partSurface_124 = 
      ((PartSurface) meshPart_128.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_129 = 
      ((MeshPart) compositePart_149.getChildParts().getPart("Body 1520"));

    PartSurface partSurface_125 = 
      ((PartSurface) meshPart_129.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_150 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3005_RW_Endplates_COPY; refset PF20"));

    CompositePart compositePart_151 = 
      ((CompositePart) compositePart_150.getChildParts().getPart("Body Group 1356"));

    MeshPart meshPart_130 = 
      ((MeshPart) compositePart_151.getChildParts().getPart("Body 1521"));

    PartSurface partSurface_126 = 
      ((PartSurface) meshPart_130.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_131 = 
      ((MeshPart) compositePart_151.getChildParts().getPart("Body 1522"));

    PartSurface partSurface_127 = 
      ((PartSurface) meshPart_131.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_152 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3100_RW_Swan_COPY; refset PF20"));

    CompositePart compositePart_153 = 
      ((CompositePart) compositePart_152.getChildParts().getPart("Body Group 1360"));

    MeshPart meshPart_132 = 
      ((MeshPart) compositePart_153.getChildParts().getPart("Body 1525"));

    PartSurface partSurface_128 = 
      ((PartSurface) meshPart_132.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_133 = 
      ((MeshPart) compositePart_153.getChildParts().getPart("Body 1526"));

    PartSurface partSurface_129 = 
      ((PartSurface) meshPart_133.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_154 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3200_RW_Tabs_COPY; refset PF20"));

    CompositePart compositePart_155 = 
      ((CompositePart) compositePart_154.getChildParts().getPart("PF20_AER_3201_Swan_Tab_COPY; refset PF20"));

    MeshPart meshPart_134 = 
      ((MeshPart) compositePart_155.getChildParts().getPart("Body 1527"));

    PartSurface partSurface_130 = 
      ((PartSurface) meshPart_134.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_156 = 
      ((CompositePart) compositePart_154.getChildParts().getPart("PF20_AER_3201_Swan_Tab_COPY; refset PF20 2"));

    MeshPart meshPart_135 = 
      ((MeshPart) compositePart_156.getChildParts().getPart("Body 1528"));

    PartSurface partSurface_131 = 
      ((PartSurface) meshPart_135.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_157 = 
      ((CompositePart) compositePart_154.getChildParts().getPart("PF20_AER_3201_Swan_Tab_COPY; refset PF20 3"));

    MeshPart meshPart_136 = 
      ((MeshPart) compositePart_157.getChildParts().getPart("Body 1529"));

    PartSurface partSurface_132 = 
      ((PartSurface) meshPart_136.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_158 = 
      ((CompositePart) compositePart_154.getChildParts().getPart("PF20_AER_3201_Swan_Tab_COPY; refset PF20 4"));

    MeshPart meshPart_137 = 
      ((MeshPart) compositePart_158.getChildParts().getPart("Body 1530"));

    PartSurface partSurface_133 = 
      ((PartSurface) meshPart_137.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_159 = 
      ((CompositePart) compositePart_154.getChildParts().getPart("PF20_AER_3203_Tierod_Tab_Lat_COPY; refset PF20"));

    CompositePart compositePart_160 = 
      ((CompositePart) compositePart_159.getChildParts().getPart("Body Group 1367"));

    MeshPart meshPart_138 = 
      ((MeshPart) compositePart_160.getChildParts().getPart("Body 1531"));

    PartSurface partSurface_134 = 
      ((PartSurface) meshPart_138.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_139 = 
      ((MeshPart) compositePart_160.getChildParts().getPart("Body 1532"));

    PartSurface partSurface_135 = 
      ((PartSurface) meshPart_139.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_140 = 
      ((MeshPart) compositePart_160.getChildParts().getPart("Body 1533"));

    PartSurface partSurface_136 = 
      ((PartSurface) meshPart_140.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_141 = 
      ((MeshPart) compositePart_160.getChildParts().getPart("Body 1534"));

    PartSurface partSurface_137 = 
      ((PartSurface) meshPart_141.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_161 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3300_RW_Ribs_COPY; refset PF20"));

    CompositePart compositePart_162 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3301_RW_Rib_Center_COPY; refset PF20"));

    MeshPart meshPart_142 = 
      ((MeshPart) compositePart_162.getChildParts().getPart("Body 1535"));

    PartSurface partSurface_138 = 
      ((PartSurface) meshPart_142.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_163 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3301_RW_Rib_Center_COPY; refset PF20 2"));

    MeshPart meshPart_143 = 
      ((MeshPart) compositePart_163.getChildParts().getPart("Body 1541"));

    PartSurface partSurface_139 = 
      ((PartSurface) meshPart_143.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_164 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3302_RW_Rib_Outer_COPY; refset PF20"));

    MeshPart meshPart_144 = 
      ((MeshPart) compositePart_164.getChildParts().getPart("Body 1536"));

    PartSurface partSurface_140 = 
      ((PartSurface) meshPart_144.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_165 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3302_RW_Rib_Outer_COPY; refset PF20 2"));

    MeshPart meshPart_145 = 
      ((MeshPart) compositePart_165.getChildParts().getPart("Body 1538"));

    PartSurface partSurface_141 = 
      ((PartSurface) meshPart_145.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_166 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3303_RW_Flap_Rib_COPY; refset PF20"));

    MeshPart meshPart_146 = 
      ((MeshPart) compositePart_166.getChildParts().getPart("Body 1537"));

    PartSurface partSurface_142 = 
      ((PartSurface) meshPart_146.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_167 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3303_RW_Flap_Rib_COPY; refset PF20 2"));

    MeshPart meshPart_147 = 
      ((MeshPart) compositePart_167.getChildParts().getPart("Body 1539"));

    PartSurface partSurface_143 = 
      ((PartSurface) meshPart_147.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_168 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3303_RW_Flap_Rib_COPY; refset PF20 3"));

    MeshPart meshPart_148 = 
      ((MeshPart) compositePart_168.getChildParts().getPart("Body 1540"));

    PartSurface partSurface_144 = 
      ((PartSurface) meshPart_148.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_169 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3303_RW_Flap_Rib_COPY; refset PF20 4"));

    MeshPart meshPart_149 = 
      ((MeshPart) compositePart_169.getChildParts().getPart("Body 1542"));

    PartSurface partSurface_145 = 
      ((PartSurface) meshPart_149.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_170 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3304_RW_Flap_Rib_Accel_COPY; refset PF20"));

    MeshPart meshPart_150 = 
      ((MeshPart) compositePart_170.getChildParts().getPart("Body 1543"));

    PartSurface partSurface_146 = 
      ((PartSurface) meshPart_150.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_171 = 
      ((CompositePart) compositePart_161.getChildParts().getPart("PF20_AER_3304_RW_Flap_Rib_Accel_COPY; refset PF20 2"));

    MeshPart meshPart_151 = 
      ((MeshPart) compositePart_171.getChildParts().getPart("Body 1544"));

    PartSurface partSurface_147 = 
      ((PartSurface) meshPart_151.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_172 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20"));

    MeshPart meshPart_152 = 
      ((MeshPart) compositePart_172.getChildParts().getPart("Body 1593"));

    PartSurface partSurface_148 = 
      ((PartSurface) meshPart_152.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_173 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20 2"));

    MeshPart meshPart_153 = 
      ((MeshPart) compositePart_173.getChildParts().getPart("Body 1597"));

    PartSurface partSurface_149 = 
      ((PartSurface) meshPart_153.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_174 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20 3"));

    MeshPart meshPart_154 = 
      ((MeshPart) compositePart_174.getChildParts().getPart("Body 1600"));

    PartSurface partSurface_150 = 
      ((PartSurface) meshPart_154.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_175 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3302_Tierod_Insert_COPY; refset PF20 4"));

    MeshPart meshPart_155 = 
      ((MeshPart) compositePart_175.getChildParts().getPart("Body 1605"));

    PartSurface partSurface_151 = 
      ((PartSurface) meshPart_155.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_176 = 
      ((CompositePart) compositePart_67.getChildParts().getPart("PF20_AER_3303_Tierod_Tube_COPY; refset PF20"));

    CompositePart compositePart_177 = 
      ((CompositePart) compositePart_176.getChildParts().getPart("Body Group 1483"));

    MeshPart meshPart_156 = 
      ((MeshPart) compositePart_177.getChildParts().getPart("Body 1601"));

    PartSurface partSurface_152 = 
      ((PartSurface) meshPart_156.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_157 = 
      ((MeshPart) compositePart_177.getChildParts().getPart("Body 1602"));

    PartSurface partSurface_153 = 
      ((PartSurface) meshPart_157.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_178 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("SW"));

    CompositePart compositePart_179 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("97447A110_3s32_126-250_AL_blind_rivet_COPY; refset MODEL"));

    MeshPart meshPart_158 = 
      ((MeshPart) compositePart_179.getChildParts().getPart("FILLET2 7"));

    PartSurface partSurface_154 = 
      ((PartSurface) meshPart_158.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_155 = 
      ((PartSurface) meshPart_158.getPartSurfaceManager().getPartSurface("ColoredFace15"));

    PartSurface partSurface_156 = 
      ((PartSurface) meshPart_158.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_180 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("97447A110_3s32_126-250_AL_blind_rivet_COPY; refset MODEL 2"));

    MeshPart meshPart_159 = 
      ((MeshPart) compositePart_180.getChildParts().getPart("FILLET2 8"));

    PartSurface partSurface_157 = 
      ((PartSurface) meshPart_159.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_158 = 
      ((PartSurface) meshPart_159.getPartSurfaceManager().getPartSurface("ColoredFace15"));

    PartSurface partSurface_159 = 
      ((PartSurface) meshPart_159.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_181 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("97447A110_3s32_126-250_AL_blind_rivet_COPY; refset MODEL 3"));

    MeshPart meshPart_160 = 
      ((MeshPart) compositePart_181.getChildParts().getPart("FILLET2 9"));

    PartSurface partSurface_160 = 
      ((PartSurface) meshPart_160.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_161 = 
      ((PartSurface) meshPart_160.getPartSurfaceManager().getPartSurface("ColoredFace15"));

    PartSurface partSurface_162 = 
      ((PartSurface) meshPart_160.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_182 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("97447A110_3s32_126-250_AL_blind_rivet_COPY; refset MODEL 4"));

    MeshPart meshPart_161 = 
      ((MeshPart) compositePart_182.getChildParts().getPart("FILLET2 10"));

    PartSurface partSurface_163 = 
      ((PartSurface) meshPart_161.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_164 = 
      ((PartSurface) meshPart_161.getPartSurfaceManager().getPartSurface("ColoredFace15"));

    PartSurface partSurface_165 = 
      ((PartSurface) meshPart_161.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_183 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("PF20_AER_2001W_updated_rules_COPY; refset PF20"));

    CompositePart compositePart_184 = 
      ((CompositePart) compositePart_183.getChildParts().getPart("Body Group 1157"));

    MeshPart meshPart_162 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1365"));

    PartSurface partSurface_166 = 
      ((PartSurface) meshPart_162.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_163 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1366"));

    PartSurface partSurface_167 = 
      ((PartSurface) meshPart_163.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_164 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1367"));

    PartSurface partSurface_168 = 
      ((PartSurface) meshPart_164.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_165 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1368"));

    PartSurface partSurface_169 = 
      ((PartSurface) meshPart_165.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_166 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1369"));

    PartSurface partSurface_170 = 
      ((PartSurface) meshPart_166.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_167 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1370"));

    PartSurface partSurface_171 = 
      ((PartSurface) meshPart_167.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_168 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1371"));

    PartSurface partSurface_172 = 
      ((PartSurface) meshPart_168.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_169 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1372"));

    PartSurface partSurface_173 = 
      ((PartSurface) meshPart_169.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_170 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1373"));

    PartSurface partSurface_174 = 
      ((PartSurface) meshPart_170.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_171 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1374"));

    PartSurface partSurface_175 = 
      ((PartSurface) meshPart_171.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_172 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1375"));

    PartSurface partSurface_176 = 
      ((PartSurface) meshPart_172.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_173 = 
      ((MeshPart) compositePart_184.getChildParts().getPart("Body 1376"));

    PartSurface partSurface_177 = 
      ((PartSurface) meshPart_173.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_185 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("PF20_AER_2200_Mounting_COPY; refset PF20"));

    CompositePart compositePart_186 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20"));

    CompositePart compositePart_187 = 
      ((CompositePart) compositePart_186.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_174 = 
      ((MeshPart) compositePart_187.getChildParts().getPart("BOSS-EXTRUDE1"));

    PartSurface partSurface_178 = 
      ((PartSurface) meshPart_174.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_188 = 
      ((CompositePart) compositePart_186.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_175 = 
      ((MeshPart) compositePart_188.getChildParts().getPart("EXTRUDE1 5"));

    PartSurface partSurface_179 = 
      ((PartSurface) meshPart_175.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_189 = 
      ((CompositePart) compositePart_186.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_176 = 
      ((MeshPart) compositePart_189.getChildParts().getPart("REVOLVE2 3"));

    PartSurface partSurface_180 = 
      ((PartSurface) meshPart_176.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_190 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 2"));

    CompositePart compositePart_191 = 
      ((CompositePart) compositePart_190.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_177 = 
      ((MeshPart) compositePart_191.getChildParts().getPart("BOSS-EXTRUDE1 2"));

    PartSurface partSurface_181 = 
      ((PartSurface) meshPart_177.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_192 = 
      ((CompositePart) compositePart_190.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_178 = 
      ((MeshPart) compositePart_192.getChildParts().getPart("EXTRUDE1 6"));

    PartSurface partSurface_182 = 
      ((PartSurface) meshPart_178.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_193 = 
      ((CompositePart) compositePart_190.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_179 = 
      ((MeshPart) compositePart_193.getChildParts().getPart("REVOLVE2 4"));

    PartSurface partSurface_183 = 
      ((PartSurface) meshPart_179.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_194 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 3"));

    CompositePart compositePart_195 = 
      ((CompositePart) compositePart_194.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_180 = 
      ((MeshPart) compositePart_195.getChildParts().getPart("BOSS-EXTRUDE1 3"));

    PartSurface partSurface_184 = 
      ((PartSurface) meshPart_180.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_196 = 
      ((CompositePart) compositePart_194.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_181 = 
      ((MeshPart) compositePart_196.getChildParts().getPart("EXTRUDE1 7"));

    PartSurface partSurface_185 = 
      ((PartSurface) meshPart_181.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_197 = 
      ((CompositePart) compositePart_194.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_182 = 
      ((MeshPart) compositePart_197.getChildParts().getPart("REVOLVE2 5"));

    PartSurface partSurface_186 = 
      ((PartSurface) meshPart_182.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_198 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 4"));

    CompositePart compositePart_199 = 
      ((CompositePart) compositePart_198.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_183 = 
      ((MeshPart) compositePart_199.getChildParts().getPart("BOSS-EXTRUDE1 4"));

    PartSurface partSurface_187 = 
      ((PartSurface) meshPart_183.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_200 = 
      ((CompositePart) compositePart_198.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_184 = 
      ((MeshPart) compositePart_200.getChildParts().getPart("EXTRUDE1 8"));

    PartSurface partSurface_188 = 
      ((PartSurface) meshPart_184.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_201 = 
      ((CompositePart) compositePart_198.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_185 = 
      ((MeshPart) compositePart_201.getChildParts().getPart("REVOLVE2 6"));

    PartSurface partSurface_189 = 
      ((PartSurface) meshPart_185.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_202 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 5"));

    CompositePart compositePart_203 = 
      ((CompositePart) compositePart_202.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_186 = 
      ((MeshPart) compositePart_203.getChildParts().getPart("BOSS-EXTRUDE1 5"));

    PartSurface partSurface_190 = 
      ((PartSurface) meshPart_186.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_204 = 
      ((CompositePart) compositePart_202.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_187 = 
      ((MeshPart) compositePart_204.getChildParts().getPart("EXTRUDE1 9"));

    PartSurface partSurface_191 = 
      ((PartSurface) meshPart_187.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_205 = 
      ((CompositePart) compositePart_202.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_188 = 
      ((MeshPart) compositePart_205.getChildParts().getPart("REVOLVE2 7"));

    PartSurface partSurface_192 = 
      ((PartSurface) meshPart_188.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_206 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 6"));

    CompositePart compositePart_207 = 
      ((CompositePart) compositePart_206.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_189 = 
      ((MeshPart) compositePart_207.getChildParts().getPart("BOSS-EXTRUDE1 6"));

    PartSurface partSurface_193 = 
      ((PartSurface) meshPart_189.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_208 = 
      ((CompositePart) compositePart_206.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_190 = 
      ((MeshPart) compositePart_208.getChildParts().getPart("EXTRUDE1 10"));

    PartSurface partSurface_194 = 
      ((PartSurface) meshPart_190.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_209 = 
      ((CompositePart) compositePart_206.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_191 = 
      ((MeshPart) compositePart_209.getChildParts().getPart("REVOLVE2 8"));

    PartSurface partSurface_195 = 
      ((PartSurface) meshPart_191.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_210 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 7"));

    CompositePart compositePart_211 = 
      ((CompositePart) compositePart_210.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_192 = 
      ((MeshPart) compositePart_211.getChildParts().getPart("BOSS-EXTRUDE1 7"));

    PartSurface partSurface_196 = 
      ((PartSurface) meshPart_192.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_212 = 
      ((CompositePart) compositePart_210.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_193 = 
      ((MeshPart) compositePart_212.getChildParts().getPart("EXTRUDE1 11"));

    PartSurface partSurface_197 = 
      ((PartSurface) meshPart_193.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_213 = 
      ((CompositePart) compositePart_210.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_194 = 
      ((MeshPart) compositePart_213.getChildParts().getPart("REVOLVE2 9"));

    PartSurface partSurface_198 = 
      ((PartSurface) meshPart_194.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_214 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 8"));

    CompositePart compositePart_215 = 
      ((CompositePart) compositePart_214.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_195 = 
      ((MeshPart) compositePart_215.getChildParts().getPart("BOSS-EXTRUDE1 8"));

    PartSurface partSurface_199 = 
      ((PartSurface) meshPart_195.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_216 = 
      ((CompositePart) compositePart_214.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_196 = 
      ((MeshPart) compositePart_216.getChildParts().getPart("EXTRUDE1 12"));

    PartSurface partSurface_200 = 
      ((PartSurface) meshPart_196.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_217 = 
      ((CompositePart) compositePart_214.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_197 = 
      ((MeshPart) compositePart_217.getChildParts().getPart("REVOLVE2 10"));

    PartSurface partSurface_201 = 
      ((PartSurface) meshPart_197.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_218 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 9"));

    CompositePart compositePart_219 = 
      ((CompositePart) compositePart_218.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_198 = 
      ((MeshPart) compositePart_219.getChildParts().getPart("BOSS-EXTRUDE1 9"));

    PartSurface partSurface_202 = 
      ((PartSurface) meshPart_198.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_220 = 
      ((CompositePart) compositePart_218.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_199 = 
      ((MeshPart) compositePart_220.getChildParts().getPart("EXTRUDE1 13"));

    PartSurface partSurface_203 = 
      ((PartSurface) meshPart_199.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_221 = 
      ((CompositePart) compositePart_218.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_200 = 
      ((MeshPart) compositePart_221.getChildParts().getPart("REVOLVE2 11"));

    PartSurface partSurface_204 = 
      ((PartSurface) meshPart_200.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_222 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 10"));

    CompositePart compositePart_223 = 
      ((CompositePart) compositePart_222.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_201 = 
      ((MeshPart) compositePart_223.getChildParts().getPart("BOSS-EXTRUDE1 10"));

    PartSurface partSurface_205 = 
      ((PartSurface) meshPart_201.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_224 = 
      ((CompositePart) compositePart_222.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_202 = 
      ((MeshPart) compositePart_224.getChildParts().getPart("EXTRUDE1 14"));

    PartSurface partSurface_206 = 
      ((PartSurface) meshPart_202.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_225 = 
      ((CompositePart) compositePart_222.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_203 = 
      ((MeshPart) compositePart_225.getChildParts().getPart("REVOLVE2 12"));

    PartSurface partSurface_207 = 
      ((PartSurface) meshPart_203.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_226 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 11"));

    CompositePart compositePart_227 = 
      ((CompositePart) compositePart_226.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_204 = 
      ((MeshPart) compositePart_227.getChildParts().getPart("BOSS-EXTRUDE1 11"));

    PartSurface partSurface_208 = 
      ((PartSurface) meshPart_204.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_228 = 
      ((CompositePart) compositePart_226.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_205 = 
      ((MeshPart) compositePart_228.getChildParts().getPart("EXTRUDE1 15"));

    PartSurface partSurface_209 = 
      ((PartSurface) meshPart_205.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_229 = 
      ((CompositePart) compositePart_226.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_206 = 
      ((MeshPart) compositePart_229.getChildParts().getPart("REVOLVE2 13"));

    PartSurface partSurface_210 = 
      ((PartSurface) meshPart_206.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_230 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 12"));

    CompositePart compositePart_231 = 
      ((CompositePart) compositePart_230.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_207 = 
      ((MeshPart) compositePart_231.getChildParts().getPart("BOSS-EXTRUDE1 12"));

    PartSurface partSurface_211 = 
      ((PartSurface) meshPart_207.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_232 = 
      ((CompositePart) compositePart_230.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_208 = 
      ((MeshPart) compositePart_232.getChildParts().getPart("EXTRUDE1 16"));

    PartSurface partSurface_212 = 
      ((PartSurface) meshPart_208.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_233 = 
      ((CompositePart) compositePart_230.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_209 = 
      ((MeshPart) compositePart_233.getChildParts().getPart("REVOLVE2 14"));

    PartSurface partSurface_213 = 
      ((PartSurface) meshPart_209.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_234 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 13"));

    CompositePart compositePart_235 = 
      ((CompositePart) compositePart_234.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_210 = 
      ((MeshPart) compositePart_235.getChildParts().getPart("BOSS-EXTRUDE1 13"));

    PartSurface partSurface_214 = 
      ((PartSurface) meshPart_210.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_236 = 
      ((CompositePart) compositePart_234.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_211 = 
      ((MeshPart) compositePart_236.getChildParts().getPart("EXTRUDE1 17"));

    PartSurface partSurface_215 = 
      ((PartSurface) meshPart_211.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_237 = 
      ((CompositePart) compositePart_234.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_212 = 
      ((MeshPart) compositePart_237.getChildParts().getPart("REVOLVE2 15"));

    PartSurface partSurface_216 = 
      ((PartSurface) meshPart_212.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_238 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 14"));

    CompositePart compositePart_239 = 
      ((CompositePart) compositePart_238.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_213 = 
      ((MeshPart) compositePart_239.getChildParts().getPart("BOSS-EXTRUDE1 14"));

    PartSurface partSurface_217 = 
      ((PartSurface) meshPart_213.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_240 = 
      ((CompositePart) compositePart_238.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_214 = 
      ((MeshPart) compositePart_240.getChildParts().getPart("EXTRUDE1 18"));

    PartSurface partSurface_218 = 
      ((PartSurface) meshPart_214.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_241 = 
      ((CompositePart) compositePart_238.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_215 = 
      ((MeshPart) compositePart_241.getChildParts().getPart("REVOLVE2 16"));

    PartSurface partSurface_219 = 
      ((PartSurface) meshPart_215.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_242 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 15"));

    CompositePart compositePart_243 = 
      ((CompositePart) compositePart_242.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_216 = 
      ((MeshPart) compositePart_243.getChildParts().getPart("BOSS-EXTRUDE1 15"));

    PartSurface partSurface_220 = 
      ((PartSurface) meshPart_216.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_244 = 
      ((CompositePart) compositePart_242.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_217 = 
      ((MeshPart) compositePart_244.getChildParts().getPart("EXTRUDE1 19"));

    PartSurface partSurface_221 = 
      ((PartSurface) meshPart_217.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_245 = 
      ((CompositePart) compositePart_242.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_218 = 
      ((MeshPart) compositePart_245.getChildParts().getPart("REVOLVE2 17"));

    PartSurface partSurface_222 = 
      ((PartSurface) meshPart_218.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_246 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_0001_Wing_Standard_Bolts_COPY; refset PF20 16"));

    CompositePart compositePart_247 = 
      ((CompositePart) compositePart_246.getChildParts().getPart("90377A121_BLACK-OXIDE 18-8 STAINLESS STEEL FLAT WASHER_step_COPY; refset MODEL"));

    MeshPart meshPart_219 = 
      ((MeshPart) compositePart_247.getChildParts().getPart("BOSS-EXTRUDE1 16"));

    PartSurface partSurface_223 = 
      ((PartSurface) meshPart_219.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_248 = 
      ((CompositePart) compositePart_246.getChildParts().getPart("92510A040_ALUM UNTHRD SPACER_step_COPY; refset MODEL"));

    MeshPart meshPart_220 = 
      ((MeshPart) compositePart_248.getChildParts().getPart("EXTRUDE1 20"));

    PartSurface partSurface_224 = 
      ((PartSurface) meshPart_220.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_249 = 
      ((CompositePart) compositePart_246.getChildParts().getPart("97763A335_18-8 SS BUTTON HEAD HEX DRIVE SCREWS_step_COPY; refset MODEL"));

    MeshPart meshPart_221 = 
      ((MeshPart) compositePart_249.getChildParts().getPart("REVOLVE2 18"));

    PartSurface partSurface_225 = 
      ((PartSurface) meshPart_221.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_250 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2201_SW_Brace_Aft_COPY; refset PF20"));

    MeshPart meshPart_222 = 
      ((MeshPart) compositePart_250.getChildParts().getPart("Body 1310"));

    PartSurface partSurface_226 = 
      ((PartSurface) meshPart_222.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_251 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2201_SW_Brace_Aft_RH_COPY; refset PF20"));

    MeshPart meshPart_223 = 
      ((MeshPart) compositePart_251.getChildParts().getPart("Body 1305"));

    PartSurface partSurface_227 = 
      ((PartSurface) meshPart_223.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_252 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2202_SW_Brace_Front_COPY; refset PF20"));

    MeshPart meshPart_224 = 
      ((MeshPart) compositePart_252.getChildParts().getPart("Body 1311"));

    PartSurface partSurface_228 = 
      ((PartSurface) meshPart_224.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_253 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2202_SW_Brace_Front_RH_COPY; refset PF20"));

    MeshPart meshPart_225 = 
      ((MeshPart) compositePart_253.getChildParts().getPart("Body 1306"));

    PartSurface partSurface_229 = 
      ((PartSurface) meshPart_225.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_254 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2203_UT_Brace_Aft_COPY; refset PF20"));

    MeshPart meshPart_226 = 
      ((MeshPart) compositePart_254.getChildParts().getPart("Body 1312"));

    PartSurface partSurface_230 = 
      ((PartSurface) meshPart_226.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_255 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2203_UT_Brace_Aft_RH_COPY; refset PF20"));

    MeshPart meshPart_227 = 
      ((MeshPart) compositePart_255.getChildParts().getPart("Body 1307"));

    PartSurface partSurface_231 = 
      ((PartSurface) meshPart_227.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_256 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2204_SW_Endplate_rules_compat_COPY; refset PF20"));

    CompositePart compositePart_257 = 
      ((CompositePart) compositePart_256.getChildParts().getPart("Body Group 1084"));

    MeshPart meshPart_228 = 
      ((MeshPart) compositePart_257.getChildParts().getPart("Body 1322"));

    PartSurface partSurface_232 = 
      ((PartSurface) meshPart_228.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_229 = 
      ((MeshPart) compositePart_257.getChildParts().getPart("Body 1323"));

    PartSurface partSurface_233 = 
      ((PartSurface) meshPart_229.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_258 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2301_Std_Tab_COPY; refset PF20"));

    MeshPart meshPart_230 = 
      ((MeshPart) compositePart_258.getChildParts().getPart("Body 1308"));

    PartSurface partSurface_234 = 
      ((PartSurface) meshPart_230.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_259 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2301_Std_Tab_COPY; refset PF20 2"));

    MeshPart meshPart_231 = 
      ((MeshPart) compositePart_259.getChildParts().getPart("Body 1309"));

    PartSurface partSurface_235 = 
      ((PartSurface) meshPart_231.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_260 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2301_Std_Tab_RH_1_COPY; refset PF20"));

    MeshPart meshPart_232 = 
      ((MeshPart) compositePart_260.getChildParts().getPart("Body 1303"));

    PartSurface partSurface_236 = 
      ((PartSurface) meshPart_232.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_261 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2301_Std_Tab_RH_COPY; refset PF20"));

    MeshPart meshPart_233 = 
      ((MeshPart) compositePart_261.getChildParts().getPart("Body 1304"));

    PartSurface partSurface_237 = 
      ((PartSurface) meshPart_233.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_262 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2302_ANT_UT_COPY; refset PF20"));

    MeshPart meshPart_234 = 
      ((MeshPart) compositePart_262.getChildParts().getPart("Body 1324"));

    PartSurface partSurface_238 = 
      ((PartSurface) meshPart_234.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_263 = 
      ((CompositePart) compositePart_185.getChildParts().getPart("PF20_AER_2302_ANT_UT_COPY; refset PF20 2"));

    MeshPart meshPart_235 = 
      ((MeshPart) compositePart_263.getChildParts().getPart("Body 1325"));

    PartSurface partSurface_239 = 
      ((PartSurface) meshPart_235.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_264 = 
      ((CompositePart) compositePart_178.getChildParts().getPart("PF20_AER_2300_SW_Ribs_COPY; refset PF20"));

    CompositePart compositePart_265 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("Body Group 1051"));

    MeshPart meshPart_236 = 
      ((MeshPart) compositePart_265.getChildParts().getPart("Body 1299"));

    PartSurface partSurface_240 = 
      ((PartSurface) meshPart_236.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_237 = 
      ((MeshPart) compositePart_265.getChildParts().getPart("Body 1300"));

    PartSurface partSurface_241 = 
      ((PartSurface) meshPart_237.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_238 = 
      ((MeshPart) compositePart_265.getChildParts().getPart("Body 1301"));

    PartSurface partSurface_242 = 
      ((PartSurface) meshPart_238.getPartSurfaceManager().getPartSurface("Default"));

    MeshPart meshPart_239 = 
      ((MeshPart) compositePart_265.getChildParts().getPart("Body 1302"));

    PartSurface partSurface_243 = 
      ((PartSurface) meshPart_239.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_266 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2301_SW_Rib_1_COPY; refset PF20"));

    MeshPart meshPart_240 = 
      ((MeshPart) compositePart_266.getChildParts().getPart("Body 1293"));

    PartSurface partSurface_244 = 
      ((PartSurface) meshPart_240.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_267 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2301_SW_Rib_1_COPY; refset PF20 2"));

    MeshPart meshPart_241 = 
      ((MeshPart) compositePart_267.getChildParts().getPart("Body 1294"));

    PartSurface partSurface_245 = 
      ((PartSurface) meshPart_241.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_268 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2301_SW_Rib_1_COPY; refset PF20 3"));

    MeshPart meshPart_242 = 
      ((MeshPart) compositePart_268.getChildParts().getPart("Body 1296"));

    PartSurface partSurface_246 = 
      ((PartSurface) meshPart_242.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_269 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2301_SW_Rib_1_COPY; refset PF20 4"));

    MeshPart meshPart_243 = 
      ((MeshPart) compositePart_269.getChildParts().getPart("Body 1298"));

    PartSurface partSurface_247 = 
      ((PartSurface) meshPart_243.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_270 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2302_SW_Rib_2_COPY; refset PF20"));

    MeshPart meshPart_244 = 
      ((MeshPart) compositePart_270.getChildParts().getPart("Body 1291"));

    PartSurface partSurface_248 = 
      ((PartSurface) meshPart_244.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_271 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2302_SW_Rib_2_COPY; refset PF20 2"));

    MeshPart meshPart_245 = 
      ((MeshPart) compositePart_271.getChildParts().getPart("Body 1292"));

    PartSurface partSurface_249 = 
      ((PartSurface) meshPart_245.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_272 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2302_SW_Rib_2_COPY; refset PF20 3"));

    MeshPart meshPart_246 = 
      ((MeshPart) compositePart_272.getChildParts().getPart("Body 1295"));

    PartSurface partSurface_250 = 
      ((PartSurface) meshPart_246.getPartSurfaceManager().getPartSurface("Default"));

    CompositePart compositePart_273 = 
      ((CompositePart) compositePart_264.getChildParts().getPart("PF20_AER_2302_SW_Rib_2_COPY; refset PF20 4"));

    MeshPart meshPart_247 = 
      ((MeshPart) compositePart_273.getChildParts().getPart("Body 1297"));

    PartSurface partSurface_251 = 
      ((PartSurface) meshPart_247.getPartSurfaceManager().getPartSurface("Default"));

    surfaceCustomMeshControl_0.getGeometryObjects().setObjects(compositePart_2, compositePart_3, meshPart_4, partSurface_0, compositePart_4, meshPart_5, partSurface_1, compositePart_5, compositePart_6, meshPart_6, partSurface_2, meshPart_7, partSurface_3, compositePart_7, compositePart_8, meshPart_8, partSurface_4, meshPart_9, partSurface_5, compositePart_9, meshPart_10, partSurface_6, compositePart_10, meshPart_11, partSurface_7, compositePart_11, meshPart_12, partSurface_8, compositePart_12, meshPart_13, partSurface_9, compositePart_13, meshPart_14, partSurface_10, compositePart_14, meshPart_15, partSurface_11, compositePart_15, meshPart_16, partSurface_12, compositePart_16, meshPart_17, partSurface_13, compositePart_17, meshPart_18, partSurface_14, compositePart_18, meshPart_19, partSurface_15, compositePart_19, meshPart_20, partSurface_16, compositePart_20, meshPart_21, partSurface_17, compositePart_21, meshPart_22, partSurface_18, compositePart_22, meshPart_23, partSurface_19, compositePart_23, meshPart_24, partSurface_20, compositePart_24, meshPart_25, partSurface_21, compositePart_25, compositePart_26, meshPart_26, partSurface_22, meshPart_27, partSurface_23, compositePart_27, compositePart_28, meshPart_28, partSurface_24, meshPart_29, partSurface_25, compositePart_29, compositePart_30, compositePart_31, meshPart_30, partSurface_26, meshPart_31, partSurface_27, compositePart_32, compositePart_33, meshPart_32, partSurface_28, meshPart_33, partSurface_29, compositePart_34, meshPart_34, partSurface_30, compositePart_35, meshPart_35, partSurface_31, compositePart_36, meshPart_36, partSurface_32, compositePart_37, compositePart_38, compositePart_39, meshPart_37, partSurface_33, meshPart_38, partSurface_34, compositePart_40, compositePart_41, meshPart_39, partSurface_35, meshPart_40, partSurface_36, compositePart_42, meshPart_41, partSurface_37, compositePart_43, meshPart_42, partSurface_38, compositePart_44, meshPart_43, partSurface_39, compositePart_45, compositePart_46, meshPart_44, partSurface_40, compositePart_47, meshPart_45, partSurface_41, compositePart_48, meshPart_46, partSurface_42, compositePart_49, meshPart_47, partSurface_43, compositePart_50, compositePart_51, compositePart_52, meshPart_48, partSurface_44, meshPart_49, partSurface_45, compositePart_53, compositePart_54, meshPart_50, partSurface_46, meshPart_51, partSurface_47, compositePart_55, compositePart_56, meshPart_52, partSurface_48, meshPart_53, partSurface_49, compositePart_57, compositePart_58, meshPart_54, partSurface_50, meshPart_55, partSurface_51, compositePart_59, compositePart_60, meshPart_56, partSurface_52, meshPart_57, partSurface_53, compositePart_61, compositePart_62, meshPart_58, partSurface_54, meshPart_59, partSurface_55, compositePart_63, compositePart_64, meshPart_60, partSurface_56, meshPart_61, partSurface_57, compositePart_65, compositePart_66, meshPart_62, partSurface_58, meshPart_63, partSurface_59, compositePart_67, compositePart_68, compositePart_69, meshPart_64, partSurface_60, meshPart_65, partSurface_61, compositePart_70, compositePart_71, meshPart_66, partSurface_62, meshPart_67, partSurface_63, compositePart_72, compositePart_73, meshPart_68, partSurface_64, meshPart_69, partSurface_65, compositePart_74, compositePart_75, meshPart_70, partSurface_66, meshPart_71, partSurface_67, compositePart_76, compositePart_77, meshPart_72, partSurface_68, compositePart_78, meshPart_73, partSurface_69, compositePart_79, meshPart_74, partSurface_70, compositePart_80, compositePart_81, meshPart_75, partSurface_71, compositePart_82, meshPart_76, partSurface_72, compositePart_83, meshPart_77, partSurface_73, compositePart_84, compositePart_85, meshPart_78, partSurface_74, compositePart_86, meshPart_79, partSurface_75, compositePart_87, meshPart_80, partSurface_76, compositePart_88, compositePart_89, meshPart_81, partSurface_77, compositePart_90, meshPart_82, partSurface_78, compositePart_91, meshPart_83, partSurface_79, compositePart_92, compositePart_93, meshPart_84, partSurface_80, compositePart_94, meshPart_85, partSurface_81, compositePart_95, meshPart_86, partSurface_82, compositePart_96, compositePart_97, meshPart_87, partSurface_83, compositePart_98, meshPart_88, partSurface_84, compositePart_99, meshPart_89, partSurface_85, compositePart_100, compositePart_101, meshPart_90, partSurface_86, compositePart_102, meshPart_91, partSurface_87, compositePart_103, meshPart_92, partSurface_88, compositePart_104, compositePart_105, meshPart_93, partSurface_89, compositePart_106, meshPart_94, partSurface_90, compositePart_107, meshPart_95, partSurface_91, compositePart_108, compositePart_109, meshPart_96, partSurface_92, compositePart_110, meshPart_97, partSurface_93, compositePart_111, meshPart_98, partSurface_94, compositePart_112, compositePart_113, meshPart_99, partSurface_95, compositePart_114, meshPart_100, partSurface_96, compositePart_115, meshPart_101, partSurface_97, compositePart_116, compositePart_117, meshPart_102, partSurface_98, compositePart_118, meshPart_103, partSurface_99, compositePart_119, meshPart_104, partSurface_100, compositePart_120, compositePart_121, meshPart_105, partSurface_101, compositePart_122, meshPart_106, partSurface_102, compositePart_123, meshPart_107, partSurface_103, compositePart_124, compositePart_125, meshPart_108, partSurface_104, compositePart_126, meshPart_109, partSurface_105, compositePart_127, meshPart_110, partSurface_106, compositePart_128, compositePart_129, meshPart_111, partSurface_107, compositePart_130, meshPart_112, partSurface_108, compositePart_131, meshPart_113, partSurface_109, compositePart_132, compositePart_133, meshPart_114, partSurface_110, compositePart_134, meshPart_115, partSurface_111, compositePart_135, meshPart_116, partSurface_112, compositePart_136, compositePart_137, meshPart_117, partSurface_113, compositePart_138, meshPart_118, partSurface_114, compositePart_139, meshPart_119, partSurface_115, compositePart_140, meshPart_120, partSurface_116, compositePart_141, meshPart_121, partSurface_117, compositePart_142, compositePart_143, meshPart_122, partSurface_118, meshPart_123, partSurface_119, compositePart_144, compositePart_145, meshPart_124, partSurface_120, meshPart_125, partSurface_121, compositePart_146, compositePart_147, meshPart_126, partSurface_122, meshPart_127, partSurface_123, compositePart_148, compositePart_149, meshPart_128, partSurface_124, meshPart_129, partSurface_125, compositePart_150, compositePart_151, meshPart_130, partSurface_126, meshPart_131, partSurface_127, compositePart_152, compositePart_153, meshPart_132, partSurface_128, meshPart_133, partSurface_129, compositePart_154, compositePart_155, meshPart_134, partSurface_130, compositePart_156, meshPart_135, partSurface_131, compositePart_157, meshPart_136, partSurface_132, compositePart_158, meshPart_137, partSurface_133, compositePart_159, compositePart_160, meshPart_138, partSurface_134, meshPart_139, partSurface_135, meshPart_140, partSurface_136, meshPart_141, partSurface_137, compositePart_161, compositePart_162, meshPart_142, partSurface_138, compositePart_163, meshPart_143, partSurface_139, compositePart_164, meshPart_144, partSurface_140, compositePart_165, meshPart_145, partSurface_141, compositePart_166, meshPart_146, partSurface_142, compositePart_167, meshPart_147, partSurface_143, compositePart_168, meshPart_148, partSurface_144, compositePart_169, meshPart_149, partSurface_145, compositePart_170, meshPart_150, partSurface_146, compositePart_171, meshPart_151, partSurface_147, compositePart_172, meshPart_152, partSurface_148, compositePart_173, meshPart_153, partSurface_149, compositePart_174, meshPart_154, partSurface_150, compositePart_175, meshPart_155, partSurface_151, compositePart_176, compositePart_177, meshPart_156, partSurface_152, meshPart_157, partSurface_153, compositePart_178, compositePart_179, meshPart_158, partSurface_154, partSurface_155, partSurface_156, compositePart_180, meshPart_159, partSurface_157, partSurface_158, partSurface_159, compositePart_181, meshPart_160, partSurface_160, partSurface_161, partSurface_162, compositePart_182, meshPart_161, partSurface_163, partSurface_164, partSurface_165, compositePart_183, compositePart_184, meshPart_162, partSurface_166, meshPart_163, partSurface_167, meshPart_164, partSurface_168, meshPart_165, partSurface_169, meshPart_166, partSurface_170, meshPart_167, partSurface_171, meshPart_168, partSurface_172, meshPart_169, partSurface_173, meshPart_170, partSurface_174, meshPart_171, partSurface_175, meshPart_172, partSurface_176, meshPart_173, partSurface_177, compositePart_185, compositePart_186, compositePart_187, meshPart_174, partSurface_178, compositePart_188, meshPart_175, partSurface_179, compositePart_189, meshPart_176, partSurface_180, compositePart_190, compositePart_191, meshPart_177, partSurface_181, compositePart_192, meshPart_178, partSurface_182, compositePart_193, meshPart_179, partSurface_183, compositePart_194, compositePart_195, meshPart_180, partSurface_184, compositePart_196, meshPart_181, partSurface_185, compositePart_197, meshPart_182, partSurface_186, compositePart_198, compositePart_199, meshPart_183, partSurface_187, compositePart_200, meshPart_184, partSurface_188, compositePart_201, meshPart_185, partSurface_189, compositePart_202, compositePart_203, meshPart_186, partSurface_190, compositePart_204, meshPart_187, partSurface_191, compositePart_205, meshPart_188, partSurface_192, compositePart_206, compositePart_207, meshPart_189, partSurface_193, compositePart_208, meshPart_190, partSurface_194, compositePart_209, meshPart_191, partSurface_195, compositePart_210, compositePart_211, meshPart_192, partSurface_196, compositePart_212, meshPart_193, partSurface_197, compositePart_213, meshPart_194, partSurface_198, compositePart_214, compositePart_215, meshPart_195, partSurface_199, compositePart_216, meshPart_196, partSurface_200, compositePart_217, meshPart_197, partSurface_201, compositePart_218, compositePart_219, meshPart_198, partSurface_202, compositePart_220, meshPart_199, partSurface_203, compositePart_221, meshPart_200, partSurface_204, compositePart_222, compositePart_223, meshPart_201, partSurface_205, compositePart_224, meshPart_202, partSurface_206, compositePart_225, meshPart_203, partSurface_207, compositePart_226, compositePart_227, meshPart_204, partSurface_208, compositePart_228, meshPart_205, partSurface_209, compositePart_229, meshPart_206, partSurface_210, compositePart_230, compositePart_231, meshPart_207, partSurface_211, compositePart_232, meshPart_208, partSurface_212, compositePart_233, meshPart_209, partSurface_213, compositePart_234, compositePart_235, meshPart_210, partSurface_214, compositePart_236, meshPart_211, partSurface_215, compositePart_237, meshPart_212, partSurface_216, compositePart_238, compositePart_239, meshPart_213, partSurface_217, compositePart_240, meshPart_214, partSurface_218, compositePart_241, meshPart_215, partSurface_219, compositePart_242, compositePart_243, meshPart_216, partSurface_220, compositePart_244, meshPart_217, partSurface_221, compositePart_245, meshPart_218, partSurface_222, compositePart_246, compositePart_247, meshPart_219, partSurface_223, compositePart_248, meshPart_220, partSurface_224, compositePart_249, meshPart_221, partSurface_225, compositePart_250, meshPart_222, partSurface_226, compositePart_251, meshPart_223, partSurface_227, compositePart_252, meshPart_224, partSurface_228, compositePart_253, meshPart_225, partSurface_229, compositePart_254, meshPart_226, partSurface_230, compositePart_255, meshPart_227, partSurface_231, compositePart_256, compositePart_257, meshPart_228, partSurface_232, meshPart_229, partSurface_233, compositePart_258, meshPart_230, partSurface_234, compositePart_259, meshPart_231, partSurface_235, compositePart_260, meshPart_232, partSurface_236, compositePart_261, meshPart_233, partSurface_237, compositePart_262, meshPart_234, partSurface_238, compositePart_263, meshPart_235, partSurface_239, compositePart_264, compositePart_265, meshPart_236, partSurface_240, meshPart_237, partSurface_241, meshPart_238, partSurface_242, meshPart_239, partSurface_243, compositePart_266, meshPart_240, partSurface_244, compositePart_267, meshPart_241, partSurface_245, compositePart_268, meshPart_242, partSurface_246, compositePart_269, meshPart_243, partSurface_247, compositePart_270, meshPart_244, partSurface_248, compositePart_271, meshPart_245, partSurface_249, compositePart_272, meshPart_246, partSurface_250, compositePart_273, meshPart_247, partSurface_251);
  }
}
