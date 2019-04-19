export SIMPATH="C:\Users\rauna\Documents\CFD"
export STARLOC="C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe"
export PODKEY="iOJDZCUKWsPIjut3CFrKXQ"
export PROCESSES="2"
export CP="C:\Users\rauna\Documents\fsaeSTAR\src"



export fileName="BASESIM_25-1_WT.sim"
export newName="WT_BASELINE.sim"
export MACRO="macroController.java"
export frh="0"
export rrh="0"
export rws="0"
export fws="0"
export uts="0"
export cs="0"
export ps="0"
export sceneMultiplier="0"
export yaw="0"
export roll="0"
export domainSet="full"

"$STARLOC" "$SIMPATH/$FILENAME" -batch "$CP/$MACRO" -machilefile $PBS_NODEFILE -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power


