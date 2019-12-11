import star.common.StarMacro;

class intakeRunSim extends StarMacro {

    public void execute()
    {
        intakeComponents intake = new intakeComponents(getActiveSimulation());
        intake.activeSim.getSimulationIterator().run();
    }

}
