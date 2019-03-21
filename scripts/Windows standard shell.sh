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
setENVWin SIMPATH "C:\Users\rauna\Documents\CFD\Ride height series"
setENVWin STARLOC "/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe"
setENVWin domainSet half
setENVWin geometryManip true
setENVWin FILENAME BASESIM_25-1.sim

setENVWin frh -1
setENVWin rrh -0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh -0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh -0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh 0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh 0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh 0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -1
setENVWin rrh 1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh -1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh -0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh -0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh 0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh 0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh 0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh -0.5
setENVWin rrh 1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh -1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh -0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh -0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh -0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh 0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh 0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 0.5
setENVWin rrh 1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 1
setENVWin rrh -0.25
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 1
setENVWin rrh -0.5
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 1
setENVWin rrh -0.75
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand

setENVWin frh 1
setENVWin rrh -1
newnameVal="$frh"_"$rrh".sim
setENVWin newName "$newnameVal"
runCommand
