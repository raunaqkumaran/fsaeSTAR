import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.common.*;
import star.flow.*;
import star.motion.BoundaryReferenceFrameSpecification;
import star.motion.ReferenceFrameOption;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/*
Sets up regions and boundary conditions. Also holds a few functions used by other macros for boundary conditions.
 */

public class regions extends StarMacro {

    public void execute() {
        execute0();
    }

    private void execute0() {
        simComponents activeSim = new simComponents(getActiveSimulation());

        activeSim.activeSim.println("--- Setting up regions ---");

        //This defines the initial condition. Apparently setting to zero is bad? But I also don't like setting to the full freestream value, since that messes with the monitors in early iterations. Overall convergence time doesn't really change much.
        double[] initialVector = simComponents.vectorRotate(activeSim.valEnv("Yaw"), simComponents.vectorScale(activeSim.freestreamVal * 0.1, activeSim.foreAftDirection));

        //Need to swap out the old region for the new regions. I like swapping rather than modifying, since it's more repeatable and consistent for the macros to deal with. This is destructive.,
        activeSim.regionSwap();

        // Recreate the simComponents object after region swap.
        activeSim = new simComponents(getActiveSimulation());


        // Assign boundary conditions to freestream/domain block. There is a naming convection to this.
        setDomainBoundaries(activeSim);

        // Set up wheel boundary conditions
        setTyreRotation(activeSim);

        // Set up initial conditions
        activeSim.steadyStatePhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);
        activeSim.desPhysics.getInitialConditions().get(VelocityProfile.class).
                getMethod(ConstantVectorProfileMethod.class).getQuantity().setConstant(initialVector);


        // This can be made much cleaner by using vars for some of these object returns.
        // Some of these calls will need to be modified if coordinate systems change in the future.

