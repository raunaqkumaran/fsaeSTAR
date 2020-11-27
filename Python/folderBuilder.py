import batchBuilderSupport as bbs
import os
import sys

# INPUTS HERE

if len(sys.argv) > 1:
    if os.path.isdir(sys.argv[1]):
        importPath = sys.argv[1]
        newArgv = []
        for i in range(0, len(sys.argv)):
            if i is not 1:
                newArgv.append(sys.argv[i])
else:
    importPath = os.getcwd()  # current working directory call; change to wherever the .configs are exported to

# FUNCTION CALLS

# Iterates through every file in importPath. This pulls all files with ".config" extension, so don't put unwanted ".config" files in here
for contents in os.listdir(importPath):
    configFileName = contents
    if os.path.splitext(configFileName)[1] == ".config":

        # Get config vars
        configFile = open(importPath + os.sep + configFileName, "r")

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

        timeStamp = bbs.get_timestamp()
        outputFileName = timeStamp + "_" + "controller.sh"
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
        configFile.close()

        os.system("chmod +x " + outputFileName)
        os.system("./" + outputFileName)
