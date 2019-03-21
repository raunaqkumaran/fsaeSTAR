set macroName=macroController.java
set fileLoc=G:\Google Drive\Formula SAE\FSAE Aero\2019\CFD\Unanalyzed sim data\
set macroLoc=C:\Users\rauna\Documents\WIP macros\src\
set location=C:\Program Files\CD-adapco\13.04.010-R8\STAR-CCM+13.04.010-R8\star\bin\starccm+.exe
set procs=2
set POD=iOJDZCUKWsPIjut3CFrKXQ
set multiplier=2
set fws=1
set rws=1
set cs=1
set uts=1
set ps=1
set postprocess=true
set preprocess=false
set process=false


for %%i in ("%fileLoc%"*.sim) do (

"%location%" "%%i" -batch "%macroLoc%%macroName%" -cpubind -rsh ssh -np %procs% -podkey %POD% -licpath 1999@flex.cd-adapco.com -power

)

PAUSE
