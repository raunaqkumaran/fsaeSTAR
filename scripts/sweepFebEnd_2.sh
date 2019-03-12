cd $PBS_O_WORKDIR;

PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
MACRO="macroController.java";
PROCESSES="20";
CP="/scratch/scholar/rkumaran/Simulation_space"
STARLOC="/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+"

FILENAME="0.25_-0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_-0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_1.00.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_-0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_-0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_-0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_-0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_-0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_0.25.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_0.75.sim";
$STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

qdel $PBS_JOBID;
