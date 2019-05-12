import batchBuilderSupport as bbs

# Get config var interface

file = open("linuxConfig.config", "r")
var = "CLUSTER"
val = bbs.get_config_var(var, file)
valdict = bbs.get_env_vals(file)
print(val)
print(valdict)
file.close()

file_list = ["hello.sim", "bye.sim", "test.sim"]
config_file_name = "linuxConfig.config"
config_file = open(config_file_name, "r")
bbs.individuals(file_list, config_file)
bbs.clumped(file_list, config_file)
# var dict interface
