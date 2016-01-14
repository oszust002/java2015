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

    public String getArgument(int i) {
        return arguments[i];
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    public boolean hasCorrectArgumentsAmount(){
        return type.getNumberOfArgs() == arguments.length;
    }
}
