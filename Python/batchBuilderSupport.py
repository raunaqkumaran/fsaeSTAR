import os

def posix_write_flag(header, value, file):
    writestr = ("export " + str(header) + "=" + '"' + str(value) + '"' + '\n').encode()
    file.write(writestr)


def posix_write_command(file):
    command_file = open("linux_command.txt")
    writestr = command_file.read()
    writestr = writestr.encode()
    file.write(writestr)
    command_file.close()


def posix_write_blanks(file, blankcount):
    for i in range(0, blankcount):
        file.write('\n'.encode())
        i += 1


def nt_write_flag(header, value, file):
    writestr = ("set " + str(header) + "=" + str(value) + '\r\n').encode()
    file.write(writestr)


def ntWriteBlanks(file, blankcount):
    for i in range(0, blankcount):
        file.write('\r\n'.encode())
        i += 1


def nt_write_command(file):
    command_file = open("windows_command.txt")
    writestr = command_file.read()
    writestr = writestr.encode()
    file.write(writestr)


def write_flag(header, value, file, platform):
    if platform == "posix":
        posix_write_flag(header, value, file)
    if platform == "nt":
        nt_write_flag(header, value, file)


def writeCommand(file, platform):
    if platform == "posix":
        posix_write_command(file)
    if platform == "nt":
        nt_write_command(file)


def writeBlanks(blankcount, platform, file):
    if platform == "posix":
        posix_write_blanks(file, blankcount)
    if platform == "nt":
        ntWriteBlanks(file, blankcount)


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


def get_env_vals(file):
    var_dict = dict()
    file.seek(0)
    lines = file.read()
    lines = lines.split(";")

    for line in lines:
        if line is "":
            continue
        line = line.strip()
        val = line.split("=")[0].strip()
        var_dict[val] = get_config_var(val, file)

    return var_dict


def individuals(file_list, config_file):
    output_files = []
    for x in file_list:
        output_file_name = x + " script.sh"
        output_files.append(output_file_name)
        output_file = open(output_file_name, "wb")
        config_list = get_env_vals(config_file)
        for key, val in config_list.items():
            write_flag(key, val, output_file, os.name)
        write_flag("FILENAME", x, output_file, os.name)
        write_flag("newName", os.sep + "Processed" + os.sep + x, output_file, os.name)
        writeCommand(output_file, os.name)
        output_file.close()
    return output_files


def clumped(file_list, config_file):
    output_file_name = "clumped_run.sh"
    output_file = open(output_file_name, "wb")
    config_list = get_env_vals(config_file)
    for key, val in config_list.items():
        write_flag(key, val, output_file, os.name)
    for x in file_list:
        write_flag("FILENAME", x, output_file, os.name)
        write_flag("newName", os.sep + "Processed" + os.sep + x, output_file, os.name)
        writeCommand(output_file, os.name)
        writeBlanks(2, os.name, output_file)
    output_file.close()
    return output_file_name


