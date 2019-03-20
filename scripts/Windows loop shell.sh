#!/bin/bash
set -x

linuxToWindows()
{
  temp=$(wslpath -w "$1")
}

runCommand()
{
  linuxToWindows "$FILENAME"
  FILENAME="$temp"
  linuxToWindows "$STARLOC"
  STARLOC="$temp"
  linuxToWindows "$CP"
  CP="$temp"


  cmd.exe "/C "$STARLOC" "$FILENAME" -batch "$CP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$CP" -power -podkey $PODKEY"
}

setENVWin()
{
  export $1="$2"
  cmd.exe "/C setx $1 """
  cmd.exe "/C setx $1 "$2""
}


setENVWin PODKEY "iOJDZCUKWsPIjut3CFrKXQ"
setENVWin MACRO "macroController.java"
setENVWin PROCESSES "1"
setENVWin CP '/mnt/c/Users/rauna/Documents/WIP macros/src'
setENVWin SIMPATH '/mnt/g/Google Drive/Formula SAE/FSAE Aero/2019/CFD/Unanalyzed sim data/'
setENVWin STARLOC "/mnt/c/Program Files/CD-adapco/13.04.010-R8/STAR-CCM+13.04.010-R8/star/bin/starccm+.exe"
setENVWin preprocess false
setENVWin process false
setENVWin postprocess true
setENVWin domainSet half
setENVWin yaw 0
setENVWin roll 0
setENVWin rrh 0
setENVWin frh 0
setENVWin fws 0
setENVWin rws 0
setENVWin uts 0
setENVWin cs 1
setENVWin ps 1
setENVWin sceneMultiplier 1.8

declare -a arr
i=0

for FILENAME in "$SIMPATH"*.sim; do
  [ -e "$FILENAME" ] || continue
  arr[$i]="$FILENAME"
  i=$((i+1))
done

for ((j=0; j<$i; j++))
do
  FILENAME=${arr[$j]}
  runCommand
  mv "$FILENAME" "$SIMPATH"/Complete
done

read -p "Press enter to continue"
