#!/bin/bash
set -x
set -e

runCommand()
{
  temp=$(wslpath -w "$FILENAME")
  FILENAME="$temp"
  "$STARLOC" "$FILENAME" -batch "$CP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$CP" -licpath 1999@dock.ecn.purdue.edu
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
setENVWin CP 'C:\Users\rauna\Documents\WIP macros\src'
setENVWin SIMPATH '/mnt/c/users/rauna/Documents/CFD/Yaw series/'
setENVWin STARLOC "/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe"
setENVWin domainSet half
setENVWin yaw 0
setENVWin roll 0
setENVWin rrh 0
setENVWin frh 0

declare -a arr
i=0

for FILENAME in "$SIMPATH"*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]=$FILENAME
  i=$((i+1))
done

for ((j=0; j<$i; j++))
do
  FILENAME=${arr[$j]}
  runCommand
done

read -p "Press enter to continue"
