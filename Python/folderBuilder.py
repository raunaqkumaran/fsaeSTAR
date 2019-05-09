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

# Get config vars
controllerVars = dict()
controllerVars['clumpFlag'] = bbs.get_config_var("CLUMP", configFile)
controllerVars['cluster'] = bbs.get_config_var("CLUSTER", configFile)
controllerVars['procs'] = bbs.get_config_var("PROCS", configFile)

# Write to batch file







