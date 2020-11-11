import csv
import os.path
import sys

##### Change these depending on where things are/what they are named #####
if len(sys.argv) > 1:
    exportPath = sys.argv[1]
else:
    exportPath = os.getcwd()
dataFile = 'data.csv'
##### -------------------------------------------------------------- #####

data = []  # empty list for imported config inputs

# Parsing .csv
try:
    with open(dataFile, 'r') as importFile:
        reader = csv.reader(importFile)

        for row in reader:
            data.append(row)
    importFile.close()
except:
    print("Error: Unable to locate or parse data")
else:

    header = data[0]  # Headers to use in export
    if not os.path.isdir(exportPath):
        print("Export path doesn't exist")
        exit()
    # Formatting/exporting .config
    for i in range(1, len(data)):

        with open(exportPath + 'linuxConfig_{:03d}.config'.format(i), 'w') as exportFile:

            for j in range(0, len(data[i])):
                exportFile.write(
                    header[j] + " = " + data[i][j] + ";\n")  # this leaves an empty line; not worth a conditional

        exportFile.close()
