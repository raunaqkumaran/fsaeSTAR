package simComponents;/*
    Class to instantiate frequently used simulation components. Should help keep the other macros much cleaner. Maybe
    The idea isn't for this class to *do* anything. There shouldn't be any data processing or sim manipulation done
    through this class whatsoever. This is to only act as a 'library' for frequently used objects to make the other
    macros cleaner and easier to maintain.


    Raunaq Kumaran, January 2019
 */

import star.base.neo.DoubleVector;
import star.base.neo.NeoObjectVector;
import star.base.report.Report;
import star.cadmodeler.SolidModelPart;
import star.common.*;
import star.meshing.*;
import star.surfacewrapper.SurfaceWrapperAutoMeshOperation;
import star.vis.*;

import java.io.File;
import java.util.*;


public class simComponents  {

    //Declarations. There may be 'repeated' parts. Some of this is because of typecasting that I don't understnad
    // but STAR requires

    // The Simulation
    public Simulation activeSim;

    // Surface wrapper
    public String aeroWrapName;
    public String nonAeroWrapName;
    public SurfaceWrapperAutoMeshOperation aeroWrap;
    public SurfaceWrapperAutoMeshOperation nonAeroWrap;

    // Mesh operations
    public AutoMeshOperation autoMesher;

    // Parts
    public Collection<GeometryPart> allParts;
    public Collection<GeometryPart> aeroParts;
    public Collection<GeometryPart> nonAeroParts;
    public Collection<GeometryPart> wheels;
    public Collection<GeometryPart> radiator;
    public Collection<GeometryPart> liftGenerators;
    public Collection<String> aeroPrefixes;
    public Collection<String> liftGeneratorPrefixes;
    public Collection<String> nonAeroPrefixes;
    public Collection<String> wheelNames;
    public String radiatorName;
    public SolidModelPart radPart;
    public String volumetricCarName;
    public String volumetricWakeName;
    public GeometryPart volumetricWake;
    public GeometryPart volumetricCar;

    // Boundaries
    public Collection<Boundary> domainBounds;
    public Collection<Boundary> radBounds;
    public Collection<Boundary> freestreamBounds;
    public Collection<Boundary> partBounds;
    public Collection<Boundary> wheelBounds;
    public Collection<Boundary> liftGeneratorBounds;
    public String freestreamPrefix;
    public Boundary radInlet;            // There are two sets of these corresponding to the two regions. Need these for interfacing
    public Boundary radOutlet;
    public Boundary domainRadInlet;
    public Boundary domainRadOutlet;
    public Map<String, Collection<Boundary>> partSpecBounds;

    // Regions
    public Region radiatorRegion;
    public Region domainRegion;
    public String radiatorRegionName;
    public String domainRegionName;
    public BoundaryInterface massFlowInterfaceInlet;
    public BoundaryInterface massFlowInterfaceOutlet;
    public String massFlowInterfaceNameInlet;
    public String massFlowInterfaceNameOutlet;

    //Coordinate systems
    public String rollAxisName;
    public String radiatorAxisName;
    public String frontWheelAxisName;
    public String rearWheelAxisName;

    public CylindricalCoordinateSystem frontWheelCoord;
    public CylindricalCoordinateSystem rearWheelCoord;
    public LabCoordinateSystem labCoord;
    public CartesianCoordinateSystem radiatorCoord;
    public CartesianCoordinateSystem rollAxis;

    //Scenes and displayers
    public PlaneSection crossSection;
    public String crossSectionName;
    public Scene planeSectionScene;
    public String planeSectionName;
    public Scene scene3D;
    public String scene3DName;
    public String separator;
    public FvRepresentation finiteVol;
    public ScalarDisplayer wallY;
    public String wallYSceneName;
    public VectorDisplayer velVector2D;
    public String velVectorName;
    public ScalarDisplayer pressure2D;
    public String pressureScalar2DName;
    public ScalarDisplayer pressure3D;
    public String pressureScalar3DName;
    public String dir;
    public String simName;
    public double [] profileLimits;
    public double [] aftForeLimits;
    public double [] topBottomLimits;
    public double [] foreAftDirection;
    public double [] profileDirection;
    public double [] topBottomDirection;
    public double [] utTopBottom;
    public double [] wingProfileLimits;
    public double [] carAftForeLimits;
    public double [] pressRange;
    public double [] velRange;
    public double [] wallYRange;
    public double [] wallYRangeNearWall;

