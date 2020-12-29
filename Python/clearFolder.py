import os

current_dir = os.getcwd()

all_files = [current_dir + os.sep + x for x in os.listdir(current_dir)]

for file in all_files:
    if file.endswith(".sh") or file.endswith(".out") or file.contains("nodefile."):
        print("Removing: " + file)
        os.remove(file)

print("----Complete----")