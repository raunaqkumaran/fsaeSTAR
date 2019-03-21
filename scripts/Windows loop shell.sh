#!/bin/bash
set -x

linuxToWindows()
{
  temp=$(wslpath -w "$1")
}

windowsToLinux()
{
  temp=$(wslpath -u "$1")
}

runCommand()
{
  linuxToWindows "$CP"
  windowsCP="$temp"
  linuxToWindows "$STARLOC"
  windowsSTARLOC="$temp"
  linuxToWindows "$FILENAME"
  windowsFILENAME="$temp"
  cmd.exe "/C "$windowsSTARLOC" "$windowsFILENAME" -batch "$windowsCP"\\"$MACRO" -cpubind -rsh ssh -np "$PROCESSES" -classpath "$windowsCP" -power -podkey $PODKEY"
}

setENVWin()
{
  export $1="$2"
  cmd.exe "/C setx $1 """
  cmd.exe "/C setx $1 "$2""
}


setENVWin PODKEY "iOJDZCUKWsPIjut3CFrKXQ"
setENVWin MACRO "macroController.java"
setENVWin PROCESSES "7"
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
setENVWin fws 1
setENVWin rws 1
setENVWin uts 1
setENVWin cs 1
setENVWin ps 1
setENVWin sceneMultiplier 2

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
  FILENAME=${arr[$j]}
  mv "$FILENAME" "$SIMPATH"Complete
done

read -p "Press enter to continue"
