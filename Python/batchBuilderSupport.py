import os

def linux_write_flag(header, value, file):
    writestr = ("export " + str(header) + "=" + '"' + str(value) + '"' + '\n').encode()
    file.write(writestr)


def linux_write_command(file):
    writestr = '\n"$STARLOC" "$SIMPATH/$FILENAME" -batch "$CP/$MACRO" -machinefile $PBS_NODEFILE -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power\n'
    writestr = writestr.encode()
    file.write(writestr)


def linux_write_blanks(file, blankcount):
    for i in range(0, blankcount):
        file.write('\n'.encode())
        i += 1


def windows_write_flag(header, value, file):
    writestr = ("set " + str(header) + "=" + str(value) + '\r\n').encode()
    file.write(writestr)


def windowsWriteBlanks(file, blankcount):
    for i in range(0, blankcount):
        file.write('\r\n'.encode())
        i += 1


def windowsWriteCommand(file):
    writestr = '\r\n"%STARLOC%" "%SIMPATH%\%FILENAME%" -batch "%CP%\%MACRO%" -np %PROCESSES% -power -podkey "%PODKEY% -classpath "%CP%"\r\n'
    writestr = writestr.encode()
    file.write(writestr)


def writeFlag(header, value, file, platform):
    if platform == "Linux":
        linux_write_flag(header, value, file)
    if platform == "Windows":
        windows_write_flag(header, value, file)


def writeCommand(file, platform):
    if platform == "Linux":
        linux_write_command(file)
    if platform == "Windows":
        windowsWriteCommand(file)


def writeBlanks(blankcount, platform, file):
    if platform == "Linux":
        linux_write_blanks(file, blankcount)
    if platform == "Windows":
        windowsWriteBlanks(file, blankcount)


def get_file_list(path):
    file_list = os.listdir(path)
    sim_list = []
    for f in file_list:
        if f.endswith(".sim"):
            sim_list.append(f)
    return sim_list


def get_config_var(var, file):
    file.seek(0)
    lines = file.read()
    lines = lines.split(";")
    for line in lines:
        line = line.strip()
        if line.startswith(var):
            split_line = line.split("=")
            val = split_line[1].strip()
            return val
    return ""

