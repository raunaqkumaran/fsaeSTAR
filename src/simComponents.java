/*
    Class to instantiate frequently used simulation components. Largely to act as a support library / API for other
    macros to interact with objects in our simulation environment, and handle basic simulation operations too menial
    for a full macro.

    Raunaq Kumaran, January 2019
 */

import star.base.neo.NeoObjectVector;
import star.base.report.MaxReport;
import star.base.report.Report;
import star.common.*;
import star.flow.AccumulatedForceTable;
import star.meshing.*;
import star.screenplay.Screenplay;
import star.screenplay.ScreenplayManager;
import star.surfacewrapper.SurfaceWrapperAutoMeshOperation;
import star.vis.*;
import java.io.File;
import java.util.*;


public class simComponents {

    //Some string constants. This is something I started doing later on, and haven't done for every string.
    // I keep flip-flopping between whether or not this is a good idea or not, which means the final result of string management is pretty poor. Sorry.

    public static final String YAW_INTERFACE_NAME = "Yaw interface";
    public static final String USER_FREESTREAM = "User Freestream";
    public static final String USER_YAW = "User Yaw";
    public static final String USER_FRONT_RIDE_HEIGHT = "User Front Ride Height";
    public static final String USER_REAR_RIDE_HEIGHT = "User Rear Ride Height";
    public static final String SIDESLIP = "User Sideslip";
    public static final String CONFIGSIDESLIP = "sideslip";
    public static final String FREESTREAM_PARAMETER_NAME = "Freestream";
    public static final String DOMAIN_REGION = "Subtract";
    public static final String RADIATOR_REGION = "Radiator";
    public static final String DUAL_RADIATOR_REGION = "Radiator 2";
    public static final String FRONT_LEFT = "Front Left";
    public static final String FRONT_RIGHT = "Front Right";
    public static final String REAR_LEFT = "Rear Left";
    public static final String REAR_RIGHT = "Rear Right";
    public static final String USER_STEERING = "User Steering";
    public static final String STEERING = "steering";

    //A bunch of declarations. Don't read too much into the access modifiers, they're not a big deal for a project like this.
    // I'm not going to comment all of these. there are way too many (future improvement suggestion: use fewer variables)

    //Version check. An easy way to make sure the sim and the macros are the same version. Throw an error at the beginning, rather than an uncaught NPE later.
    // This needs to match the version parameter in STAR. This is really just a way so people don't bug me with macro problems that can be solved with pulling the correct branch/tag
    private double version = 3.2;

    // Simulation object
    public Simulation activeSim;

    public Collection<GeometryPart> aeroParts;
    public Collection<GeometryPart> nonAeroParts;
    public Collection<GeometryPart> wheels;
    private Collection<GeometryPart> liftGenerators;
    public String[] aeroPrefixes = {"RW", "FW", "UT", "EC", "MOUNT", "SW", "FC"};                                       //These prefixes will be used to decide what an aero component is.
    private String[] liftGeneratorPrefixes = {"RW", "FW", "UT", "SW", "FC"};                                            //These prefixes generate lift. Aero surface wrap control needs to know this.
    public Collection<Boundary> domainBounds;
    public Collection<Boundary> radBounds;
    public Collection<Boundary> freestreamBounds;
    public Collection<Boundary> partBounds;
    public Collection<Boundary> wheelBounds;
    public String freestreamPrefix = "Freestream";                                                                      //This is the domain. Good way to make sure the macros filter out domain surfaces later on. Just make sure no actual parts include the term "freestream"
    public Map<String, Collection<Boundary>> partSpecBounds;
    private Collection<GeometryPart> allParts;
    private Collection<GeometryPart> radiator;
    private Collection<GeometryPart> dualRadiator;
    private String[] nonAeroPrefixes = {"CFD", "DONTGIVE", "NS"};                                                       //These are prefixes for non-aero parts. Everything other than aero and tyres must have one of these prefixes.
    private String[] wheelNames = {FRONT_LEFT, FRONT_RIGHT, REAR_LEFT, REAR_RIGHT};                                     //Names for wheels. Must be exact.
    private String radiatorName = "CFD_RADIATOR";
    private String dualRadiatorName = "CFD_DUAL_RADIATOR";

