package simComponents;

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
import star.meshing.*;
import star.surfacewrapper.SurfaceWrapperAutoMeshOperation;
import star.vis.*;
import java.io.File;
import java.util.*;


public class simComponents {

    //Declarations. There may be 'repeated' parts. Some of this is because of typecasting that I don't understand
    //I'm not going to comment everything. I'm hoping the variable name is usually obvious enough. Some of them aren't
    //obvious. At some point i'll make docstrings. But that's not going to happen until i'm literally dying of boredom/1 week away from graduating.

    //Version check
    private double version = 1.1;
    // The Simulation
    public Simulation activeSim;

    // Surface wrapper
    public String surfaceWrap;
    public Collection<GeometryPart> aeroParts;
    public Collection<GeometryPart> nonAeroParts;
    public Collection<GeometryPart> wheels;
    private Collection<GeometryPart> liftGenerators;
    public Collection<String> aeroPrefixes;
    private Collection<String> liftGeneratorPrefixes;
    public Collection<Boundary> domainBounds;
    public Collection<Boundary> radBounds;
    public Collection<Boundary> freestreamBounds;
    public Collection<Boundary> partBounds;
    public Collection<Boundary> wheelBounds;
    public String freestreamPrefix;
    public Map<String, Collection<Boundary>> partSpecBounds;
    public double[] profileLimits;
    public double[] aftForeLimits;
    public double[] topBottomLimits;
    public double[] foreAftDirection;
    public double[] profileDirection;
    public double[] topBottomDirection;
    public double[] utTopBottom;
    public double[] pressRange;
    public double[] wallYRange;
    public double[] wallYRangeNearWall;
    // Subtract
    public SubtractPartsOperation subtract;
    public Collection<Report> reports;
    public String massFlowRepName;
    public String pitchRepName;
    public PointPart point;
    public XyzInternalTable repTable;
    public Collection<StarPlot> plots;
    // Miscellaneous
    public double frontTyreRadius;
    public double rearTyreRadius;
    public double radResBig;                    // These define the resistance tensors for the rad region
    public double wheelBase;
    public Units noUnit;
    public Units inches;
    public Units meters;
    public Map<String, double[]> freestreamSize;
    public Map<String, double[]> halfFreestreamSize;
    public String corner1;
    public String corner2;
    public boolean fullCarFlag;
    public boolean wtFlag;
    private ScalarGlobalParameter freestreamParameter;
    public String freestreamParameterName;
    //Stopping criteria
    public MonitorIterationStoppingCriterion maxVel;
    public int maxSteps;
    public MonitorIterationStoppingCriterion maxStepStop;
    public MaxReport maxVelocity;
    public AbortFileStoppingCriterion abortFile;
    public UpdateEvent monitorWaypoint;
    // Physics
    public PhysicsContinuum saPhysics;
    public PhysicsContinuum desPhysics;
    public double freestreamVal;
    public boolean dualRadFlag;
    // Parts
    private Collection<GeometryPart> allParts;
    private Collection<GeometryPart> radiator;
    private Collection<GeometryPart> dualRadiator;
    private Collection<String> nonAeroPrefixes;
    private Collection<String> wheelNames;
    private String radiatorName;
    private String dualRadiatorName;
    private Collection<Boundary> liftGeneratorBounds;
    private String radiatorRegionName;
    private String dualRadRegionName;
    private String domainRegionName;
    //Coordinate systems
    private String rollAxisName;
    private String radiatorAxisName;
    private String frontWheelAxisName;
    private String rearWheelAxisName;
    private String dualRadiatorAxisName;
    public double[] velRange;
    private String subtractName;
    private String pointName;
    private String repTableName;
    private String maxVelName;
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
    //Scenes and displayers
    public PlaneSection crossSection;
    public Scene planeSectionScene;
    public Scene scene3D;
    public String separator;
    public FvRepresentation finiteVol;
    public ScalarDisplayer wallY;
    public VectorDisplayer velVector2D;
    public ScalarDisplayer pressure2D;
    public ScalarDisplayer pressure3D;
    public String dir;
    public String simName;
    //Views
    public VisView carRear;
    public VisView carStd;
    public VisView utRear;
    public VisView utBottom;
    public VisView utProfile;
    public VisView fwBottom;

    // Reports

