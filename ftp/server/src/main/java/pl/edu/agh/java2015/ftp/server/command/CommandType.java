package pl.edu.agh.java2015.ftp.server.command;

/**
 * Enum class, which contains all handled commands with number of arguments that they take
 * @author Kamil Osuch
 * @version 1.0
 */
public enum CommandType{
    USER(1), PASS(1), QUIT(0), NOOP(0), PASV(0), STOR(1), RETR(1), APPE(1), ABOR(0), DELE(1), RMD(1),
    MKD(1), PWD(0), LIST(0), CWD(1), CHMOD(2), NOTHANDLED(0);


    private final int numberOfArgs;

    /**
     * Creates {@link CommandType} with specified number of args
     * @param numberOfArgs number of arguments that command takes
     */
    CommandType(int numberOfArgs){
        this.numberOfArgs = numberOfArgs;
    }
    public int getNumberOfArgs() {
        return numberOfArgs;
    }
}