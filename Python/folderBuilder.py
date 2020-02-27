import batchBuilderSupport as bbs
import os

# INPUTS HERE

configFileName = "linuxConfig.config"

# FUNCTION CALLS

# Get config vars
configFile = open(configFileName, "r")
controllerVars = bbs.get_env_vals(configFile)

if controllerVars['CLUSTER'] != "LOCAL":
    if int(controllerVars['PROCS']) < 20:
        controllerVars['NODES'] = 1
    else:
        controllerVars['NODES'] = int(int(controllerVars['PROCS']) / 20)

    controllerVars['PPN'] = int(int(controllerVars['PROCS']) / controllerVars['NODES'])

path = controllerVars['SIMPATH']

# Get command strings
posixCommand = bbs.generatecommand(controllerVars)
qsubCommand = bbs.generateqsub(controllerVars)

# Get file list
file_list = bbs.get_file_list(path)

# Set up output files

outputFileName = "controller.sh"
outputFile = open(outputFileName, "wb")
outputFile.write("#!/bin/sh\n".encode())
for key, value in controllerVars.items():
    bbs.posix_write_flag(key, value, outputFile)

if controllerVars['CLUMPED'] == "true":
    child_scripts = bbs.clumped(file_list, configFile, posixCommand)
    outputFile.write((qsubCommand + '"' + child_scripts + '"').encode())
    bbs.posix_write_blanks(outputFile, 2)
    os.system("chmod +x " + '"' + child_scripts + '"')

else:
    child_scripts = bbs.individuals(file_list, configFile, posixCommand)
    for x in child_scripts:
        outputFile.write((qsubCommand + '"' + x + '"').encode())
        bbs.posix_write_blanks(outputFile, 2)
        os.system("chmod +x " + '"' + x + '"')

outputFile.close()


os.system("chmod +x " + outputFileName)
os.system("./" + outputFileName)
