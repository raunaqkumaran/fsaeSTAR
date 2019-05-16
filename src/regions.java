import star.common.*;
import simComponents.*;
import star.flow.*;

public class regions extends StarMacro {
    public void execute() {
        execute0();
    }

    private void execute0()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        getActiveSimulation().println(activeSim.freestreamVal);

        double [] freestreamVector = simComponents.vectorScale(activeSim.freestreamVal, activeSim.foreAftDirection);
        double frontRotationRate = activeSim.freestreamVal / activeSim.frontTyreRadius;
        double rearRotationRate = activeSim.freestreamVal / activeSim.rearTyreRadius;

        if (activeSim.wtFlag)
        {
            freestreamVector = new double[] {0,0,0};
            frontRotationRate = 0;
            rearRotationRate = 0;
        }


        activeSim.regionSwap();     // This is one of the few 'processes'
                                    // done within simComponents. Recreates the two main regions.

        // Necessary to recreate the simComponents object to make the new boundary collections
        activeSim = new simComponents(getActiveSimulation());

        // Assign boundary conditions to freestream/domain block.
        // Please don't change these domain names. I tried to set things up so it's somewhat easy to change set-up
        // and update the macros accordingly, but there's zero reason to fuck with these.
        // Some of the function calls are ugly. But that's STAR'S API being what it is...

        for (Boundary fsBound : activeSim.freestreamBounds)
        {
            String boundName = fsBound.getPresentationName();
            if (boundName.contains("Left") || boundName.contains("Symmetry") || boundName.contains("Top"))
                fsBound.setBoundaryType(SymmetryBoundary.class);
            else if (boundName.contains("Inlet"))
            {
                fsBound.setBoundaryType(InletBoundary.class);
                fsBound.getValues().get(VelocityMagnitudeProfile.class).
                        getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.freestreamVal);
            }
            else if (boundName.contains("Ground"))
            {
                fsBound.getConditions().get(WallSlidingOption.class).
                        setSelected(WallSlidingOption.Type.VECTOR);
                fsBound.getValues().get(WallRelativeVelocityProfile.class).
                        getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(freestreamVector);
            }
            else if (boundName.contains("Outlet"))
            {
                fsBound.setBoundaryType(PressureBoundary.class);
            }
        }

        // Set up wheel boundary conditions

        for (Boundary wheelBound : activeSim.wheelBounds)
        {
            String boundName = wheelBound.getPresentationName();
            wheelBound.getConditions().get(WallSlidingOption.class).
                    setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

            try {

                if (boundName.contains("Front")) {
                    wheelBound.getValues().
                            get(ReferenceFrame.class).setCoordinateSystem(activeSim.frontWheelCoord);
                    wheelBound.getValues().get(WallRelativeRotationProfile.class).
                            getMethod(ConstantScalarProfileMethod.class).getQuantity().
                            setValue(frontRotationRate);
                } else if (boundName.contains("Rear")) {
                    wheelBound.getValues().get(ReferenceFrame.class).
                            setCoordinateSystem(activeSim.rearWheelCoord);
                    wheelBound.getValues().get(WallRelativeRotationProfile.class).
                            getMethod(ConstantScalarProfileMethod.class).getQuantity().
                            setValue(rearRotationRate);
                }
            }
            catch (Exception e)
            {
                activeSim.activeSim.println("regions.java - Wheel boundary set-up failed");
            }
        }

        // Set up initial conditions
        activeSim.kePhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(freestreamVector);

        try {
            activeSim.kePhysics.erase(activeSim.domainRegion);
        }
        catch (Exception e)
        {

        }
        activeSim.kePhysics.add(activeSim.domainRegion);

        // These function calls are fucking nasty. But that's Java for you.
        // Some of these calls will need to be modified if coordinate systems change in the future.

        activeSim.massFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadInlet, activeSim.radInlet,
                        activeSim.massFlowInterfaceNameInlet);

        activeSim.massFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadOutlet, activeSim.radOutlet,
                        activeSim.massFlowInterfaceNameOutlet);
        
        if (activeSim.dualRadFlag){
            activeSim.dualMassFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadInlet, activeSim.dualRadInlet, 
                            activeSim.dualMassFlowInterfaceNameInlet);
            activeSim.dualMassFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadOutlet, activeSim.dualRadOutlet, 
                            activeSim.dualMassFlowInterfaceNameOutlet);
            
        }

        activeSim.radiatorRegion.setRegionType(PorousRegion.class);
        activeSim.radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class).getYAxis().setCoordinateSystem(activeSim.radiatorCoord);
        activeSim.radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class).getXAxis().setCoordinateSystem(activeSim.radiatorCoord);
        activeSim.radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class).getProfile(0).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
        activeSim.radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class).getProfile(1).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResSmall);
        activeSim.radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class).getProfile(2).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
        
        if (activeSim.dualRadFlag){
            activeSim.dualRadiatorRegion.setRegionType(PorousRegion.class);
            activeSim.dualRadiatorRegion.getValues().get(PorousInertialResistance.class).
                    getMethod(PrincipalTensorProfileMethod.class).getYAxis().setCoordinateSystem(activeSim.dualRadCoord);
            activeSim.dualRadiatorRegion.getValues().get(PorousInertialResistance.class).
                    getMethod(PrincipalTensorProfileMethod.class).getXAxis().setCoordinateSystem(activeSim.dualRadCoord);
            activeSim.dualRadiatorRegion.getValues().get(PorousInertialResistance.class).
                    getMethod(PrincipalTensorProfileMethod.class).getProfile(0).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
            activeSim.dualRadiatorRegion.getValues().get(PorousInertialResistance.class).
                    getMethod(PrincipalTensorProfileMethod.class).getProfile(1).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResSmall);
            activeSim.dualRadiatorRegion.getValues().get(PorousInertialResistance.class).
                    getMethod(PrincipalTensorProfileMethod.class).getProfile(2).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
        }
    }
}
