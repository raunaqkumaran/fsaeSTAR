import star.base.neo.NeoObjectVector;
import star.common.*;
import star.flow.StaticPressureProfile;
import star.flow.VelocityMagnitudeProfile;

class intakeRegions extends StarMacro
{
    public void execute()
    {
        intakeComponents intakeSim = new intakeComponents(getActiveSimulation());
        intakeSim.activeSim.getRegionManager().removeRegion(intakeSim.intakeRegion);

        intakeSim.activeSim.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[]{intakeSim.intakePart}),
                "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null,
                "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);

        intakeSim = new intakeComponents(getActiveSimulation());

        for (Boundary bound : intakeSim.regionBounds)
        {
            if (bound.getPresentationName().contains(intakeSim.runner1))
            {
                if (intakeSim.parameterObj.runner1 != 0)
                {
                    bound.setBoundaryType(PressureBoundary.class);
                    bound.getValues().get(StaticPressureProfile.class).getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(intakeSim.parameterObj.runner1);
                }
            }

            if (bound.getPresentationName().contains(intakeSim.runner2))
            {
                if (intakeSim.parameterObj.runner2 != 0)
                {
                    bound.setBoundaryType(PressureBoundary.class);
                    bound.getValues().get(StaticPressureProfile.class).getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(intakeSim.parameterObj.runner2);
                }
            }

            if (bound.getPresentationName().contains(intakeSim.runner3))
            {
                if (intakeSim.parameterObj.runner3 != 0)
                {
                    bound.setBoundaryType(PressureBoundary.class);
                    bound.getValues().get(StaticPressureProfile.class).getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(intakeSim.parameterObj.runner3);
                }
            }

            if (bound.getPresentationName().contains(intakeSim.runner4))
            {
                if (intakeSim.parameterObj.runner4 != 0)
                {
                    bound.setBoundaryType(PressureBoundary.class);
                    bound.getValues().get(StaticPressureProfile.class).getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(intakeSim.parameterObj.runner4);
                }
            }

            if (bound.getPresentationName().contains(intakeSim.freeOutlet))
            {
                bound.setBoundaryType(PressureBoundary.class);
            }

            if (bound.getPresentationName().contains(intakeSim.inlet))
            {
                bound.setBoundaryType(InletBoundary.class);
                bound.getValues().get(VelocityMagnitudeProfile.class).getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(intakeSim.parameterObj.freestream);
            }
        }
    }
}