    //Double arrays to hold ranges for scenes and plane section sweeps. Limits are in inches, and control how far the cross sections will go. Pressures are Cps.
    public double[] profileLimits = {-29, 29};
    public double[] aftForeLimits = {-70, 55};
    public double[] utLimits = {0.35, 10};
    public double[] topBottomLimits = {10, 60};

    //These define the vector direction for cross section scenes.
    public double[] foreAftDirection = {1, 0, 0};
    public double[] profileDirection = {0, 1, 0};
    public double[] topBottomDirection = {0, 0, 1};


    //Some objects for reports and plots
    public Collection<Report> reports;
    public String massFlowRepName;
    public String pitchRepName;
    public PointPart point;
    public XyzInternalTable repTable;
    public Map<String, AccumulatedForceTable> forceTables;
    public Collection<StarPlot> plots;

    //Units
    public Units noUnit;
    public Units inches;
    public Units meters;
    public Units degs;

    //Vehicle dimensions radii
    public double frontTyreRadius = 0.228599;           //meters
    public double rearTyreRadius = 0.228599;            //meters
    public double wheelBase = 61;                       //inches (i know, sorry)
    public double radResBig = 10000;                    //Pretty sure this can be any big number.

    // Subtract object
    public SubtractPartsOperation subtract;

    //Parameters for user flow characteristics
    private ScalarGlobalParameter freestreamParameter;
    private ScalarGlobalParameter userYaw;
    private ScalarGlobalParameter userFreestream;
    private ScalarGlobalParameter frontRide;
    private ScalarGlobalParameter rearRide;
    private ScalarGlobalParameter sideSlip;
    private ScalarGlobalParameter userSteering;

    //Flags to track sim status
    public boolean fullCarFlag;             //True if full car domain detected
    public boolean wtFlag;                  //True if user wants WT (no ground velocity, no tyre rotation)
    public boolean DESFlag;

    //Stopping criteria
    public MonitorIterationStoppingCriterion maxVel;
    public int maxSteps;
    public MonitorIterationStoppingCriterion maxStepStop;
    public MaxReport maxVelocity;
    public AbortFileStoppingCriterion abortFile;    //Don't think we're using this for anything right now.
    public UpdateEvent monitorWaypoint;             //Only for transient.

    // Physics
    public PhysicsContinuum steadyStatePhysics;
    public PhysicsContinuum desPhysics;
    public double freestreamVal;
    public boolean dualRadFlag;
    public boolean fanFlag;

    // Regions
    private String subtractName = "Subtract";
    public Region radiatorRegion;
    public Region dualRadiatorRegion;
    public Region domainRegion;
    public BoundaryInterface massFlowInterfaceInlet;
    public BoundaryInterface massFlowInterfaceOutlet;
    public BoundaryInterface fanInterface;
    public BoundaryInterface dualFanInterface;
    public String massFlowInterfaceNameInlet;
    public String massFlowInterfaceNameOutlet;
    public String dualMassFlowInterfaceNameInlet;
    public String dualMassFlowInterfaceNameOutlet;
    public BoundaryInterface dualMassFlowInterfaceInlet;
    public BoundaryInterface dualMassFlowInterfaceOutlet;
    public CylindricalCoordinateSystem frontWheelCoord;
    public CylindricalCoordinateSystem rearWheelCoord;
    public CylindricalCoordinateSystem frontWheelSteering;
    public Boundary fsInlet;                            //fs refers to freestream here
    public Boundary leftPlane;
    public Boundary groundPlane;
    public Boundary fsOutlet;
    public Boundary symPlane;
    public Boundary topPlane;
    public CartesianCoordinateSystem radiatorCoord;
    public CartesianCoordinateSystem rollAxis;          //I don't think I'm using this for anything right now. But there's some amount of code in here that allows for roll adjustments. This is necessary for that.
    public CartesianCoordinateSystem dualRadCoord;
    public BoundaryInterface yawInterface;              //This is necessary for doing yaw correctly.
    private Collection<Boundary> dualRadBounds;
    private Collection<Boundary> domainRadBounds;
    public Boundary dualRadInlet;
    public Boundary dualRadOutlet;
    public Boundary radInlet;            // There are two sets of these corresponding to the two regions. Need these for interfacing
    public Boundary radOutlet;
    public Boundary domainRadInlet;
    public Boundary domainRadOutlet;
    public Boundary domainDualRadInlet;
    public Boundary domainDualRadOutlet;
    public Boundary domainFanBound;
    public Boundary radFanBound;
    public Boundary dualRadFanBound;
    public Boundary dualDomainFanBound;


