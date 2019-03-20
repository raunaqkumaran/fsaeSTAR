#!/bin/bash
set -x
set -e


runCommand()
{
  "$STARLOC" "$SIMPATH"\\"$FILENAME" -batch "$CP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$CP" -licpath 1999@dock.ecn.purdue.edu
}

setENVWin()
{
  export $1="$2"
  cmd.exe "/C setx $1 """
  cmd.exe "/C setx $1 "$2""
}

setENV()
{
  export $1="$2"
}

setENVWin PODKEY "iOJDZCUKWsPIjut3CFrKXQ"
setENVWin MACRO "macroController.java"
setENVWin PROCESSES "4"
setENVWin CP "C:\Users\rauna\Documents\WIP macros\src"
setENVWin SIMPATH "C:\Users\rauna\Documents\CFD\Yaw series"
setENVWin STARLOC "/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe"
setENVWin yaw 0
setENVWin roll 0


setENVWin domainSet full
setENVWin geometryManip true
setENVWin yaw 0.5
setENVWin FILENAME BASESIM_24-1.sim
newnameVal="$yaw"_"$FILENAME"
setENVWin newName "$newnameVal"

runCommand
mv "$FILENAME" "$SIMPATH"/Complete
