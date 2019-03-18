#!/bin/bash
set -x

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="4";
export CP="/mnt/c/Users/rauna/Documents/WIP macros/src";
export SIMPATH="/mnt/c/Users/rauna/Documents/CFD";
export STARLOC="/mnt/c/Program Files/CD-Adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe";

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
  "$STARLOC" "$FILENAME" -batch "$CP/$MACRO" -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power
done

read -p "Press enter to continue"
