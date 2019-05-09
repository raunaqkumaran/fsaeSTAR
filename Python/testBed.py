import batchBuilderSupport as bbs

# Get config var interface

file = open("simConfig.config", "r")
var = "CLUSTER"
val = bbs.get_config_var(var, file)
print(val)
file.close()

# var dict interface