    //Views
    public VisView carRear;
    public String carRearName;
    public VisView carStd;
    public String carStdName;
    public VisView utRear;
    public String utRearName;
    public VisView utBottom;
    public String utBottomName;
    public VisView utProfile;
    public String utProfileName;
    public VisView fwRear;
    public String fwRearName;
    public VisView fwBottom;
    public String fwBottomName;
    public VisView fwTop;
    public String fwTopName;
    public VisView fwProfile;
    public String fwProfileName;
    public VisView rwTop;
    public String rwTopName;
    public VisView rwProfile;
    public String rwProfileName;
    public VisView rwBottom;
    public String rwBottomName;
    public VisView rwRear;
    public String rwRearName;
    public Collection<VisView> fwViews;
    public Collection<VisView> rwViews;
    public Collection<VisView> carViews;
    public Collection<VisView> utViews;

    // Auto mesh

    public String autoMeshName;
    public AutoMeshOperation autoMesh;
    public String wingControlName;
    public SurfaceCustomMeshControl wingControl;
    public String groundControlName;
    public SurfaceCustomMeshControl groundControl;
    public String freestreamControlName;
    public SurfaceCustomMeshControl freestreamControl;
    public String volControlCarName;
    public VolumeCustomMeshControl volControlCar;
    public String volControlWakeName;
    public VolumeCustomMeshControl volControlWake;



    // Subtract

    public SubtractPartsOperation subtract;
    public String subtractName;
    public MeshOperationPart subtractPart;

    // Reports

    // I'm assuming reports are going to be PARTPREFIX LIFT/DRAG formatted. There shouldn't be any reason to do
    // things differently. Additional reports can be initialized here and added to the collection. I'm also assuming
    // the simulation already has the reports existing in it.

    public Collection<Report> reports;
    public String massFlowRepName;
    public String pitchRepName;
    public String pointName;
    public PointPart point;
    public XyzInternalTable repTable;
    public String repTableName;
    public Collection<StarPlot> plots;

    // Miscellaneous

    public double frontTyreRadius;
    public double rearTyreRadius;
    public double radResBig;                    // These define the resistance tensors for the rad region
    public double radResSmall;
    public double wheelBase;
    public Units noUnit;
    public Units inches;
    public Units degs;
    public Units meters;
    public Map<String, double[]> freestreamSize;
    public Map<String, double[]> wakeSize;
    public Map<String, double[]> carSize;
    public Map<String, double[]> halfFreestreamSize;
    public Map<String, double[]> halfWakeSize;
    public Map<String, double[]> halfCarSize;
    public String corner1;
    public String corner2;
    public String domainName;
    public SimpleBlockPart domain;
    public SimpleBlockPart wake;
    public SimpleBlockPart car;
    public boolean createRollAxisFlag;                  // Flip this if you need to make a rollaxis
    public boolean fullCarFlag;

    // Physics
    public PhysicsContinuum mainPhysics;
    public String mainPhysicsName;
    public double freestreamVal;

    // Constructor

