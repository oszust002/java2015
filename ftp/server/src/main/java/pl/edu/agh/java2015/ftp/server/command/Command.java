package pl.edu.agh.java2015.ftp.server.command;

import java.util.Arrays;

/**
 * Created by Kamil on 14.01.2016.
 */
public class Command {

    private final CommandType type;
    private final String[] arguments;

    public Command(CommandType type) {
        this.type = type;
        arguments = new String[0];
    }

    public Command(CommandType type, String[] arguments) {
        this.type = type;
        this.arguments = arguments;
    }


    public CommandType getType() {
        return type;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

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
}
