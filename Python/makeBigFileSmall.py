'''This is literally the definition of a one-off piece of code. Just writing this so i can make the 5 terabytes of
sim files I have less massive. '''

import batchBuilderSupport as bbs
import os
path = r"C:\Users\rauna\Documents\CFD"
fileList = bbs.get_file_list(path)
starPath = r'"C:\Program Files\Siemens\14.06.013-R8\STAR-CCM+14.06.013-R8\star\bin\starccm+.exe"'
macroPath = r"C:\Users\rauna\Documents\fsaeSTAR\src\universalMeshClear.java"
outputFile = "deleteEverything.bat"
output = open(outputFile, "w")
for x in fileList:
    fileTotal = ' "' + path + os.sep + x + '"'
    cmdString = starPath + " -licpath 1999@dock.ecn.purdue.edu -batch " + macroPath + fileTotal
    output.write(cmdString)
    output.write(os.linesep)
output.close()
