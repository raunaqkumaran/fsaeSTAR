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

    //Some string constants. This is something I started doing later on, and haven't done for every string. Probably a good idea to do so though.

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

    //A bunch of declarations. Don't read too much into the access modifiers, they're not a big deal for a project like this.

    //Version check. An easy way to make sure the sim and the macros are the same version. Throw an error at the beginning, rather than an uncaught NPE later. This needs to match the version parameter in STAR.
    private double version = 2.0;

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
    public String freestreamPrefix = "Freestream";                                                                      //This prefix differentiates from geometry, and the domain itself.
    public Map<String, Collection<Boundary>> partSpecBounds;
    private Collection<GeometryPart> allParts;
    private Collection<GeometryPart> radiator;
    private Collection<GeometryPart> dualRadiator;
    private String[] nonAeroPrefixes = {"CFD", "DONTGIVE", "NS"};                                                       //These are prefixes for non-aero parts. Everything other than aero and tyres must have one of these prefixes.
    private String[] wheelNames = {"Front Left", "Front Right", "Rear Left", "Rear Right"};                             //Names for wheels. Must be exact.
    private String radiatorName = "CFD_RADIATOR";
    private String dualRadiatorName = "CFD_DUAL_RADIATOR";
    private Collection<Boundary> liftGeneratorBounds;

    //Double arrays to hold ranges for scenes and plane section sweeps. Limits are in inches, and control how far the cross sections will go. Pressures are Cps.
    public double[] profileLimits = {-29, 29};
    public double[] aftForeLimits = {-70, 55};
    public double[] utLimits = {0.35, 10};
    public double[] topBottomLimits = {10, 60};
    public double[] pressRange = {-2.5, 1.5};
    public double[] totalPressRange = {-2.5, 1.5};
    public double[] velRange = {0, 2};
    public double[] wallYRange = {3, 30};
    public double[] wallYRangeNearWall = {0.0, 3};
    public double[] utTopBottom = {0.35, 10};

    //These define the vector direction for cross section scenes.
    public double[] foreAftDirection = {1, 0, 0};
    public double[] profileDirection = {0, -1, 0};
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

    //Flags to track sim status
    public boolean fullCarFlag;             //True if full car domain detected
    public boolean wtFlag;                  //True if user wants WT (no ground velocity, no tyre rotation)

    //Stopping criteria
    public MonitorIterationStoppingCriterion maxVel;
    public int maxSteps;
    public MonitorIterationStoppingCriterion maxStepStop;
    public MaxReport maxVelocity;
    public AbortFileStoppingCriterion abortFile;    //Don't think we're using this for anything right now.
    public UpdateEvent monitorWaypoint;             //Only for transient.

    // Physics
    public PhysicsContinuum saPhysics;
    public PhysicsContinuum desPhysics;
    public double freestreamVal;
    public boolean dualRadFlag;

    private String subtractName = "Subtract";
    public Units degs;
    public SurfaceWrapperAutoMeshOperation surfaceWrapOperation;
    public SurfaceCustomMeshControl aeroSurfaceWrapper;
    public GeometryPart dualRadPart;
    public GeometryPart radPart;
    public GeometryPart volumetricWake;
    public GeometryPart volumetricCar;
    public GeometryPart volumetricRearWing;
    public GeometryPart volumetricFrontWing;
    public GeometryPart volumetricUnderbody;
    public GeometryPart volumetricWingWake;
    public GeometryPart farWakePart;
    public Boundary dualRadInlet;
    public Boundary dualRadOutlet;
    public Boundary radInlet;            // There are two sets of these corresponding to the two regions. Need these for interfacing
    public Boundary radOutlet;
    public Boundary domainRadInlet;
    public Boundary domainRadOutlet;
    public Boundary domainDualRadInlet;
    public Boundary domainDualRadOutlet;
    // Regions
    public Region radiatorRegion;
    public Region dualRadiatorRegion;
    public Region domainRegion;
    public BoundaryInterface massFlowInterfaceInlet;
    public BoundaryInterface massFlowInterfaceOutlet;
    public String massFlowInterfaceNameInlet;
    public String massFlowInterfaceNameOutlet;
    public String dualMassFlowInterfaceNameInlet;
    public String dualMassFlowInterfaceNameOutlet;
    public BoundaryInterface dualMassFlowInterfaceInlet;
    public BoundaryInterface dualMassFlowInterfaceOutlet;
    public CylindricalCoordinateSystem frontWheelCoord;
    public CylindricalCoordinateSystem rearWheelCoord;
    public Boundary fsInlet;
    public CartesianCoordinateSystem radiatorCoord;
    public CartesianCoordinateSystem rollAxis;
    public CartesianCoordinateSystem dualRadCoord;
    public BoundaryInterface yawInterface;
    public Boundary leftPlane;
    public Boundary groundPlane;
    public Boundary fsOutlet;
    public Boundary symPlane;
    public Boundary topPlane;
    private Collection<Boundary> dualRadBounds;
    private Collection<Boundary> domainRadBounds;
    //Scenes and displayers
    public PlaneSection crossSection;
    public Scene scene2D;
    public PartDisplayer meshDisplayer;
    public Scene scene3D;
    public String separator;
    public FvRepresentation finiteVol;
    public ScalarDisplayer wallY;
    public VectorDisplayer velVector2D;
    public ScalarDisplayer pressure2D;
    public ScalarDisplayer totalPressure2D;
    public ScalarDisplayer pressure3D;
    public String dir;
    public String simName;
    public Scene meshScene;
    public Screenplay aftFore;
    public Screenplay profile;
    public Screenplay topBottom;
    //Views
    public VisView carRear;
    public VisView carStd;
    public VisView utRear;
    public VisView utBottom;
    public VisView utProfile;
    public VisView fwBottom;
    private VisView fwRear;                                 //Some of these are collections in case we ever go to multiple views in the future.
    private Collection<VisView> fwViews;
    private Collection<VisView> rwViews;
    private Collection<VisView> carViews;
    private Collection<VisView> utViews;
    public VisView fwTop;
    public VisView fwProfile;
    public VisView rwTop;
    public VisView rwProfile;
    public VisView rwBottom;
    public VisView rwRear;

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
        aeroSurfaceWrapper = (SurfaceCustomMeshControl) surfaceWrapOperation.getCustomMeshControls().getObject("Aero Control");


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
        liftGeneratorBounds = new ArrayList<>();
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
        if (fullCarFlag)
            profileLimits = new double[]{-35, 35};

        //Set physics objects
        physicsSet();

        //No autosave
        activeSim.getSimulationIterator().getAutoSave().getStarUpdate().setEnabled(false);
        activeSim.getSimulationIterator().getAutoSave().setAutoSaveMesh(false);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        activeSim.println("Time taken to generate simComponents : " + totalTime + " ms");


    }

    private void userParameters()
    {
        userFreestream = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_FREESTREAM);
        userYaw = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_YAW);
        frontRide = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_FRONT_RIDE_HEIGHT);
        rearRide = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(USER_REAR_RIDE_HEIGHT);
        sideSlip = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(SIDESLIP);
    }

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

                //Separate category for lift generators
                for (String prefix : liftGeneratorPrefixes) {
                    if (boundName.contains(prefix)) {
                        liftGeneratorBounds.add(bound);
                        partFlag = 1;
                    }
                }
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
            } else if (boundName.contains("Outlet")) {
                if (boundName.contains(radiatorName))
                    domainRadOutlet = bound;
                else if (boundName.contains(dualRadiatorName))
                    domainDualRadOutlet = bound;
            }

            //Filter out for aero parts.

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
        }

        //Now for the other radiator.
        for (Boundary bound : dualRadBounds) {

            String boundName = bound.getPresentationName();

            if (boundName.contains("Inlet"))
                dualRadInlet = bound;
            if (boundName.contains("Outlet"))
                dualRadOutlet = bound;
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

    private void physicsSet() {
        tagContinua();

        // Flags
        freestreamVal = valEnv("freestream");
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

    private void tagContinua() {
        // Define physics block
        desPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("DES");

        if (activeSim.getContinuumManager().has("Steady state"))
            saPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("Steady state");
        else if (activeSim.getContinuumManager().has("S-a physics"))
        {
            saPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum("S-a physics");
            saPhysics.setPresentationName("Steady state");
        }
        else
        {
            throw new RuntimeException("No physics continuum found for steady state. Check physicsSet() in simComponents.java for logic");
        }
    }

    private void domainCatch() {
        try {
            domain = (SimpleBlockPart) activeSim.get(SimulationPartManager.class).getPart("Freestream");
        } catch (NullPointerException e) {
            activeSim.println(this.getClass().getName() + " - Domain could not be caught");
        }
    }

    //Returns true for full car. False for half car. The whole first 70% of this is legacy code that isn't used for much any more.


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
        try {
            fwViews = new ArrayList<>();
            rwViews = new ArrayList<>();
            carViews = new ArrayList<>();
            utViews = new ArrayList<>();

            carStd = activeSim.getViewManager().getObject("Car standard");
            carRear = activeSim.getViewManager().getObject("Car Rear");
            carViews.addAll(Arrays.asList(carRear, carStd));

            utBottom = activeSim.getViewManager().getObject("UT Bottom");
            utRear = activeSim.getViewManager().getObject("UT Rear");
            utProfile = activeSim.getViewManager().getObject("UT Profile");
            utViews.addAll(Arrays.asList(utBottom, utRear, utProfile));

            fwBottom = activeSim.getViewManager().getObject("FW Bottom");
            fwProfile = activeSim.getViewManager().getObject("FW Profile");
            fwRear = activeSim.getViewManager().getObject("FW Rear");
            fwTop = activeSim.getViewManager().getObject("FW Top");
            fwViews.addAll(Arrays.asList(fwBottom, fwProfile, fwRear, fwTop));

            rwProfile = activeSim.getViewManager().getObject("RW Profile");
            rwBottom = activeSim.getViewManager().getObject("RW Bottom");
            rwTop = activeSim.getViewManager().getObject("RW Top");
            rwRear = activeSim.getViewManager().getObject("RW Rear");
            rwViews.addAll(Arrays.asList(rwProfile, rwBottom, rwTop, rwRear));

        } catch (Exception e) {
            activeSim.println("simComponents.java - View lookup failed");
        }
    }

    public static double[] vectorScale(double scalar, double[] vect) {
        vect = vect.clone();
        for (int i = 0; i < vect.length; i++)
            vect[i] *= scalar;

        return vect;
    }

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
        else if (env.equals("maxSteps"))
            return 1100;
        else
            return 0;
    }

    public static String valEnvString(String env) {
        if (System.getenv(env) != null && !System.getenv(env).isEmpty())
            return System.getenv(env);
        else
            return null;
    }

    public static boolean boolEnv(String env) {

        // Read the sys environment to figure out if you want a full car or a half car sim
        if (env.equals("domainSet") && System.getenv(env) != null && System.getenv(env).toLowerCase().equals("full"))
            return true;
        return System.getenv(env) != null && System.getenv(env).toLowerCase().equals("true");

    }


    private void setupCoordinates() {
        try {
            radiatorCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem("Radiator Cartesian");
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

    public void regionSwap() {
        // Removes old regions. Creates new ones. There's some variable casting going on which I don't understand
        // but STAR seems to require it.
        try {
            activeSim.getRegionManager().removeRegion(domainRegion);
            activeSim.getRegionManager().removeRegion(radiatorRegion);
            if (dualRadFlag)
                activeSim.getRegionManager().removeRegion(dualRadiatorRegion);
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

    public static double[] vectorRotate(double angle, double[] arr) {
        double r = Math.hypot(arr[0], arr[1]);
        double x = r * Math.cos(angle);
        double y = -r * Math.sin(angle);

        arr[0] = x;
        arr[1] = y;

        return arr;

    }

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

    public double calculateSideslip()
    {
        double sideslipAngle = valEnv(CONFIGSIDESLIP);
        double yVal = freestreamVal * Math.tan(Math.toRadians(sideslipAngle));
        return yVal;
    }

}