    //Scenes and displayers
    public PlaneSection crossSection;
    public Scene scene2D;
    public Scene scene3D;
    public String separator;
    public FvRepresentation finiteVol;
    public String dir;
    public String simName;
    public Scene meshScene;
    public Screenplay aftFore;
    public Screenplay profile;
    public Screenplay topBottom;

    //Mesh controls
    public AutoMeshOperation autoMesh;
    public SurfaceCustomMeshControl frontWingControl;
    public SurfaceCustomMeshControl rearWingControl;
    public SurfaceCustomMeshControl sideWingControl;
    public SurfaceCustomMeshControl undertrayControl;
    public SurfaceCustomMeshControl bodyworkControl;
    public SurfaceCustomMeshControl groundControl;
    public SurfaceCustomMeshControl freestreamControl;
    public VolumeCustomMeshControl volControlCar;
    public VolumeCustomMeshControl volControlWake;
    public VolumeCustomMeshControl farWakeControl;
    public VolumeCustomMeshControl radiatorControl;
    public VolumeCustomMeshControl volControlWingWake;
    public VolumeCustomMeshControl volControlRearWing;
    public VolumeCustomMeshControl volControlFrontWing;
    public VolumeCustomMeshControl volControlUnderbody;
    public MeshOperationPart subtractPart;
    public SimpleBlockPart domain;
    public SurfaceWrapperAutoMeshOperation surfaceWrapOperation;
    public SurfaceWrapperAutoMeshOperation surfaceWrapOperationPPM;
    public SurfaceCustomMeshControl aeroSurfaceWrapper;
    public SurfaceCustomMeshControl aeroSurfaceWrapperPPM;
    public GeometryPart dualRadPart;
    public GeometryPart radPart;
    public GeometryPart volumetricWake;
    public GeometryPart volumetricCar;
    public GeometryPart volumetricRearWing;
    public GeometryPart volumetricFrontWing;
    public GeometryPart volumetricUnderbody;
    public GeometryPart volumetricWingWake;
    public GeometryPart farWakePart;

