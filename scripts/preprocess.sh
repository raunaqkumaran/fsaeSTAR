cd $PBS_O_WORKDIR;

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="20";
export CP="/scratch/scholar/rkumaran/Simulation_space"
export STARLOC="/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+"
export SIMLOC="/scratch/scholar/rkumaran/Simulation_space/"
export process="false"
export preprocess="true"

declare -a arr
i=0

for FILENAME in $SIMLOC*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]="$FILENAME"
  i=$((i+1))
done

for ((j=0; j < $i; j++)); do
  FILENAME=${arr[$j]}
  $STARLOC -machinefile $PBS_NODEFILE -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP $FILENAME
  mv $FILENAME Preprocess
done

qdel $PBS_JOBID;
