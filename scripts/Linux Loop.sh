#!/bin/bash
set -x

cd $PBS_O_WORKDIR;

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="20";
export CP="/scratch/scholar/rkumaran/Simulation_space"
export SIMPATH="/scratch/scholar/rkumaran/Simulation_space"
export STARLOC="/home/rkumaran/Desktop/star/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+"

declare -a arr
i=0

for FILENAME in $SIMPATH/*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]=$FILENAME
  ((i++))
done

for ((j=0; j<$i; j++))
do
  FILENAME=${arr[$j]}
  "$STARLOC" "$FILENAME" -batch "$CP/$MACRO" -machilefile $PBS_NODEFILE -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power
done

qdel $PBS_JOBID;