    // I'm assuming reports are going to be PARTPREFIX LIFT/DRAG formatted. There shouldn't be any reason to do
    // things differently. Additional reports can be initialized here and added to the collection. I'm also assuming
    // the simulation already has the reports existing in it.
    public VisView fwTop;
    public VisView fwProfile;
    public VisView rwTop;
    public VisView rwProfile;
    public VisView rwBottom;
    public VisView rwRear;
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
    public VolumeCustomMeshControl volControlWingWake;
    public VolumeCustomMeshControl volControlRearWing;
    public VolumeCustomMeshControl volControlFrontWing;
    public VolumeCustomMeshControl volControlUnderbody;
    public MeshOperationPart subtractPart;
    public SimpleBlockPart domain;
    // Boundaries
    private Collection<Boundary> dualRadBounds;
    private String crossSectionName;
    private String planeSectionName;
    private String scene3DName;
    private String wallYSceneName;
    private String velVectorName;
    private String pressureScalar2DName;
    private String pressureScalar3DName;
    private VisView fwRear;
    private Collection<VisView> fwViews;
    private Collection<VisView> rwViews;
    private Collection<VisView> carViews;
    private Collection<VisView> utViews;
    private Collection<Boundary> domainRadBounds;
    // Auto mesh

    // Constructor
    public simComponents(Simulation inputSim) {

        long startTime = System.currentTimeMillis();

        // Surface wrapper names
        surfaceWrap = "Surface wrapper";

        // Initialize simulation
        activeSim = inputSim;

        // Units
        frontTyreRadius = 0.228599;
        rearTyreRadius = 0.228599;
        radResBig = 10000;
        wheelBase = 61;
        noUnit = activeSim.getUnitsManager().getObject("");
        inches = activeSim.getUnitsManager().getObject("in");
        degs = activeSim.getUnitsManager().getObject("deg");
        meters = activeSim.getUnitsManager().getObject("m");

        // Initialize surface wrappers
        surfaceWrapOperation = ((SurfaceWrapperAutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(surfaceWrap));
        aeroSurfaceWrapper = (SurfaceCustomMeshControl) surfaceWrapOperation.getCustomMeshControls().getObject("Aero Control");


        // Part management. Define naming convention here so STAR knows what's what
        allParts = activeSim.getGeometryPartManager().getParts();
        aeroPrefixes = new ArrayList<>();
        nonAeroPrefixes = new ArrayList<>();
        wheelNames = new ArrayList<>();
        radiatorName = "CFD_RADIATOR";
        dualRadiatorName = "CFD_DUAL_RADIATOR";
        liftGeneratorPrefixes = new ArrayList<>();
        aeroPrefixes.addAll(Arrays.asList("RW", "FW", "UT", "EC", "NS", "MOUNT", "SW", "FC"));
        nonAeroPrefixes.addAll(Collections.singletonList("CFD"));
        wheelNames.addAll(Arrays.asList("Front Left", "Front Right", "Rear Left", "Rear Right"));
        liftGeneratorPrefixes.addAll(Arrays.asList("RW", "FW", "UT", "SW", "FC"));

        // This is probably a terrible way to iterate, but there aren't that many parts. It's fine for now and looks
        // prettier than the other alternative that I could think of at the time. I couldn't find a way to check if
        // a given string startsWith any of the string in the prefix collection without iterating through the collection
        // and running startsWith with each element of the collection.

        aeroParts = new ArrayList<>();
        nonAeroParts = new ArrayList<>();
        wheels = new ArrayList<>();
        radiator = new ArrayList<>();
        dualRadiator = new ArrayList<>();
        liftGenerators = new ArrayList<>();
        populateAllParts();
        dualRadFlag = dualRadiator.size() != 0;

        // Set up regions
        domainRegionName = "Subtract";
        radiatorRegionName = "Radiator";
        dualRadRegionName = "Radiator 2";
        setupRadRegion();


        // Set up boundaries
        domainBounds = Objects.requireNonNull(domainRegion).getBoundaryManager().getBoundaries();
        dualRadBounds = new ArrayList<>();
        radBounds = Objects.requireNonNull(radiatorRegion).getBoundaryManager().getBoundaries();
        if (dualRadFlag)
            dualRadBounds = Objects.requireNonNull(dualRadiatorRegion).getBoundaryManager().getBoundaries();
        freestreamPrefix = "Freestream";

        // Takes all boundaries, filters them into freestream, parts, and wheels.
        freestreamBounds = new ArrayList<>();
        wheelBounds = new ArrayList<>();
        partBounds = new ArrayList<>();
        liftGeneratorBounds = new ArrayList<>();
        partSpecBounds = new HashMap<>();
        domainRadBounds = new ArrayList<>();

        boundarySet();

        // Set up coordinate systems

        rollAxisName = "Roll axis";
        radiatorAxisName = "Radiator Cartesian";
        frontWheelAxisName = "Front Wheel Cylindrical";
        rearWheelAxisName = "Rear Wheel Cylindrical";
        dualRadiatorAxisName = "Dual Radiator Cartesian";
        activeSim.getCoordinateSystemManager().getLabCoordinateSystem();

        setupCoordinates();


        // Set up the range of values to scan with the cross section scene. Also used for some physics
        profileLimits = new double[]{0, 35};
        topBottomLimits = new double[]{0, 60};
        aftForeLimits = new double[]{-70, 50};
        foreAftDirection = new double[]{1, 0, 0};
        profileDirection = new double[]{0, -1, 0};
        topBottomDirection = new double[]{0, 0, 1};
        utTopBottom = new double[]{0, 10};
        pressRange = new double[]{-2.5, 1.5};
        velRange = new double[]{0.0, 2};
        wallYRange = new double[]{5, 30};
        wallYRangeNearWall = new double[]{0.0, 5};


        // Set up scenes, representations, and views.

        sceneSetup();


        // Set up subtract
        subtractName = "Subtract";
        subtract = (SubtractPartsOperation) activeSim.get(MeshOperationManager.class).getObject(subtractName);
        mesherSetup();


        // Set up reports
        reports = activeSim.getReportManager().getObjects();
        maxVelName = "Maximum Velocity";
        pitchRepName = "Pitch Moment Coefficient";
        massFlowRepName = "Radiator Mass Flow";
        pointName = "Point";
        point = (PointPart) activeSim.getPartManager().getObject(pointName);
        repTableName = "Reports table";
        repTable = (XyzInternalTable) activeSim.getTableManager().getTable(repTableName);

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

        //Set physics objects
        physicsSet();

        //Blow up if it's the wrong version
        checkVersion();

        //No autosave
        activeSim.getSimulationIterator().getAutoSave().getStarUpdate().setEnabled(false);
        activeSim.getSimulationIterator().getAutoSave().setAutoSaveMesh(false);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        activeSim.println("Time taken to generate simComponents : " + totalTime + " ms");


    }

    private void boundarySet() {
        for (Boundary bound : domainBounds) {
            int partFlag = 0;                                      // Helps with if-logic to look for parts
            String boundName = bound.getPresentationName();

            if (boundName.contains(freestreamPrefix)) {
                freestreamBounds.add(bound);
                partFlag = 1;
            } else {
                for (String wheelName : wheelNames) {
                    if (boundName.contains(wheelName)) {
                        wheelBounds.add(bound);
                        partFlag = 1;
                    }
                }
            }

            if (partFlag == 0) {
                partBounds.add(bound);

                for (String prefix : liftGeneratorPrefixes) {
                    if (boundName.contains(prefix)) {
                        liftGeneratorBounds.add(bound);
                        partFlag = 1;
                    }
                }
            }

            if (partFlag == 0) {
                if (boundName.contains("RADIATOR")) {
                    domainRadBounds.add(bound);
                }
            }
        }

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

            for (String prefix : aeroPrefixes) {
                if (boundName.contains(prefix) && !boundName.toLowerCase().contains("suspension"))      // Janky code so CFD_SUSPENSION doesn't trigger the NS prefix.
                // I can't add an _ to the prefix because there are other
                // dependencies to consider
                {
                    Collection<Boundary> temp = new ArrayList<>();

                    if (partSpecBounds.containsKey(prefix))
                        temp = partSpecBounds.get(prefix);

                    temp.add(bound);
                    partSpecBounds.put(prefix, temp);
                }
            }
        }

        for (Boundary bound : radBounds) {
            String boundName = bound.getPresentationName();

            if (boundName.contains("Inlet"))
                radInlet = bound;
            if (boundName.contains("Outlet"))
                radOutlet = bound;
        }

        for (Boundary bound : dualRadBounds) {

            String boundName = bound.getPresentationName();

            if (boundName.contains("Inlet"))
                dualRadInlet = bound;
            if (boundName.contains("Outlet"))
                dualRadOutlet = bound;
        }

        for (Boundary fsBound : freestreamBounds)
        {
            String boundName = fsBound.getPresentationName();
            if (boundName.contains("Left"))
                leftPlane = fsBound;
            else if (boundName.contains("Symmetry"))
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

        // Flags
        freestreamVal = valEnv("freestream");
        wtFlag = boolEnv("windTunnel");
        freestreamParameterName = "Freestream";
        freestreamParameter = (ScalarGlobalParameter) activeSim.get(GlobalParameterManager.class).getObject(freestreamParameterName);
        freestreamParameter.getQuantity().setValue(freestreamVal);

        //Stopping criteria
        maxSteps = (int) valEnv("maxSteps");
        maxStepStop = (MonitorIterationStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Iterations");
        maxVel = (MonitorIterationStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Velocity Monitor Criterion");
        maxVelocity = (MaxReport) activeSim.getReportManager().getReport(maxVelName);
        abortFile = (AbortFileStoppingCriterion) activeSim.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Stop File");
        monitorWaypoint = activeSim.getUpdateEventManager().getUpdateEvent("Monitor Waypoint");

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
        // Define blocks
        corner1 = "Corner 1";
        corner2 = "Corner 2";

        halfFreestreamSize = new HashMap<>();
        halfFreestreamSize.put(corner1, new double[]{-16, 0, 0.009});
        halfFreestreamSize.put(corner2, new double[]{32, -6, 6});

        freestreamSize = new HashMap<>();
        freestreamSize.put(corner1, new double[]{-16, 6, 0.009});
        freestreamSize.put(corner2, new double[]{32, -6, 6});

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
        } catch (Exception e) {
            activeSim.println(this.getClass().getName() + " - Mesh initializer error");
        }
    }

    public void clearMesh() {
        activeSim.getMeshPipelineController().clearGeneratedMeshes();
    }

    private void sceneSetup() {
        try {
            crossSectionName = "Plane Section";
            crossSection = (PlaneSection) activeSim.getPartManager().getObject(crossSectionName);
            planeSectionName = "Plane section scenes";
            planeSectionScene = activeSim.getSceneManager().getScene(planeSectionName);
            planeSectionScene.setAdvancedRenderingEnabled(false);
            scene3DName = "3D scenes";
            scene3D = activeSim.getSceneManager().getScene(scene3DName);
            scene3D.setAdvancedRenderingEnabled(false);
            separator = File.separator;
            dir = activeSim.getSessionDir();
            simName = activeSim.getPresentationName();
            finiteVol = (FvRepresentation) activeSim.getRepresentationManager().getObject("Volume Mesh");
            wallYSceneName = "Y+";
            wallY = (ScalarDisplayer) scene3D.getDisplayerManager().getDisplayer(wallYSceneName);
            velVectorName = "Velocity vector";
            velVector2D = (VectorDisplayer) planeSectionScene.getDisplayerManager().getDisplayer(velVectorName);
            pressureScalar2DName = "Pressure";
            pressureScalar3DName = "Pressure";
            pressure2D = (ScalarDisplayer) planeSectionScene.getDisplayerManager().getDisplayer(pressureScalar2DName);
            pressure3D = (ScalarDisplayer) scene3D.getDisplayerManager().getDisplayer(pressureScalar3DName);

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
            activeSim.println("simComponents.simComponents.java - View lookup failed");
        }
    }

    public static double[] vectorScale(double scalar, double[] vect) {
        vect = vect.clone();
        for (int i = 0; i < vect.length; i++)
            vect[i] *= scalar;

        return vect;
    }

    public static double valEnv(String env) {
        if (System.getenv(env) != null && !System.getenv(env).isEmpty())
            return Double.parseDouble(System.getenv(env));
        else if (env.equals("freestream"))
            return 15;
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
            radiatorCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(radiatorAxisName);
            frontWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(frontWheelAxisName);
            rearWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(rearWheelAxisName);
            if (dualRadFlag)
                dualRadCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().
                        getCoordinateSystem(dualRadiatorAxisName);

        } catch (Exception e) {
            activeSim.println("simComponents.simComponents.java - Coordinate system lookup failed");
        }
        try {
            rollAxis = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(rollAxisName);
        } catch (Exception e) {
            activeSim.println(this.getClass().getName() + " rollAxis not found");
            //createRollAxis();
        }
    }

    private void setupRadRegion() {
        try {
            domainRegion = assignRegion(domainRegionName);
            radiatorRegion = assignRegion(radiatorRegionName);
            massFlowInterfaceNameInlet = "Inlet interface";
            massFlowInterfaceNameOutlet = "Outlet interface";
            if (dualRadFlag) {
                dualRadiatorRegion = assignRegion(dualRadRegionName);
                dualMassFlowInterfaceNameInlet = "Dual inlet interface";
                dualMassFlowInterfaceNameOutlet = "Dual outlet interface";
            }
        } catch (Exception e) {
            activeSim.println("simComponents.simComponents.java - Couldn't find/create domain or radiator region");
        }
    }

    private void populateAllParts() {
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

        domainRegion = assignRegion(domainRegionName);
        radiatorRegion = assignRegion(radiatorName);
        radiatorRegion.setPresentationName(radiatorRegionName);
        if (dualRadFlag) {
            dualRadiatorRegion = assignRegion(dualRadiatorName);
            dualRadiatorRegion.setPresentationName(dualRadRegionName);
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
            activeSim.saveState(activeSim.getSessionDir() + File.separator + newName);
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
            throw new IllegalStateException("You're using the wrong macro + sim combination. Don't be surprised if something strange happens if you try forcing this macro to work");
        }
    }

}
