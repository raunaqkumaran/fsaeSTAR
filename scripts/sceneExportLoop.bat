set macroName=exportScenes.java
set fileLoc=G:\Google Drive\Formula SAE\FSAE Aero\2019\CFD\Unanalyzed sim data\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=2
set POD=iOJDZCUKWsPIjut3CFrKXQ
set fileName=BASESIM_25-1.sim
set sceneMultiplier=1.8
set fws=0
set uts=0
set cs=1
set rws=0
set ps=1



for %%i in ("%fileLoc%"*.sim) do (

echo %%i

"%location%" "%%i" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -podkey %POD% -licpath 1999@flex.cd-adapco.com -power 

)


PAUSE

