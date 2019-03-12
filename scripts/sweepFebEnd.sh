cd $PBS_O_WORKDIR;

PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
MACRO="macroController.java";
PROCESSES="20";
CP="/scratch/scholar/rkumaran/Simulation_space"

FILENAME="-0.25_-0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25_1,00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.25.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_-0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75_-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-0.75.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_-0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25_1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.25.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.50.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME


FILENAME="0.75_-0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_-1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_0.5.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75_1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="0.75.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME

FILENAME="1.00.sim";
"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $CP/$FILENAME


qdel $PBS_JOBID;
