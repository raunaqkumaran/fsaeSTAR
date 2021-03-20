'''Applies a macro on a set of files in a given folder'''

import batchBuilderSupport as bbs
import os
import sys

path = r"D:\Archive\Formula"
sizeLimit = 400e6
fileList = bbs.get_file_list(path, sizeLimit)
starPath = r'"C:\Program Files\Siemens\15.06.008-R8\STAR-CCM+15.06.008-R8\star\bin\starccm+.exe"'
macroPath = [r"C:\Users\rauna\Documents\GitHub\fsaeSTAR\src\universalMeshClear.java"]
classPath = r"C:\Users\rauna\Documents\GitHub\fsaeSTAR\src"
outputFile = "deleteEverything.bat"
output = open(outputFile, "w")
for x in fileList:
    print("Writing line for ", x)
    for y in macroPath:
        cmdString = starPath + " -power -podkey " + "ejp6DLYl3MCoXIiuatSpAg" + " -classpath " + bbs.add_quotes(classPath) + " -batch " + bbs.add_quotes(y) + " " + bbs.add_quotes(x)
        output.write(cmdString)
        output.write(os.linesep)
output.close()
