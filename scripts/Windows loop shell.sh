#!/bin/bash
set -x
set -e

runCommand()
{
  "$STARLOC" "$SIMPATH"\\"$FILENAME" -batch "$CP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$CP" -licpath 1999@dock.ecn.purdue.edu
}

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ"
export MACRO="macroController.java"
export PROCESSES="4"
export CP="C:\Users\rauna\Documents\WIP macros\src"
export SIMPATH="C:\Users\rauna\Documents\CFD\Yaw series"
export STARLOC="/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe"

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
  runCommand
done

read -p "Press enter to continue"
