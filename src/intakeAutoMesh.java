import star.common.StarMacro;

class intakeAutoMesh extends StarMacro {

    public void execute()
    {
        intakeComponents intake = new intakeComponents(getActiveSimulation());
        intake.autoMesh.getInputGeometryObjects().setObjects(intake.intakePart);
        intake.autoMesh.execute();
    }
}
