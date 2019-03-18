#!/bin/bash
set -x

export PODKEY="iOJDZCUKWsPIjut3CFrKXQ";
export MACRO="macroController.java";
export PROCESSES="4";
export CP="/mnt/c/Users/rauna/Documents/WIP macros/src";
export SIMPATH="/mnt/c/Users/rauna/Documents/CFD";
export STARLOC="/mnt/c/Program Files/CD-Adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe";

export FILENAME="BASESIM_25-1.sim"
"$STARLOC" "$SIMPATH/$FILENAME" -batch "$CP/$MACRO" -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power

PAUSE
