set macroName=macroController.java
set fileLoc=C:\Users\rauna\Documents\CFD\Roll series\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=2
set POD=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_24-1.sim
set geometryManip=true
set domainSet=full

set roll=-0.5
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=-1
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=-1.5
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=-2
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=0.5
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=1
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=1.5
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

set roll=2
set newName=roll_%roll%_%fileName%
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -licpath 1999@dock.ecn.purdue.edu

PAUSE
