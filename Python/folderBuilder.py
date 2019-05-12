import pandas
import batchBuilderSupport as bbs
import os

# TODO
'''
Get file list - DONE
Write function to rip command format from a test file - DONE
Write general functions to rip flags from a configuration file - DONE
Write function to generate batch file
Write generic functions to submit commands to a terminal - Not needed
Write the logic to move files around once they're done. 
Build program
'''


# INPUTS HERE

configFileName = "simConfig.config"
configFile = open(configFileName, "r")
windowsCommandFile = "windows_command.txt"
linuxCommandFile = "linux_command.txt"
qsubFile = "qsub.txt"
path = os.getcwd()

# FUNCTION CALLS

# Get file list
file_list = bbs.get_file_list(path)

# Get command strings
File = open(windowsCommandFile, "r")
windowsCommand = File.read()
File.close()
File = open(linuxCommandFile, "r")
linuxCommand = File.read()
File.close()
File = open(qsubFile, "r")
qsubCommand = File.read()
File.close()

# Get config vars
controllerVars = bbs.get_env_vals(configFile)
controllerVars['NODES'] = int(int(controllerVars['PROCS']) / 20)
controllerVars['PPN'] = int(int(controllerVars['PROCS']) / controllerVars['NODES'])


# Set up output files

outputFileName = "controller.sh"
outputFile = open(outputFileName, "wb")
for key, value in controllerVars.items():
    bbs.writeFlag(key, value, outputFile, "Linux")

if 'CLUMP' not in controllerVars:
    print("CLUMP flag missing")
    exit(-1)

if controllerVars['CLUMP'] == "false":
    child_scripts = bbs.individuals(file_list, configFile)
    for x in child_scripts:
        outputFile.write((qsubCommand + " " + '"' + x + '"').encode())
        bbs.linux_write_blanks(outputFile, 1)
elif controllerVars['CLUMP'] == "true":
    child_script = bbs.clumped(file_list, configFile)
    outputFile.write((qsubCommand + " " + '"' + child_script + '"').encode())

outputFile.close()








