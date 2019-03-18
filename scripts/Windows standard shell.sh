#!/bin/bash
set -x
set -e


runCommand()
{
  "$STARLOC" "$SIMPATH"\\"$FILENAME" -batch "$CP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$CP" -licpath 1999@dock.ecn.purdue.edu
}

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="4";
export CP="C:\Users\rauna\Documents\WIP macros\src";
export SIMPATH="C:\Users\rauna\Documents\CFD\Yaw series";
export STARLOC="/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe";
export domainSet="full"
export geometryManip="true"


export FILENAME="BASESIM_24-1.sim"
export yaw="0.5"
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=1
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=1.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=2
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=2.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=3
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=3.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=4
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=4.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=5.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=6
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=6.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=7
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=7.5
export newName=""$yaw"_"$FILENAME""
runCommand

export FILENAME="BASESIM_24-1.sim"
export yaw=8
export newName=""$yaw"_"$FILENAME""
runCommand

read -p "Press enter to continue"
