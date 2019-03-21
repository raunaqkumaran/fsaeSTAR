set macroName=macroController.java
set fileLoc=C:\Users\rauna\Documents\CFD\Ride height series\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=4
set PODKEY=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_25-1.sim
set geometryManip=true
set domainSet=full

set frh=-1

set rrh=-0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.5

set rrh=-0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.5

set rrh=-0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=1

set rrh=0
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.25
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.5
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-0.75
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set rrh=-1
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

PAUSE
