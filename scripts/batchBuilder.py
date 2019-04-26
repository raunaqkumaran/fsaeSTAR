import pandas
import batchBuilderSupport as bbs

''' INPUTS START '''
simPlanFile = "1928_190425.xlsx"
sheetName = "Simulation plan"
batchName = simPlanFile + ".sh"

simulationPlan = pandas.read_excel(simPlanFile, sheet_name=sheetName, header=None)
outputFile = open(batchName, "wb")

platform = "Linux"          # Either use "Linux" or "Windows"

''' INPUTS END '''

'''DON'T MESS WITH ANYTHING UNDER THIS'''
totalRows = simulationPlan.shape[0]
totalColumns = simulationPlan.shape[1]

rowCounter = 1

while simulationPlan.iloc[rowCounter, 0] != "LOCALS":
    header = simulationPlan.iloc[rowCounter, 0]
    value = simulationPlan.iloc[rowCounter, 1]
    if not pandas.isnull(header):
        bbs.writeFlag(header, value, outputFile, platform)
        print("written")
    rowCounter += 1

rowCounter += 1

bbs.writeBlanks(3, platform, outputFile)

while pandas.isnull(simulationPlan.iloc[rowCounter, 0]):
    rowCounter += 1
    headerLoc = rowCounter

i = 0
headerVals = []

while i in range (0, totalColumns) and not pandas.isnull(simulationPlan.iloc[headerLoc, i]):
    headerVals.append(simulationPlan.iloc[headerLoc, i])
    i += 1

rowCounter += 1

i = 0

while rowCounter in range (rowCounter, totalRows) and not pandas.isnull(simulationPlan.iloc[rowCounter, 0]):
    while i in range(0, totalColumns) and not pandas.isnull(simulationPlan.iloc[rowCounter, i]):
        header = headerVals[i]
        val = simulationPlan.iloc[rowCounter, i]
        bbs.writeFlag(header, val, outputFile, platform)
        i += 1
    bbs.writeCommand(outputFile, platform)
    bbs.writeBlanks(2, platform, outputFile)
    rowCounter += 1
    i = 0


print("End here")