    public simComponents(Simulation inputSim)
    {
        // Surface wrapper names
        aeroWrapName = "Aero wrap (Non-aero wrap, wings, mounts and bodywork)";
        nonAeroWrapName = "Non-aero wrap (Car)";

        // Initialize simulation
        activeSim = inputSim;

        // Initialize surface wrappers
        try {
            aeroWrap = ((SurfaceWrapperAutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(aeroWrapName));
            nonAeroWrap = ((SurfaceWrapperAutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(nonAeroWrapName));
        }
        catch (Exception e)
        {
            activeSim.println("simComponents.simComponents.java - Couldn't find aero or non-aero surface wrappers");
        }

        // Initialize mesher
        String mesherName = "Automated Mesh";
        try {
            autoMesher = (AutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(mesherName);
        }
        catch (Exception e)
        {
            activeSim.println("simComponents.simComponents.java - Couldn't find automated mesher");
        }

        // Part management. Define naming convention here so STAR knows what's what
        allParts = activeSim.getGeometryPartManager().getParts();
        aeroPrefixes = new ArrayList<>();
        nonAeroPrefixes = new ArrayList<>();
        wheelNames = new ArrayList<>();
        radiatorName = "CFD_RADIATOR";
        liftGeneratorPrefixes = new ArrayList<>();
        aeroPrefixes.addAll(Arrays.asList("RW", "FW", "UT", "EC", "NS", "MOUNT", "SW", "FC"));
        nonAeroPrefixes.addAll(Arrays.asList("CFD"));
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
        liftGenerators = new ArrayList<>();

        for (GeometryPart prt : allParts)
        {
            String prtName = prt.getPresentationName();
            for (String prefix : aeroPrefixes)
            {
                if (prtName.startsWith(prefix))
                {
                    aeroParts.add(prt);
                }
            }
            for (String prefix : nonAeroPrefixes)
            {
                if (prtName.startsWith(prefix))
                    nonAeroParts.add(prt);
            }
            for (String prefix : wheelNames)
            {
                if (prtName.startsWith(prefix))
                    wheels.add(prt);
            }
            for (String prefix : liftGeneratorPrefixes)
            {
                if (prtName.startsWith(prefix))
                {
                    liftGenerators.add(prt);
                }
            }
            if (prtName.startsWith(radiatorName))
                radiator.add(prt);

        }

        // Set up regions

        domainRegionName = "Subtract";
        radiatorRegionName = "Radiator";

        try{
            domainRegion = activeSim.getRegionManager().getRegion(domainRegionName);
            radiatorRegion = activeSim.getRegionManager().getRegion(radiatorRegionName);
            massFlowInterfaceNameInlet = "Inlet interface";
            massFlowInterfaceNameOutlet = "Outlet interface";
        }
        catch (Exception e)
        {
            activeSim.println("simComponents.simComponents.java - Couldn't find domain or radiator region");
        }


        // Set up boundaries

        domainBounds = domainRegion.getBoundaryManager().getBoundaries();
        radBounds = radiatorRegion.getBoundaryManager().getBoundaries();
        freestreamPrefix = "Freestream";

        // Takes all boundaries, filters them into freestream, parts, and wheels.

        freestreamBounds = new ArrayList<>();
        wheelBounds = new ArrayList<>();
        partBounds = new ArrayList<>();
        liftGeneratorBounds = new ArrayList<>();
        partSpecBounds = new HashMap<>();

        for (Boundary bound : domainBounds)
        {
            int partFlag = 0;                                      // Helps with if-logic to look for parts
            String boundName = bound.getPresentationName();

            if (boundName.contains(freestreamPrefix))
            {
                freestreamBounds.add(bound);
                partFlag = 1;
            }
            else {
                for (String wheelName : wheelNames) {
                    if (boundName.contains(wheelName)) {
                        wheelBounds.add(bound);
                        partFlag = 1;
                    }
                }
            }

            if (partFlag == 0)
            {
                partBounds.add(bound);

                for (String prefix : liftGeneratorPrefixes)
                {
                    if (boundName.contains(prefix))
                    {
                        liftGeneratorBounds.add(bound);
                    }
                }
            }
        }

        for (Boundary bound : partBounds)
        {
            String boundName = bound.getPresentationName();
            if (boundName.contains("Inlet"))
                domainRadInlet = bound;
            else if (boundName.contains("Outlet"))
                domainRadOutlet = bound;

            for (String prefix : aeroPrefixes)
            {
                if (boundName.contains(prefix) && !boundName.toLowerCase().contains("suspension"))      // Janky code so CFD_SUSPENSION doesn't trigger the NS prefix.
                                                                                                        // I can't add an _ to the prefix because there are other dependencies to consider
                {
                    Collection<Boundary> temp = new ArrayList<>();

                    if (partSpecBounds.containsKey(prefix))
                        temp = partSpecBounds.get(prefix);

                    temp.add(bound);
                    partSpecBounds.put(prefix, temp);
                }
            }
        }

        for (Boundary bound : radBounds)
        {
            String boundName = bound.getPresentationName();
            if (boundName.contains("Inlet"))
                radInlet = bound;
            if (boundName.contains("Outlet"))
                radOutlet = bound;
        }

        // Set up coordinate systems

        {
            rollAxisName = "Roll axis";
            radiatorAxisName = "Radiator Cartesian";
            frontWheelAxisName = "Front Wheel Cylindrical";
            rearWheelAxisName = "Rear Wheel Cylindrical";
            labCoord = activeSim.getCoordinateSystemManager().getLabCoordinateSystem();

            try
            {
                radiatorCoord = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(radiatorAxisName);
                frontWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(frontWheelAxisName);
                rearWheelCoord = (CylindricalCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(rearWheelAxisName);

            }
            catch (Exception e)
            {
                activeSim.println("simComponents.simComponents.java - Coordinate system lookup failed");
            }
            try {
                rollAxis = (CartesianCoordinateSystem) activeSim.getCoordinateSystemManager().getCoordinateSystem(rollAxisName);
                createRollAxisFlag = false;
            }
            catch (Exception e)
            {
                activeSim.println(this.getClass().getName() + " rollAxis not found");
                createRollAxisFlag = true;
            }
        }

        // Set up the range of values to scan with the cross section scene. Also used for some physics

        profileLimits = new double[] {0, 35};
        wingProfileLimits = new double[] {};
        topBottomLimits = new double[] {0, 60};
        aftForeLimits = new double[] {-70, 50};
        carAftForeLimits = new double[] {-70, 45};
        foreAftDirection = new double[] {0, 0, 1};
        profileDirection = new double[] {0, 1, 0};
        topBottomDirection = new double[] {1, 0, 0};
        utTopBottom = new double[] {0, 10};
        pressRange = new double[] {-500.0, 200.0};
        velRange = new double[] {0.0, 30.0};
        wallYRange = new double[] {30.0, 100};
        wallYRangeNearWall = new double[] {0.0, 30.0};

        // Set up scenes, representations, and views.

        {
            try {
                crossSectionName = "Plane Section";
                crossSection = (PlaneSection) activeSim.getPartManager().getObject(crossSectionName);
                planeSectionName = "Plane section scenes";
                planeSectionScene = activeSim.getSceneManager().getScene(planeSectionName);
                scene3DName = "3D scenes";
                scene3D = activeSim.getSceneManager().getScene(scene3DName);
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

            }
            catch (Exception e){
                activeSim.println(this.getClass().getName() + " - Scene or displayer lookup failed, or volume mesh not found");
            }
            try
            {
                fwViews = new ArrayList<>();
                rwViews = new ArrayList<>();
                carViews = new ArrayList<>();
                utViews = new ArrayList<>();

                carStdName = "Car standard";
                carStd = activeSim.getViewManager().getObject(carStdName);
                carRearName = "Car Rear";
                carRear = activeSim.getViewManager().getObject(carRearName);
                carViews.addAll(Arrays.asList(carRear, carStd));

                utBottomName = "UT Bottom";
                utBottom = activeSim.getViewManager().getObject(utBottomName);
                utRearName = "UT Rear";
                utRear = activeSim.getViewManager().getObject(utRearName);
                utProfileName = "UT Profile";
                utProfile = activeSim.getViewManager().getObject(utProfileName);
                utViews.addAll(Arrays.asList(utBottom, utRear, utProfile));

                fwBottomName = "FW Bottom";
                fwBottom = activeSim.getViewManager().getObject(fwBottomName);
                fwProfileName = "FW Profile";
                fwProfile = activeSim.getViewManager().getObject(fwProfileName);
                fwRearName = "FW Rear";
                fwRear = activeSim.getViewManager().getObject(fwRearName);
                fwTopName = "FW Top";
                fwTop = activeSim.getViewManager().getObject(fwTopName);
                fwViews.addAll(Arrays.asList(fwBottom, fwProfile, fwRear, fwTop));

                rwProfileName = "RW Profile";
                rwBottomName = "RW Bottom";
                rwTopName = "RW Top";
                rwRearName = "RW Rear";
                rwProfile = activeSim.getViewManager().getObject(rwProfileName);
                rwBottom = activeSim.getViewManager().getObject(rwBottomName);
                rwTop = activeSim.getViewManager().getObject(rwTopName);
                rwRear = activeSim.getViewManager().getObject(rwRearName);
                rwViews.addAll(Arrays.asList(rwProfile, rwBottom, rwTop, rwRear));

            }
            catch (Exception e)
            {
                activeSim.println("simComponents.simComponents.java - View lookup failed");
            }
        }

        // Set up subtract

        subtractName = "Subtract";
        subtract = (SubtractPartsOperation) activeSim.get(MeshOperationManager.class).getObject(subtractName);

        // Set up mesher

        try {

            // Get volumetric blocks
            volumetricCarName = "Volumetric Control Car";
            volumetricWakeName = "Volumetric Control Wake";
            volumetricCar = activeSim.get(SimulationPartManager.class).getPart(volumetricCarName);
            volumetricWake = activeSim.get(SimulationPartManager.class).getPart(volumetricWakeName);

            // Get mesher
            autoMeshName = "Automated Mesh";
            autoMesh = (AutoMeshOperation) activeSim.get(MeshOperationManager.class).getObject(autoMeshName);

            // Get custom controls
            wingControlName = "Surface Control Wings";
            groundControlName = "Surface Control Ground";
            freestreamControlName = "Surface Control Freestream";
            volControlCarName = "Volumetric Control Car";
            volControlWakeName = "Volumetric Control Wake";

            wingControl = (SurfaceCustomMeshControl) autoMesh.getCustomMeshControls().getObject(wingControlName);
            groundControl = (SurfaceCustomMeshControl) autoMesh.getCustomMeshControls().getObject(groundControlName);
            freestreamControl = (SurfaceCustomMeshControl) autoMesh.getCustomMeshControls().getObject(freestreamControlName);
            volControlWake = (VolumeCustomMeshControl) autoMesh.getCustomMeshControls().getObject(volControlWakeName);
            volControlCar = (VolumeCustomMeshControl) autoMesh.getCustomMeshControls().getObject(volControlCarName);

        }

        catch (Exception e)
        {
            activeSim.println(this.getClass().getName() + " - Mesh initializer error");
        }



        // Set up reports

        reports = activeSim.getReportManager().getObjects();
        pitchRepName = "Pitch Moment Coefficient";
        massFlowRepName = "Radiator Mass Flow";
        pointName = "Point";
        point = (PointPart) activeSim.getPartManager().getObject(pointName);
        repTableName = "Reports table";
        repTable = (XyzInternalTable) activeSim.getTableManager().getTable(repTableName);

        // Miscellaneous constructor things

        frontTyreRadius = 0.228599;
        rearTyreRadius = 0.228599;
        radResBig = 10000;
        radResSmall = 70;
        wheelBase = 61;
        noUnit = activeSim.getUnitsManager().getObject("");
        inches = activeSim.getUnitsManager().getObject("in");
        degs = activeSim.getUnitsManager().getObject("deg");
        meters = activeSim.getUnitsManager().getObject("m");
        radPart = (SolidModelPart) activeSim.get(SimulationPartManager.class).getObject(radiatorName);
        subtractPart = (MeshOperationPart) activeSim.get(SimulationPartManager.class).getPart(subtractName);

        // Plots

        plots = activeSim.getPlotManager().getPlots();

        // Define blocks
        corner1 = "Corner 1";
        corner2 = "Corner 2";
        
        halfFreestreamSize = new HashMap<>();
        halfFreestreamSize.put(corner1, new double[] {0, 0, -16.0});
        halfFreestreamSize.put(corner2, new double[] {6, 6, 32});
        
        freestreamSize = new HashMap<>();
        freestreamSize.put(corner1, new double[] {0, -6, -16});
        freestreamSize.put(corner2, new double[] {6, 6, 32});
        
        halfCarSize = new HashMap<>();
        halfCarSize.put(corner1, new double[] {-0.1, -0.1, -2});
        halfCarSize.put(corner2, new double[] {1.5, 0.75, 2});

        carSize = new HashMap<>();
        carSize.put(corner1, new double[] {-0.1, -0.75, -2});
        carSize.put(corner2, new double[] {1.5, 0.75, 2});

        halfWakeSize = new HashMap<>();
        halfWakeSize.put(corner1, new double[] {-0.1, -0.1, -3});
        halfWakeSize.put(corner2, new double[] {2, 1, 16});

        wakeSize = new HashMap<>();
        wakeSize.put(corner1, new double[] {-0.1, -1, -3});
        wakeSize.put(corner2, new double[] {2, 1, 16});

        domainName = "Freestream";
        domain = (SimpleBlockPart) activeSim.get(SimulationPartManager.class).getPart(domainName);
        wake = (SimpleBlockPart) activeSim.get(SimulationPartManager.class).getPart(volumetricWakeName);
        car = (SimpleBlockPart) activeSim.get(SimulationPartManager.class).getPart(volumetricCarName);

        if (createRollAxisFlag)
            createRollAxis();

        // Define physics block
        mainPhysicsName = "Physics 1";
        mainPhysics = (PhysicsContinuum) activeSim.getContinuumManager().getContinuum(mainPhysicsName);

        // Flags
        fullCarFlag = boolEnv("domainSet");
        freestreamVal = valEnv("freestream");
    }

    public void regionSwap()
    {
        // Removes old regions. Creates new ones. There's some variable casting going on which I don't understand
        // but STAR seems to require it.

        try {
            activeSim.getRegionManager().removeRegion(domainRegion);
            activeSim.getRegionManager().removeRegion(radiatorRegion);
        }
        catch (Exception e) {
            activeSim.println("Old regions not found");
        }

        try {
            radPart = (SolidModelPart) activeSim.get(SimulationPartManager.class).getObject(radiatorName);
            subtractPart = (MeshOperationPart) activeSim.get(SimulationPartManager.class).getPart(subtractName);
        }
        catch (Exception e)
        {
            activeSim.println("Can't create new regions");
        }

        activeSim.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {radPart, subtractPart}),
                "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null,
                "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);

        domainRegion = activeSim.getRegionManager().getRegion(subtractName);
        domainRegion.setPresentationName(domainRegionName);

        radiatorRegion = activeSim.getRegionManager().getRegion(radiatorName);
        radiatorRegion.setPresentationName(radiatorRegionName);

    }
    private void createRollAxis()
    {

        rollAxis = activeSim.getCoordinateSystemManager().
                getLabCoordinateSystem().getLocalCoordinateSystemManager().
                createLocalCoordinateSystem(CartesianCoordinateSystem.class, rollAxisName);
        rollAxis.setBasis0(new DoubleVector(new double[] {-0.07617, 0, 0.997094}));
        rollAxis.setBasis1(new DoubleVector(new double[] {0, 1, 0}));
        inches = activeSim.getUnitsManager().getObject("in");
        rollAxis.getOrigin().setCoordinate(inches, inches, inches, new DoubleVector(new double[] {8.41, 0, 0}));
        rollAxis.setPresentationName(rollAxisName);
    }

    public static double[] vectorScale(double scalar, double [] vect)
    {
        vect = vect.clone();
        for (int i = 0; i < vect.length; i++)
            vect[i] *= scalar;

        return vect;
    }

    public static double valEnv(String env)
    {
        if (System.getenv(env)!= null && !System.getenv(env).isEmpty())
            return Double.parseDouble(System.getenv(env));
        else if (env.equals("freestream"))
                return 15;
        else
            return 0;
    }

    public static boolean boolEnv(String env)
    {
        // Read the sys environment to figure out if you want a full car or a half car sim
        if (env.equals("domainSet") && System.getenv(env)!=null && System.getenv(env).toLowerCase().equals("full"))
            return true;
        if (System.getenv(env)!=null && System.getenv(env).toLowerCase().equals("true"))
            return true;
        else
            return false;

    }

    public void saveSim()
    {
        String newName = System.getenv("newName");

        if (newName != null)
            activeSim.saveState(dir + separator + newName);
        else
            activeSim.saveState(dir + separator + simName + ".sim");
    }

    public static void saveSim(Simulation sim)
    {
        String newName = System.getenv("newName");

        if (newName != null)
            sim.saveState(sim.getSessionDir() + File.separator + newName);
        else
            sim.saveState(sim.getSessionDir() + File.separator + sim.getPresentationName() + ".sim");
    }



}
