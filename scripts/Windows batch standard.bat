set macroName=macroController.java
set fileLoc=C:\Users\rauna\Documents\CFD\
set macroLoc=C:\Users\rauna\Documents\fsaeSTAR\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=4
set PODKEY=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_25-1.sim
set geometryManip=true
set preprocess=false
set process=false
set postprocess=false

set frh=-1.01
set rrh=0.26
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.76
set rrh=-1.01
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.76
set rrh=-0.24
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.49
set rrh=-0.49
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.49
set rrh=-0.76
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=-0.49
set rrh=0.51
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0
set rrh=0.52
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.24
set rrh=0.24
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.25
set rrh=-0.51
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.25
set rrh=1.01
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.25
set rrh=-0.76
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.51
set rrh=0
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.51
set rrh=0.51
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.75
set rrh=0.01
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.76
set rrh=-1.01
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

set frh=0.75
set rrh=-0.49
set newName=%frh%_%rrh%.sim
"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -np %procs% -power -podkey "%PODKEY%"

PAUSE
