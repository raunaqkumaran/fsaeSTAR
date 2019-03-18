#!/bin/bash

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="4";
export CP="/mnt/c/Users/rauna/Documents/WIP macros/src"
export SIMPATH="/mnt/c/Users/rauna/Documents/CFD"
export STARLOC="/mnt/c/Program Files/CD-Adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+"

declare -a arr
i=0

for FILENAME in $SIMPATH/*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]=$FILENAME
  echo ${arr[$i]}
  echo $i
  ((i++))
done

for ((j=0; j<$i; j++))
do
  FILENAME=${arr[$j]}
  echo $STARLOC
  echo $MACRO
  echo $CP
  echo $FILENAME
  "$STARLOC" -np $PROCESSES -power -podkey $PODKEY -batch $MACRO -classpath $CP "$FILENAME"
done
