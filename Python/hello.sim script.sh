export CLUSTER="scholar"
export PROCS="40"
export CLUMP="false"
export WALLTIME="72:00:00"
export FILENAME="hello.sim"

"$STARLOC" "$SIMPATH/$FILENAME" -batch "$CP/$MACRO" -machinefile $PBS_NODEFILE -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power
