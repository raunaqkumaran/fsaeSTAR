set macroName=macroController.java
set fileLoc=C:\Users\rauna\Documents\CFD\Yaw series\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=2
set POD=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_24-1.sim
set geometryManip=true
set domainSet=full

set yaw=0.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=1
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=1.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=2
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=2.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=3
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=3.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=4
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=4.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=5.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=6
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=6.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=7
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=7.5
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set yaw=8
set newName=%yaw%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

PAUSE