        //Set up boundaries for the radiator inlets and outlest.
        activeSim.massFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadInlet, activeSim.radInlet,
                        activeSim.massFlowInterfaceNameInlet);

        activeSim.massFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                createBoundaryInterface(activeSim.domainRadOutlet, activeSim.radOutlet,
                        activeSim.massFlowInterfaceNameOutlet);

        activeSim.fanInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.radFanBound, activeSim.domainFanBound, "Fan Interface");
        setUpFan(activeSim, activeSim.fanInterface);
        if (activeSim.dualRadFlag && activeSim.domainDualRadInlet != null && activeSim.domainDualRadOutlet != null) {
            activeSim.dualMassFlowInterfaceInlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadInlet, activeSim.dualRadInlet,
                            activeSim.dualMassFlowInterfaceNameInlet);
            activeSim.dualMassFlowInterfaceOutlet = activeSim.activeSim.getInterfaceManager().
                    createBoundaryInterface(activeSim.domainDualRadOutlet, activeSim.dualRadOutlet,
                            activeSim.dualMassFlowInterfaceNameOutlet);
            activeSim.dualFanInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.dualRadFanBound, activeSim.dualDomainFanBound, "Dual Fan Interface");
            setUpFan(activeSim, activeSim.dualFanInterface);
        }

        //Assign viscous properties to the radiator regions.
        setRadiatorParams(activeSim, activeSim.radiatorRegion);

        if (activeSim.dualRadFlag) {
            setRadiatorParams(activeSim, activeSim.dualRadiatorRegion);
        }
    }

    public void setUpFan(simComponents activeSim, BoundaryInterface fanInterface)
    {
        fanInterface.setInterfaceType(FanInterface.class);
        fanInterface.getConditions().get(InterfaceFanCurveSpecification.class).getFanCurveTypeOption().setSelected(FanCurveTypeOption.Type.TABLE);
        FanCurveTableLeaf node = fanInterface.getValues().get(FanCurveTable.class).getModelPartValue();
        node.setVolumeFlowTable(activeSim.fan_curve_table);
        activeSim.fan_curve_table.setFileName(simComponents.FAN_CURVE_CSV_FN);
        if (!activeSim.fan_curve_table.getFile().exists())
        {
            activeSim.activeSim.println("Cannot find fan_curve.csv in working directory, attempting to find file in classpath");
            String classPath = simComponents.valEnvString("CP");
            String filePath = classPath + File.separator + simComponents.FAN_CURVE_CSV_FN;
            File f = new File(filePath);
            if (!f.exists())
            {
                throw new IllegalStateException("No fan table found. Terminating");
            }
            activeSim.fan_curve_table.setFileName(filePath);
        }
        activeSim.fan_curve_table.extract();
        node.setVolumeFlowTableX("m^3/s");
        node.setVolumeFlowUnitsX(activeSim.activeSim.getUnitsManager().getUnits("m^3/s"));
        if (activeSim.fanFlag)
            node.setVolumeFlowTableP("dP");
        else
            node.setVolumeFlowTableP("no_fan");
        node.setVolumeFlowUnitsP(activeSim.activeSim.getUnitsManager().getUnits("Pa"));
    }

    private void setTyreRotation(simComponents activeSim) {

        //Calculate angular rotation rate for wheels based on freestream (vehicle speed) and provided tyre radii. (omega = v/r, need to have m/s and meters)
        double frontRotationRate = activeSim.freestreamVal / activeSim.frontTyreRadius;
        double rearRotationRate = activeSim.freestreamVal / activeSim.rearTyreRadius;
        double diffVelocity;
        //Set rotation rates to zero if the wt flag is enabled.
        if (activeSim.wtFlag) {
            frontRotationRate = 0;
            rearRotationRate = 0;
        }
        if (activeSim.corneringFlag)
        {
            diffVelocity = velocityDifference(activeSim);
        }
        else
        {
            diffVelocity = 0;
        }

        for (Boundary wheelBound : activeSim.wheelBounds) {

            //Set the correct boundary type to the wheels.
            String boundName = wheelBound.getPresentationName();
            wheelBound.getConditions().get(WallSlidingOption.class).
                    setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

            try {
                //This is the chunk that actually assigns the rotation rate to the wheels.
                if (boundName.contains("Front")) {
                    double rotationRate = frontRotationRate;
                    if (boundName.contains("Front Right"))
                    {
                        rotationRate = frontRotationRate - diffVelocity / activeSim.frontTyreRadius;
                    }
                    else if (boundName.contains("Front Left"))
                    {
                        rotationRate = frontRotationRate + diffVelocity / activeSim.frontTyreRadius;
                    }
                    activeSim.activeSim.println("Setting front tyre rotation rate to : " + rotationRate);
                    wheelBound.getValues().
                            get(ReferenceFrame.class).setCoordinateSystem(activeSim.frontWheelCoord);
                    wheelBound.getValues().get(WallRelativeRotationProfile.class).
                            getMethod(ConstantScalarProfileMethod.class).getQuantity().
                            setValue(rotationRate);
                } else if (boundName.contains("Rear")) {
                    double rotationRate = rearRotationRate;
                    if (boundName.contains("Rear Right"))
                    {
                        rotationRate = rearRotationRate - diffVelocity / activeSim.rearTyreRadius;
                    }
                    else if (boundName.contains("Rear Left"))
                    {
                        rotationRate = rearRotationRate + diffVelocity / activeSim.rearTyreRadius;
                    }
                    activeSim.activeSim.println("Setting rear tyre rotation rate to : " + rotationRate);
                    wheelBound.getValues().get(ReferenceFrame.class).
                            setCoordinateSystem(activeSim.rearWheelCoord);
                    wheelBound.getValues().get(WallRelativeRotationProfile.class).
                            getMethod(ConstantScalarProfileMethod.class).getQuantity().
                            setValue(rotationRate);
                }
            } catch (Exception e) {
                activeSim.activeSim.println("regions.java - Wheel boundary set-up failed");
            }
        }
    }

    private double velocityDifference(simComponents activeSim)
    {
        double omega = activeSim.angularVelocity.getQuantity().evaluate();
        return omega * activeSim.trackWidth * 0.0254;
    }

    //Sets up boundary conditions for the domain boundaries. Ground, inlet, outlet, symmetry, symmetry, symmetry.
    private void setDomainBoundaries(simComponents activeSim) {
        if (activeSim.corneringFlag)
        {
            setDomainBoundaries_Cornering(activeSim);
            return;
        }
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

    private void setDomainBoundaries_Cornering(simComponents activeSim){
        activeSim.leftPlane.setBoundaryType(SymmetryBoundary.class);
        activeSim.symPlane.setBoundaryType(SymmetryBoundary.class);
        activeSim.topPlane.setBoundaryType(SymmetryBoundary.class);
        activeSim.fsOutlet.setBoundaryType(OutletBoundary.class);
        activeSim.groundPlane.getConditions().get(ReferenceFrameOption.class).setSelected(ReferenceFrameOption.Type.LOCAL_FRAME);
        activeSim.groundPlane.getValues().get(BoundaryReferenceFrameSpecification.class).setReferenceFrame(activeSim.rotatingFrame);
        activeSim.fsInlet.setBoundaryType(InletBoundary.class);
        activeSim.fsInlet.getConditions().get(ReferenceFrameOption.class).setSelected(ReferenceFrameOption.Type.LOCAL_FRAME);
        activeSim.fsInlet.getValues().get(BoundaryReferenceFrameSpecification.class).setReferenceFrame(activeSim.rotatingFrame);
    }

    //Set up interfaces for a full car domain. Need to interface the left and right boundaries together, otherwise the domain will naturally straighten any yaw condition you set at the inlet.
    //Honestly, I don't fully understand what's going on with this function, or the exact sequence of events that led me to write it the way it's written. It would've been a good idea for me to comment this function when I wrote it, but that ship has sailed.
    //I do know it was very hard to get this to work reliably. there are a lot of edge cases that unravel here, especially when resuing a sim, or changing a sim from full car to half car or vice versa.
    public void yawInterfaces(simComponents activeSim) {
        setTyreRotation(activeSim);

        if (activeSim.fullCarFlag) {
            activeSim.activeSim.println("Setting boundary conditions for yaw");

            // I don't know why this chunk is important, but it is.
            if (activeSim.activeSim.getInterfaceManager().has(simComponents.YAW_INTERFACE_NAME) && activeSim.activeSim.getInterfaceManager().getInterface(simComponents.YAW_INTERFACE_NAME) instanceof BoundaryInterface)
            {
                activeSim.activeSim.println("Found yaw interface");
                activeSim.yawInterface = (BoundaryInterface) activeSim.activeSim.getInterfaceManager().getInterface(simComponents.YAW_INTERFACE_NAME);
                activeSim.activeSim.getInterfaceManager().deleteInterface(activeSim.yawInterface);
                setDomainBoundaries(activeSim);  //I don't know why I'm calling this. I think it's for repeatability, to make sure there's a consistent set-up for the domain boundaries before createBoundaryInterface is called.
            }

            activeSim.activeSim.println("Creating yaw interface");
            activeSim.yawInterface = activeSim.activeSim.getInterfaceManager().createBoundaryInterface(activeSim.leftPlane, activeSim.symPlane, simComponents.YAW_INTERFACE_NAME);

            activeSim.yawInterface.setPresentationName(simComponents.YAW_INTERFACE_NAME);
            activeSim.yawInterface.getTopology().setSelected(InterfaceConfigurationOption.Type.PERIODIC);               //I don't have the foggiest idea what interface topology is supposed to be for. this is some blatant plagiarism.

            //Set up the yaw condition at the inlet.
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

    //Set up viscous and intertial resistances for the radiators. There's a good article on Siemens' Steve portal (or whatever they're calling it now, there's a very solid chance the Steve portal no longer exists if you're reading this in the future) explaining how you can get radiator properties out of wind tunnel data for a given radiator.
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

    //Make sure all regions are set to the correct physics model.
    public static void setTurbulence(simComponents activeSim) {

        activeSim.domainRegion.setPhysicsContinuum(activeSim.steadyStatePhysics);
        activeSim.radiatorRegion.setPhysicsContinuum(activeSim.steadyStatePhysics);
        if (activeSim.dualRadFlag)
            activeSim.dualRadiatorRegion.setPhysicsContinuum(activeSim.steadyStatePhysics);


        if (activeSim.DESFlag) {
            activeSim.domainRegion.setPhysicsContinuum(activeSim.desPhysics);
            activeSim.radiatorRegion.setPhysicsContinuum(activeSim.desPhysics);
            if (activeSim.dualRadFlag)
                activeSim.dualRadiatorRegion.setPhysicsContinuum(activeSim.desPhysics);
        }
    }

    //this is really important for postProc. 2D postProc is very slow if you don't reduce the total number of boundaries. This merges boundaries. This could be done just before meshing, but I don't like doing that since you lose flexibility with reports. This is safe to do just before 2D postProc, so long as you understand that this function will destory the mesh.
    public void mergeBoundaries (simComponents activeSim)
    {
        MeshManager meshManager = activeSim.activeSim.getMeshManager();
        Collection<Boundary> mergeBounds = new ArrayList<>();
        for (Boundary x : activeSim.partBounds)
        {
            mergeBounds.add(x);

            for (Collection<Boundary> coll : activeSim.partSpecBounds.values())
            {
                if (coll.contains(x))
                    mergeBounds.remove(x);
            }
            if (x.getPresentationName().toLowerCase().contains("interface") || x.getPresentationName().toLowerCase().contains("radiator"))
                mergeBounds.remove(x);
        }
        activeSim.activeSim.println("merging: " + mergeBounds);
        meshManager.combineBoundaries(new NeoObjectVector(mergeBounds.toArray()));
    }
    public void initFans(simComponents activeSim)
    {
        for (Interface x : activeSim.activeSim.getInterfaceManager().getObjects())
        {
            if (x.getInterfaceType() instanceof FanInterface)
            {
                setUpFan(activeSim, (BoundaryInterface) x);
            }
        }
    }
}
