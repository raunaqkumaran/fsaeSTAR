def linux_write_flag(header, value, file):
    writestr = ("export " + str(header) + "=" + '"' + str(value) + '"' + '\n').encode()
    file.write(writestr)


def linux_write_command(file):
    writestr = '\n"$STARLOC" "$SIMPATH/$FILENAME" -batch "$CP/$MACRO" -machinefile $PBS_NODEFILE -cpubind -rsh ssh -np $PROCESSES -podkey $PODKEY -classpath "$CP" -power\n'
    writestr = writestr.encode()
    file.write(writestr)


def linuxWriteBlanks(file, blankcount):
    for i in range(0, blankcount):
        file.write('\n'.encode())
        i += 1


def windowsWriteFlag(header, value, file):
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
        windowsWriteFlag(header, value, file)


def writeCommand(file, platform):
    if platform == "Linux":
        linux_write_command(file)
    if platform == "Windows":
        windowsWriteCommand(file)


def writeBlanks(blankcount, platform, file):
    if platform == "Linux":
        linuxWriteBlanks(file, blankcount)
    if platform == "Windows":
        windowsWriteBlanks(file, blankcount)