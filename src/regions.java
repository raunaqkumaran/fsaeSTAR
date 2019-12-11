import simComponents.simComponents;
import star.base.neo.DoubleVector;
import star.common.*;
import star.flow.*;

public class regions extends StarMacro {
    public void execute() {
        execute0();
    }

    private void execute0() {
        simComponents activeSim = new simComponents(getActiveSimulation());

        activeSim.activeSim.println("--- Setting up regions ---");
        double[] initialVector = simComponents.vectorRotate(simComponents.valEnv("Yaw"), simComponents.vectorScale(activeSim.freestreamVal * 0.1, activeSim.foreAftDirection));
        double frontRotationRate = activeSim.freestreamVal / activeSim.frontTyreRadius;
        double rearRotationRate = activeSim.freestreamVal / activeSim.rearTyreRadius;

        if (activeSim.wtFlag) {
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

        setDomainBoundaries(activeSim);

        // Set up wheel boundary conditions

        for (Boundary wheelBound : activeSim.wheelBounds) {
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
            } catch (Exception e) {
                activeSim.activeSim.println("regions.java - Wheel boundary set-up failed");
            }
        }

        // Set up initial conditions
        activeSim.saPhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);
        activeSim.desPhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);


        // These function calls are fucking nasty. This can be made much cleaner by using vars for some of these object returns.
        // Some of these calls will need to be modified if coordinate systems change in the future.

        activeSim.massFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadInlet, activeSim.radInlet,
                        activeSim.massFlowInterfaceNameInlet);

        activeSim.massFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadOutlet, activeSim.radOutlet,
                        activeSim.massFlowInterfaceNameOutlet);

        if (activeSim.dualRadFlag && activeSim.domainDualRadInlet != null && activeSim.domainDualRadOutlet != null) {
            activeSim.dualMassFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadInlet, activeSim.dualRadInlet,
                            activeSim.dualMassFlowInterfaceNameInlet);
            activeSim.dualMassFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadOutlet, activeSim.dualRadOutlet,
                            activeSim.dualMassFlowInterfaceNameOutlet);

        }

        setRadiatorParams(activeSim, activeSim.radiatorRegion);

        if (activeSim.dualRadFlag) {
            setRadiatorParams(activeSim, activeSim.dualRadiatorRegion);
        }
    }

    private void setDomainBoundaries(simComponents activeSim) {
            activeSim.leftPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.symPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.topPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.fsInlet.setBoundaryType(InletBoundary.class);
            activeSim.fsInlet.getValues().get(VelocityMagnitudeProfile.class).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${" + activeSim.freestreamParameterName + "}");
            activeSim.groundPlane.getConditions().get(WallSlidingOption.class).
                    setSelected(WallSlidingOption.Type.VECTOR);
            if (activeSim.wtFlag)
                activeSim.groundPlane.getValues().get(WallRelativeVelocityProfile.class).
                        getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(new double[]{0, 0, 0});
            else
                activeSim.groundPlane.getValues().get(WallRelativeVelocityProfile.class).
                        getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${Freestream}, 0, 0]");
            activeSim.fsOutlet.setBoundaryType(PressureBoundary.class);
    }

    public void yawInterfaces(simComponents activeSim) {
        setDomainBoundaries(activeSim);

        if (activeSim.fullCarFlag) {
            activeSim.activeSim.println("Setting boundary conditions for yaw");
            if (activeSim.activeSim.getInterfaceManager().has("Yaw interface"))
            {
                if (activeSim.activeSim.getSimulationIterator().getCurrentIteration() > 0 && activeSim.activeSim.getInterfaceManager().getInterface("Yaw interface") instanceof BoundaryInterface)
                    activeSim.yawInterface = (BoundaryInterface) activeSim.activeSim.getInterfaceManager().getInterface("Yaw interface");
                else
                {
                    activeSim.activeSim.getInterfaceManager().deleteInterface(activeSim.activeSim.getInterfaceManager().getInterface("Yaw interface"));
                    activeSim.yawInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.leftPlane, activeSim.symPlane, "Yaw interface");
                }
            }
            else
                activeSim.yawInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.leftPlane, activeSim.symPlane, "Yaw interface");
            activeSim.yawInterface.setPresentationName("Yaw interface");
            activeSim.yawInterface.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);
            double yawVal = simComponents.valEnv("yaw");
            activeSim.fsInlet.getConditions().get(FlowDirectionOption.class).setSelected(FlowDirectionOption.Type.ANGLES);
            FlowAnglesProfile yawAngleControl = activeSim.fsInlet.getValues().get(FlowAnglesProfile.class);
            yawAngleControl.getMethod(ConstantAnglesProfileMethod.class).getQuantity().setUnits(activeSim.degs);
            yawAngleControl.getMethod(ConstantAnglesProfileMethod.class).getQuantity().setRotationAngles(new DoubleVector(new double[]{0, 0, yawVal}));
        } else {
            activeSim.activeSim.println("Can't set boundary conditions for yaw with fullCarFlag set to false");
        }
    }

    private void setRadiatorParams(simComponents activeSim, Region radiatorRegion) {
        radiatorRegion.setRegionType(PorousRegion.class);
        PrincipalTensorProfileMethod radiatorTensor = radiatorRegion.getValues().get(PorousInertialResistance.class).
                getMethod(PrincipalTensorProfileMethod.class);
        PrincipalTensorProfileMethod radiatorViscousTensor = radiatorRegion.getValues().get(PorousViscousResistance.class).getMethod(PrincipalTensorProfileMethod.class);

        radiatorTensor.getYAxis().setCoordinateSystem(activeSim.radiatorCoord);
        radiatorTensor.getXAxis().setCoordinateSystem(activeSim.radiatorCoord);
        radiatorTensor.getProfile(0).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
        radiatorTensor.getProfile(1).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Inertial Resistance}");
        radiatorTensor.getProfile(2).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);

        radiatorViscousTensor.getYAxis().setCoordinateSystem(activeSim.radiatorCoord);
        radiatorViscousTensor.getXAxis().setCoordinateSystem(activeSim.radiatorCoord);
        radiatorViscousTensor.getProfile(0).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
        radiatorViscousTensor.getProfile(1).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Viscous Resistance}");
        radiatorViscousTensor.getProfile(2).
                getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.radResBig);
    }

    public static void setPhysics(simComponents activeSim) {

        activeSim.domainRegion.setPhysicsContinuum(activeSim.saPhysics);
        activeSim.radiatorRegion.setPhysicsContinuum(activeSim.saPhysics);
        if (activeSim.dualRadFlag)
            activeSim.dualRadiatorRegion.setPhysicsContinuum(activeSim.saPhysics);


        if (simComponents.boolEnv("DES")) {
            activeSim.domainRegion.setPhysicsContinuum(activeSim.desPhysics);
            activeSim.radiatorRegion.setPhysicsContinuum(activeSim.desPhysics);
            if (activeSim.dualRadFlag)
                activeSim.dualRadiatorRegion.setPhysicsContinuum(activeSim.desPhysics);
        }
    }
}
