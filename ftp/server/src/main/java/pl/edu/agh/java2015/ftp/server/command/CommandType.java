package pl.edu.agh.java2015.ftp.server.command;

/**
 * Created by Kamil on 14.01.2016.
 */
public enum CommandType{
    USER(1), PASS(1), QUIT(0), NOOP(0), PASV(0), STOR(1), RETR(1), APPE(1), ABOR(0), DELE(1), RMD(1),
    MKD(1), PWD(0), LIST(0), CWD(1), CHMOD(2), NOTHANDLED(0);


    private final int numberOfArgs;
    CommandType(int numberOfArgs){
        this.numberOfArgs = numberOfArgs;
    }
    public int getNumberOfArgs() {
        return numberOfArgs;
    }
}