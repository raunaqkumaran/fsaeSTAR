'''This is literally the definition of a one-off piece of code. Just writing this so i can make the 5 terabytes of
sim files I have less massive. '''

import batchBuilderSupport as bbs
import os
import sys
path = r"D:\Archive\Formula\2020"
sizeLimit = 400e6
fileList = bbs.get_file_list(path, sizeLimit)
starPath = r'"C:\Program Files\Siemens\15.02.009-R8\STAR-CCM+15.02.009-R8\star\bin\starccm+.exe"'
macroPath = r"C:\Users\rauna\Documents\GitHub\fsaeSTAR\src\universalMeshClear.java"
outputFile = "deleteEverything.bat"
output = open(outputFile, "w")
for x in fileList:
    cmdString = starPath + " -power -podkey " + "5SaJLFgZg7E9iD6YITAjlw" + " -batch " + macroPath + ' "' + x + '"'
    output.write(cmdString)
    output.write(os.linesep)
output.close()
