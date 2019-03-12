cd $PBS_O_WORKDIR;

PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
MACRO="base23_preprocessing_run.java";
PROCESSES="20";

FILENAME="BASESIM_24-2.sim";

"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY $FILENAME -batch $MACRO;

FILENAME="BASESIM_24-3.sim";

"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY $FILENAME -batch $MACRO;

FILENAME="BASESIM_24-4.sim";

"/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+" -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY $FILENAME -batch $MACRO;

qdel $PBS_JOBID;
