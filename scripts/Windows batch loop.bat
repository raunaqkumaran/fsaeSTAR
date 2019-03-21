set macroName=exportScenes.java
set fileLoc=E:\Google Drive\Sync folders\Scholar sync\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=2
set POD=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_25-1.sim
set multiplier=1.8

for %%fileName in (%fileLoc*.sim) do (

"%location%" "%fileLoc%%fileName%" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -podkey %POD% -licpath 1999@flex.cd-adapco.com -power

)

PAUSE
