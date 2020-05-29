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
        double[] initialVector = simComponents.vectorRotate(activeSim.valEnv("Yaw"), simComponents.vectorScale(activeSim.freestreamVal * 0.1, activeSim.foreAftDirection));

        activeSim.regionSwap();     // This is one of the few 'processes'
        // done within simComponents. Recreates the two main regions.

        // Necessary to recreate the simComponents object to make the new boundary collections
        activeSim = new simComponents(getActiveSimulation());


        // Assign boundary conditions to freestream/domain block.
        // Please don't change these domain names. I tried to set things up so it's somewhat easy to change set-up
        // and update the macros

        setDomainBoundaries(activeSim);

        // Set up wheel boundary conditions

        setTyreRotation(activeSim);

        // Set up initial conditions
        activeSim.saPhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);
        activeSim.desPhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);


        // This can be made much cleaner by using vars for some of these object returns.
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

    private void setTyreRotation(simComponents activeSim) {

        for (Boundary wheelBound : activeSim.wheelBounds) {
            double frontRotationRate = activeSim.freestreamVal / activeSim.frontTyreRadius;
            double rearRotationRate = activeSim.freestreamVal / activeSim.rearTyreRadius;

            if (activeSim.wtFlag) {
                frontRotationRate = 0;
                rearRotationRate = 0;
            }
            String boundName = wheelBound.getPresentationName();
            wheelBound.getConditions().get(WallSlidingOption.class).
                    setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

            try {

                if (boundName.contains("Front")) {
                    activeSim.activeSim.println("Setting front tyre rotation rate to : " + frontRotationRate);
                    wheelBound.getValues().
                            get(ReferenceFrame.class).setCoordinateSystem(activeSim.frontWheelCoord);
                    wheelBound.getValues().get(WallRelativeRotationProfile.class).
                            getMethod(ConstantScalarProfileMethod.class).getQuantity().
                            setValue(frontRotationRate);
                } else if (boundName.contains("Rear")) {
                    activeSim.activeSim.println("Setting rear tyre rotation rate to : " + rearRotationRate);
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
    }

    private void setDomainBoundaries(simComponents activeSim) {
            String yVal = String.valueOf(activeSim.calculateSideslip());
            activeSim.leftPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.symPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.topPlane.setBoundaryType(SymmetryBoundary.class);
            activeSim.fsInlet.setBoundaryType(InletBoundary.class);
            activeSim.fsInlet.getValues().get(VelocityMagnitudeProfile.class).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${" + activeSim.FREESTREAM_PARAMETER_NAME + "}");
            activeSim.groundPlane.getConditions().get(WallSlidingOption.class).
                    setSelected(WallSlidingOption.Type.VECTOR);
            if (activeSim.wtFlag)
                activeSim.groundPlane.getValues().get(WallRelativeVelocityProfile.class).
                        getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(new double[]{0, 0, 0});
            else
                activeSim.groundPlane.getValues().get(WallRelativeVelocityProfile.class).
                        getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${Freestream}," + yVal + ", 0]");
            activeSim.fsOutlet.setBoundaryType(PressureBoundary.class);
    }

    public void yawInterfaces(simComponents activeSim) {
        setTyreRotation(activeSim);

        if (activeSim.fullCarFlag) {
            activeSim.activeSim.println("Setting boundary conditions for yaw");
            if (activeSim.activeSim.getInterfaceManager().has(simComponents.YAW_INTERFACE_NAME) && activeSim.activeSim.getInterfaceManager().getInterface(simComponents.YAW_INTERFACE_NAME) instanceof BoundaryInterface)
            {
                activeSim.activeSim.println("Found yaw interface");
                activeSim.yawInterface = (BoundaryInterface) activeSim.activeSim.getInterfaceManager().getInterface(simComponents.YAW_INTERFACE_NAME);
                activeSim.activeSim.getInterfaceManager().deleteInterface(activeSim.yawInterface);
                setDomainBoundaries(activeSim);
            }

            activeSim.activeSim.println("Creating yaw interface");
            activeSim.yawInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.leftPlane, activeSim.symPlane, simComponents.YAW_INTERFACE_NAME);

            activeSim.yawInterface.setPresentationName(simComponents.YAW_INTERFACE_NAME);
            activeSim.yawInterface.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);
            double yawVal = activeSim.valEnv("yaw");
            activeSim.fsInlet.getValues().get(VelocityMagnitudeProfile.class).
                    getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(activeSim.freestreamVal / Math.cos(Math.toRadians(yawVal)));
            activeSim.fsInlet.getConditions().get(FlowDirectionOption.class).setSelected(FlowDirectionOption.Type.ANGLES);
            FlowAnglesProfile yawAngleControl = activeSim.fsInlet.getValues().get(FlowAnglesProfile.class);
            yawAngleControl.getMethod(ConstantAnglesProfileMethod.class).getQuantity().setUnits(activeSim.degs);
            yawAngleControl.getMethod(ConstantAnglesProfileMethod.class).getQuantity().setRotationAngles(new DoubleVector(new double[]{yawVal, 0, 0}));
            activeSim.activeSim.println("Yaw set to " + yawVal + " attempted");
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

    public static void setTurbulence(simComponents activeSim) {

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
