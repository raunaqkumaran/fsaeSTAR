cd $PBS_O_WORKDIR;
set -x

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="20";
export CP="/home/rkumaran/fsaeSTAR/src"
export STARLOC="/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+"
export SIMLOC="/scratch/scholar/rkumaran/Simulation_space/"
export process="false"
export preprocess="true"
export domainSet="half"
export maxSteps="700"

declare -a arr
i=0

for FILENAME in $SIMLOC*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]="$FILENAME"
  i=$((i+1))
done

for ((j=0; j < $i; j++)); do
  FILENAME=${arr[$j]}
  $STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $CP/$MACRO -classpath $CP $FILENAME
  mv $FILENAME /scratch/scholar/rkumaran/Simulation_space/Preprocess
done

qdel $PBS_JOBID;