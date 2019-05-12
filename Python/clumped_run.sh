set CLUSTER=long
set PROCS=40
set CLUMP=true
set WALLTIME=72:00:00
set POD=iOJDZCUKWsPIjut3CFrKXQ
set SIMPATH=/scratch/scholar/rkumaran/Simulation_space
set STARLOC=/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+
set CP=/home/rkumaran/fsaeSTAR/src
set MACRO=macroController.java
set frh=0
set rrh=0
set rws=0
set uts=0
set cs=0
set ps=0
set sceneMultiplier=1
set yaw=0
set roll=0
set domainSet=half
set freestream=15
set maxSteps=5000
set preprocess=false
set process=true
set postprocess=false
set windTunnel=false
set geometryManip=false
set FILENAME=BASESIM_25-1_WT.sim
set newName=\Processed\BASESIM_25-1_WT.sim
"%STARLOC%" "%SIMPATH%\%FILENAME%" -batch "%CP%\%MACRO%" -np %PROCS% -power -podkey "%PODKEY% -classpath "%CP%"

set FILENAME=BASESIM_25-1_WT_Debug.sim
set newName=\Processed\BASESIM_25-1_WT_Debug.sim
"%STARLOC%" "%SIMPATH%\%FILENAME%" -batch "%CP%\%MACRO%" -np %PROCS% -power -podkey "%PODKEY% -classpath "%CP%"

set FILENAME=intake_cfd_2_optionA.sim
set newName=\Processed\intake_cfd_2_optionA.sim
"%STARLOC%" "%SIMPATH%\%FILENAME%" -batch "%CP%\%MACRO%" -np %PROCS% -power -podkey "%PODKEY% -classpath "%CP%"

