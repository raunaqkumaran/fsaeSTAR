'''Applies a macro on a set of files in a given folder'''

import batchBuilderSupport as bbs
import os
import sys

path = r"D:\2021 Simulations"
sizeLimit = 400e6
fileList = bbs.get_file_list(path, sizeLimit)
starPath = r'"C:\Program Files\Siemens\15.02.009-R8\STAR-CCM+15.02.009-R8\star\bin\starccm+.exe"'
macroPath = [r"C:\Users\rauna\Documents\GitHub\fsaeSTAR\src\universalMeshClear.java"]
classPath = r"C:\Users\rauna\Documents\GitHub\fsaeSTAR\src"
outputFile = "deleteEverything.bat"
output = open(outputFile, "w")
for x in fileList:
    for y in macroPath:
        cmdString = starPath + " -power -podkey " + "5SaJLFgZg7E9iD6YITAjlw" + " -classpath " + classPath + " -batch " + y + ' "' + x + '"'
        output.write(cmdString)
        output.write(os.linesep)
output.close()