    // Constructor
    public simComponents(Simulation inputSim) {

        long startTime = System.currentTimeMillis();

        // Initialize simulation
        activeSim = inputSim;

        //Blow up if it's the wrong version
        checkVersion();

        //Define user parameters
        userParameters();

        // Units
        noUnit = activeSim.getUnitsManager().getObject("");
        inches = activeSim.getUnitsManager().getObject("in");
        degs = activeSim.getUnitsManager().getObject("deg");
        meters = activeSim.getUnitsManager().getObject("m");

        // Initialize surface wrappers
        surfaceWrapOperation = ((SurfaceWrapperAutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject("Surface wrapper"));
        surfaceWrapOperationPPM = ((SurfaceWrapperAutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject("Surface wrapper PPM"));
        aeroSurfaceWrapper = (SurfaceCustomMeshControl) surfaceWrapOperation.getCustomMeshControls().getObject("Aero Control");
        aeroSurfaceWrapperPPM = (SurfaceCustomMeshControl) surfaceWrapOperationPPM.getCustomMeshControls().getObject("Aero Control");


        // Part management. Get an object to hold all parts.
        allParts = activeSim.getGeometryPartManager().getParts();

        //Start filtering parts into categories
        aeroParts = new ArrayList<>();
        nonAeroParts = new ArrayList<>();
        wheels = new ArrayList<>();
        radiator = new ArrayList<>();
        dualRadiator = new ArrayList<>();
        liftGenerators = new ArrayList<>();
        //This does all the filtering.
        for (GeometryPart prt : allParts) {
            String prtName = prt.getPresentationName();
            for (String prefix : aeroPrefixes) {
                if (prtName.startsWith(prefix)) {
                    aeroParts.add(prt);
                }
            }
            for (String prefix : nonAeroPrefixes) {
                if (prtName.startsWith(prefix))
                    nonAeroParts.add(prt);
            }
            for (String prefix : wheelNames) {
                if (prtName.startsWith(prefix))
                    wheels.add(prt);
            }
            for (String prefix : liftGeneratorPrefixes) {
                if (prtName.startsWith(prefix)) {
                    liftGenerators.add(prt);
                }
            }
            if (prtName.startsWith(radiatorName))
                radiator.add(prt);

            if (prtName.startsWith(dualRadiatorName))
                dualRadiator.add(prt);
        }
        dualRadFlag = dualRadiator.size() != 0;     //Sets dual rad flag to true if populateAllParts finds something for dualRadFlag

        // Set up radiator regions
        try {
            domainRegion = assignRegion(DOMAIN_REGION);
            radiatorRegion = assignRegion(RADIATOR_REGION);
            massFlowInterfaceNameInlet = "Inlet interface";
            massFlowInterfaceNameOutlet = "Outlet interface";
            if (dualRadFlag) {
                dualRadiatorRegion = assignRegion(DUAL_RADIATOR_REGION);
                dualMassFlowInterfaceNameInlet = "Dual inlet interface";
                dualMassFlowInterfaceNameOutlet = "Dual outlet interface";
            }
        } catch (Exception e) {
            activeSim.println("simComponents.java - Couldn't find/create domain or radiator region");
        }


        // Extract radiator boundaries from radiator regions.
        domainBounds = Objects.requireNonNull(domainRegion).getBoundaryManager().getBoundaries();
        dualRadBounds = new ArrayList<>();
        radBounds = Objects.requireNonNull(radiatorRegion).getBoundaryManager().getBoundaries();
        if (dualRadFlag)
            dualRadBounds = Objects.requireNonNull(dualRadiatorRegion).getBoundaryManager().getBoundaries();

        // Takes all boundaries, filters them into freestream, parts, and wheels.
        freestreamBounds = new ArrayList<>();
        wheelBounds = new ArrayList<>();
        partBounds = new ArrayList<>();
        partSpecBounds = new HashMap<>();
        domainRadBounds = new ArrayList<>();
        boundarySet();

        // Set up coordinate systems
        activeSim.getCoordinateSystemManager().getLabCoordinateSystem();

        setupCoordinates();

        // Set up scenes, representations, and views.

        sceneSetup();


        // Set up subtract
        subtract = (SubtractPartsOperation) activeSim.get(MeshOperationManager.class).getObject(subtractName);
        mesherSetup();


        // Set up reports
        reports = activeSim.getReportManager().getObjects();
        pitchRepName = "Pitch Moment Coefficient";
        massFlowRepName = "Radiator Mass Flow";
        String pointName = "Point";
        point = (PointPart) activeSim.getPartManager().getObject(pointName);
        repTable = (XyzInternalTable) activeSim.getTableManager().getTable("Reports table");
        // THESE KEYS MUST MATCH THE CONVENTION USED IN AERO PREFIXES
        forceTables = new HashMap<>();
        forceTables.put("FW", (AccumulatedForceTable) activeSim.getTableManager().getTable("FW Force Histogram"));
        forceTables.put("RW", (AccumulatedForceTable) activeSim.getTableManager().getTable("RW Force Histogram"));


        // Miscellaneous constructor things
        radPart = activeSim.get(SimulationPartManager.class).getObject(radiatorName);
        subtractPart = (MeshOperationPart) activeSim.get(SimulationPartManager.class).getPart(subtractName);
        if (dualRadFlag)
            dualRadPart = activeSim.get(SimulationPartManager.class).getObject(dualRadiatorName);

        // Plots
        plots = activeSim.getPlotManager().getPlots();

        //Define domain sizes
        domainCatch();
        fullCarFlag = domainSizing();
        if (!fullCarFlag)
            profileLimits[1] = 0;

        //Set up fan flag
        fanFlag = boolEnv("FAN");

        //Set physics objects
        physicsSet();

        //No autosave
        activeSim.getSimulationIterator().getAutoSave().getStarUpdate().setEnabled(false);
        activeSim.getSimulationIterator().getAutoSave().setAutoSaveMesh(false);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        activeSim.println("Time taken to generate simComponents : " + totalTime + " ms");


    }


    //Assigns user parameters in the sim file to their associated java objects. Makes it easier to refer to them later

    private void userParameters()
    {
        userFreestream = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_FREESTREAM);
        userYaw = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_YAW);
        frontRide = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_FRONT_RIDE_HEIGHT);
        rearRide = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_REAR_RIDE_HEIGHT);
        sideSlip = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(SIDESLIP);
        userSteering = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_STEERING);
    }

    //Sets up boundary conditions. It's generally a good idea to avoid touching things (especially boundaries) when you can avoid it.
    private void boundarySet() {

        //Iterates through every boundary in domainBounds and checks for prexies.
        for (Boundary bound : domainBounds) {
            int partFlag = 0;                                      // Helps with if-logic to look for parts
            String boundName = bound.getPresentationName();

            //check for domain
            if (boundName.contains(freestreamPrefix)) {
                freestreamBounds.add(bound);
                partFlag = 1;
            }
            //check for wheels
            else {
                for (String wheelName : wheelNames) {
                    if (boundName.contains(wheelName)) {
                        wheelBounds.add(bound);
                        partFlag = 1;
                    }
                }
            }
            // if it isn't a domain or a wheel, it's a "part", and gets added to part bounds.
            if (partFlag == 0) {
                partBounds.add(bound);
            }
            // here we filter for the radiator
            if (partFlag == 0) {
                if (boundName.contains("RADIATOR")) {
                    domainRadBounds.add(bound);
                }
            }
        }


        //Get inlet and outlet radiator boundaries in the domain region for reports. If it fails here, it's because the rad surfaces haven't been split
        for (Boundary bound : partBounds) {
            String boundName = bound.getPresentationName();
            if (boundName.contains("Inlet")) {
                if (boundName.contains(radiatorName))
                    domainRadInlet = bound;
                else if (boundName.contains(dualRadiatorName))
                    domainDualRadInlet = bound;
            }
            else if (boundName.contains("Outlet") && !boundName.contains("Fan")) {
                if (boundName.contains(radiatorName))
                    domainRadOutlet = bound;
                else if (boundName.contains(dualRadiatorName))
                    domainDualRadOutlet = bound;
            }
            else if (boundName.contains("Fan Outlet"))
            {
                if (boundName.contains(radiatorName))
                    domainFanBound = bound;
                else if (boundName.contains(dualRadiatorName))
                    dualDomainFanBound = bound;
            }

            //Positively select aero parts, and throw them into partSpecBounds

            for (String prefix : aeroPrefixes) {
                if (boundName.contains(prefix) && !boundName.toLowerCase().contains("suspension"))      // Janky code so CFD_SUSPENSION doesn't trigger the NS prefix.

                {
                    Collection<Boundary> temp = new ArrayList<>();
                    //Part spec bounds keys based on prefix, helps when setting up reports
                    if (partSpecBounds.containsKey(prefix))
                        temp = partSpecBounds.get(prefix);

                    temp.add(bound);
                    partSpecBounds.put(prefix, temp);
                }
            }
        }

        //This is for the radiator region now.
        for (Boundary bound : radBounds) {
            String boundName = bound.getPresentationName();

            if (boundName.contains("Inlet"))
                radInlet = bound;
            if (boundName.contains("Outlet"))
                radOutlet = bound;
            if (boundName.contains("Fan"))
                radFanBound = bound;
        }

        //Now for the other radiator.
        for (Boundary bound : dualRadBounds) {

            String boundName = bound.getPresentationName();

            if (boundName.contains("Inlet"))
                dualRadInlet = bound;
            if (boundName.contains("Outlet"))
                dualRadOutlet = bound;
            if (boundName.contains("Fan"))
                dualRadFanBound = bound;
        }

        //Filter out freestream boundaries to make it easier to set up boundary conditions later.
        for (Boundary fsBound : freestreamBounds)
        {
            //The check for YAW_INTERFACE is so i can keep using String.contains.
            String boundName = fsBound.getPresentationName();
            if (boundName.contains("Left") && !boundName.contains(simComponents.YAW_INTERFACE_NAME))
                leftPlane = fsBound;
            else if (boundName.contains("Symmetry") && !boundName.contains(simComponents.YAW_INTERFACE_NAME))
                symPlane = fsBound;
            else if (boundName.contains("Top"))
                topPlane = fsBound;
            else if (boundName.contains("Inlet"))
                fsInlet = fsBound;
            else if (boundName.contains("Ground"))
                groundPlane = fsBound;
            else if (boundName.contains("Outlet"))
                fsOutlet = fsBound;
        }
    }

    //Sets up continuua and populates the appropriate stopping criteria. The stopping criteria are a function of steady vs transient. It's been a while since I've used this for transient, I'm not sure if the transient code still works.
    private void physicsSet() {
        tagContinua();

        // Flags
        freestreamVal = valEnv("freestream");
        DESFlag = boolEnv("DES");
        wtFlag = boolEnv("windTunnel");
        setFreestreamParameterValue();

        //Stopping criteria
        maxSteps = (int) valEnv("maxSteps");
        maxStepStop = (MonitorIterationStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Iterations");
        maxVel = (MonitorIterationStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Velocity Monitor Criterion");
        maxVelocity = (MaxReport) activeSim.getReportManager().getReport("Maximum Velocity");
        abortFile = (AbortFileStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Stop File");
        monitorWaypoint = activeSim.getUpdateEventManager().getUpdateEvent("Monitor Waypoint");

    }

    public void setFreestreamParameterValue() {
        freestreamParameter = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(FREESTREAM_PARAMETER_NAME);
        freestreamParameter.getQuantity().setValue(freestreamVal);
    }

    //This is assigning the continuua objects in the sim to their java objects.
    private void tagContinua() {
        // Define physics block
        desPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("DES");

        //If there isn't a continuum named steady state, it will default to a continuum named S-a physics. This logic was largely for backwards compatibility, and could probably be removed.
        if (activeSim.getContinuumManager().has("Steady state"))
            steadyStatePhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("Steady state");
        else if (activeSim.getContinuumManager().has("S-a physics"))
        {
            steadyStatePhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("S-a physics");
            steadyStatePhysics.setPresentationName("Steady state");
        }
        else
        {
            // I don't know if RuntimeException is the right exception class to throw. It probably isn't, but it gets the job done.
            throw new RuntimeException("No physics continuum found for steady state. Check physicsSet() in simComponents.java for logic");
        }
    }

    //Assigns the freestream domain in the sim to its java object. Doesn't throw a killer exception. Could probably be modified to throw one. It's very unlikely the macro is going to get very far without a freestream anyway.
    private void domainCatch() {
        try {
            domain = (SimpleBlockPart) activeSim.get(SimulationPartManager.class).getPart("Freestream");
        } catch (NullPointerException e) {
            activeSim.println(this.getClass().getName() + " - Domain could not be caught");
        }
    }

    //Returns true for full car. False for half car. Based purely on whether or not the y-coordinate of the domain block extends beyong positive +0.5 meters. PLEASE KEEP USING METERS.
    private boolean domainSizing() {

        double[] domainCorner = domain.getCorner1().evaluate().toDoubleArray();
        if (domainCorner[1] > 0.5) {
            activeSim.println("Full car domain detected");
            return true;
        } else {
            activeSim.println("Half car domain detected");
            return false;
        }
    }


    //Populate mesh objects with their STAR objects. This function doesn't do any processing, it's just object initialization.
    private void mesherSetup() {
        // Set up mesher
        try {

            // Get volumetric blocks
            SimulationPartManager partMan = activeSim.get(SimulationPartManager.class);
            volumetricCar = partMan.getPart("Volumetric Control Car");
            volumetricWake = partMan.getPart("Volumetric Control Wake");
            volumetricFrontWing = partMan.getPart("Front Wing Control");
            volumetricRearWing = partMan.getPart("Rear Wing Control");
            volumetricUnderbody = partMan.getPart("Underbody Control");
            volumetricWingWake = partMan.getPart("Rear Wing Wake");
            farWakePart = partMan.getPart("Far wake");


            // Get mesher
            autoMesh = (AutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject("Automated Mesh");

            // Get custom controls

            CustomMeshControlManager getCustomControl = autoMesh.getCustomMeshControls();
            groundControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Ground");
            freestreamControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Freestream");
            rearWingControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Rear Wing");
            frontWingControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Front Wing");
            bodyworkControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Bodywork");
            sideWingControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Side Wing");
            undertrayControl = (SurfaceCustomMeshControl) getCustomControl.getObject("Surface Control Undertray");
            volControlWake = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Wake");
            volControlCar = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Car");
            volControlWingWake = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Rear Wing Wake");
            volControlRearWing = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Rear Wing");
            volControlFrontWing = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Front Wing");
            volControlUnderbody = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Underbody");
            farWakeControl = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Far Wake");
            radiatorControl = (VolumeCustomMeshControl) getCustomControl.getObject("Volumetric Control Radiator");
        } catch (Exception e) {
            activeSim.println(this.getClass().getName() + " - Mesh initializer error");
        }
    }

    public void clearMesh() {
        activeSim.getMeshPipelineController().clearGeneratedMeshes();
    }

    private void sceneSetup() {
        try {
            crossSection = (PlaneSection) activeSim.getPartManager().getObject("Plane Section");
            scene2D = activeSim.getSceneManager().getScene("2D scenes");
            scene2D.setMeshOverrideMode(SceneMeshOverride.USE_DISPLAYER_PROPERTY);
            scene3D = activeSim.getSceneManager().getScene("3D scenes");
            meshScene = activeSim.getSceneManager().getScene("Mesh");
            separator = File.separator;
            dir = activeSim.getSessionDir();
            simName = activeSim.getPresentationName();
            finiteVol = (FvRepresentation) activeSim.getRepresentationManager().getObject("Volume Mesh");
            aftFore = activeSim.get(ScreenplayManager.class).getObject("Screenplay 1");
            profile = activeSim.get(ScreenplayManager.class).getObject("Screenplay 2");
            topBottom = activeSim.get(ScreenplayManager.class).getObject("Screenplay 3");
        } catch (Exception e) {
            activeSim.println(this.getClass().getName() + " - Scene or displayer lookup failed, or volume mesh not found");
        }
    }

    //Scales a vector with a scale.
    //example: vectorScale(2, [1, 1, 1]) = [2, 2, 2]
    public static double[] vectorScale(double scalar, double[] vect) {
        vect = vect.clone();
        for (int i = 0; i < vect.length; i++)
            vect[i] *= scalar;

        return vect;
    }

    // I don't like this function, but it's a simple one. There's got to be a cleaner way to do this but, this works for now.
    public double valEnv(String env) {
        if (System.getenv(env) != null && !System.getenv(env).isEmpty())
            return Double.parseDouble(System.getenv(env));
        else if (env.equals("freestream"))
        {
            return userFreestream.getQuantity().getRawValue();
        }
        else if (env.equals("yaw"))
        {
            return userYaw.getQuantity().getRawValue();
        }
        else if (env.equals("frh"))
        {
            return frontRide.getQuantity().getRawValue();
        }
        else if (env.equals("rrh"))
        {
            return rearRide.getQuantity().getRawValue();
        }
        else if (env.equals(CONFIGSIDESLIP))
        {
            return sideSlip.getQuantity().getRawValue();
        }
        else if (env.equals(STEERING))
            return userSteering.getQuantity().getRawValue();
        else if (env.equals("maxSteps"))
            return 1100;
        else
            return 0;
    }

    //This is dumber than valEnv. Just pulls the sysenv and returns it as a string.
    public static String valEnvString(String env) {
        if (System.getenv(env) != null && !System.getenv(env).isEmpty())
            return System.getenv(env);
        else
            return null;
    }

    //Returns true if true, false if false. Needs to parse the string into a boolean. There's some legacy code for the domainSet flag, but this could be removed.
    public static boolean boolEnv(String env) {

        // Read the sys environment to figure out if you want a full car or a half car sim
        if (env.equals("domainSet") && System.getenv(env) != null && System.getenv(env).toLowerCase().equals("half"))
            return true;
        return System.getenv(env) != null && System.getenv(env).toLowerCase().equals("true");

    }


    //Initialize coordinate systems with their STAR objects.
    private void setupCoordinates() {
        try {
            radiatorCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Radiator Cartesian");
            frontWheelSteering = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Front Wheel Steering");
            frontWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Front Wheel Cylindrical");
            rearWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Rear Wheel Cylindrical");
            if (dualRadFlag)
                dualRadCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().
                        getCoordinateSystem("Dual Radiator Cartesian");

        } catch (Exception e) {
            activeSim.println("simComponents.java - Coordinate system lookup failed");
        }
        try {
            rollAxis = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Roll axis");
        } catch (Exception e) {
            activeSim.println(this.getClass().getName() + " rollAxis not found");
            //createRollAxis();
        }
    }

    // Removes old regions. Creates new ones. Avoid using this function too if you can avoid it. The way this does things is very destructive.
    public void regionSwap() {

        try {
            for (Region x : activeSim.getRegionManager().getRegions())
            {
                activeSim.getRegionManager().removeRegion(x);
            }
        } catch (Exception e) {
            activeSim.println("Old regions not found");
        }

        try {
            radPart = activeSim.get(SimulationPartManager.class).getObject(radiatorName);
            subtractPart = (MeshOperationPart) activeSim.get(SimulationPartManager.class).getPart(subtractName);
            if (dualRadFlag)
                dualRadPart = activeSim.get(SimulationPartManager.class).getObject(dualRadiatorName);
        } catch (Exception e) {
            activeSim.println("Can't create new regions");
        }
        try {
            if (dualRadFlag) {
                activeSim.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[]{radPart, subtractPart, dualRadPart}),
                        "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null,
                        "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);
            } else {
                activeSim.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[]{radPart, subtractPart}),
                        "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null,
                        "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);
            }
        } catch (NullPointerException e) {
            activeSim.println(this.getClass().getName() + " - Region swap failed");
        }

        domainRegion = assignRegion(DOMAIN_REGION);
        radiatorRegion = assignRegion(radiatorName);
        radiatorRegion.setPresentationName(RADIATOR_REGION);
        if (dualRadFlag) {
            dualRadiatorRegion = assignRegion(dualRadiatorName);
            dualRadiatorRegion.setPresentationName(DUAL_RADIATOR_REGION);
        }

    }

    //I have no idea why this is here. But it reverses an array. eg [1, 2, 3] -> [3, 2, 1]
    public static Object[] reverseArr(Object [] arr)
    {
        for (int i = 0; i < arr.length / 2 ; i++)
        {
            Object temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }

        return arr;
    }

    public void saveSim() {
        String newName = System.getenv("newName");

        if (newName != null)
            activeSim.saveState(newName);
        else
            activeSim.saveState(activeSim.getSessionDir() + File.separator + activeSim.getPresentationName() + ".sim");
    }

    public void killSim() {
        activeSim.kill();
    }

    public void clearHistory() {
        activeSim.getSolution().clearSolution(Solution.Clear.History);
    }

    public void clearSoln() {
        activeSim.getSolution().clearSolution(Solution.Clear.Fields);
        clearHistory();
    }

    //If a region named regName exists, it'll return that region, otherwise it'll create a new empty region and name it regName
    private Region assignRegion(String regName) {
        Region output;
        if (activeSim.getRegionManager().has(regName))
            output = activeSim.getRegionManager().getRegion(regName);
        else {
            output = activeSim.getRegionManager().createEmptyRegion();
            output.setPresentationName(regName);
        }
        return output;
    }

    //Some 2D vector transformation.
    public static double[] vectorRotate(double angle, double[] arr) {
        double r = Math.hypot(arr[0], arr[1]);
        double x = r * Math.cos(angle);
        double y = -r * Math.sin(angle);

        arr[0] = x;
        arr[1] = y;

        return arr;

    }

    //Explode if the version in the macro doesn't match the version in the sim parameter.
    private void checkVersion()
    {
        ScalarGlobalParameter versionParam;
        if (activeSim.get(GlobalParameterManager.class).has("Version checker"))
        {
            versionParam = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject("Version checker");
        }
        else
        {
            return;
        }
        double val = versionParam.getQuantity().getRawValue();
        if (val != version)
        {
            activeSim.println("Version: " + version);
            throw new IllegalStateException("You're using the wrong macro + sim combination.");
        }
    }

    //Convert a sideslip angle into an actionable y-component of velocity for the ground. Obviously, since it's using a tangent function, don't use very high and unrealistic sideslip angles.
    public double calculateSideslip()
    {
        double sideslipAngle = valEnv(CONFIGSIDESLIP);
        double yVal = freestreamVal * Math.tan(Math.toRadians(sideslipAngle));
        return yVal;
    }

}
