import batchBuilderSupport as bbs
import os

# TODO
'''
Get file list - DONE
Write function to rip command format from a test file - DONE
Write general functions to rip flags from a configuration file - DONE
Write function to generate batch file - DONE
Write generic functions to submit commands to a terminal 
Write the logic to move files around once they're done. 
Build program
'''


# INPUTS HERE

configFileName = "linuxConfig.config"
configFile = open(configFileName, "r")
ntCommandFile = "windows_command.txt"
posixCommandFile = "linux_command.txt"
qsubFile = "qsub.txt"
platform = os.name

# FUNCTION CALLS


# Get command strings
File = open(ntCommandFile, "r")
ntCommand = File.read()
File.close()
File = open(posixCommandFile, "r")
posixCommand = File.read()
File.close()
File = open(qsubFile, "r")
qsubCommand = File.read()
File.close()

# Get config vars
controllerVars = bbs.get_env_vals(configFile)
controllerVars['NODES'] = int(int(controllerVars['PROCS']) / 20)
controllerVars['PPN'] = int(int(controllerVars['PROCS']) / controllerVars['NODES'])
path = controllerVars['SIMPATH']

# Get file list
file_list = bbs.get_file_list(path)


# Set up output files

outputFileName = "controller.sh"
outputFile = open(outputFileName, "wb")
for key, value in controllerVars.items():
    bbs.write_flag(key, value, outputFile, platform)

if 'CLUMP' not in controllerVars:
    print("CLUMP flag missing")
    exit(-1)

if controllerVars['CLUMP'] == "false":
    child_scripts = bbs.individuals(file_list, configFile)
    if platform == "posix":
        for x in child_scripts:
            outputFile.write((qsubCommand + " " + '"' + x + '"').encode())
            bbs.writeBlanks(2, platform, outputFile)

elif controllerVars['CLUMP'] == "true":
    child_script = bbs.clumped(file_list, configFile)
    if platform == "posix":
        outputFile.write((qsubCommand + " " + '"' + child_script + '"').encode())

if platform == "posix":
    os.system("chmod +x " + outputFileName)
    os.system("./" + outputFileName)
outputFile.close()








