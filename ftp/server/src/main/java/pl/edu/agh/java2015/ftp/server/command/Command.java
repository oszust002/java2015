package pl.edu.agh.java2015.ftp.server.command;

import java.util.Arrays;

/**
 * Class which represents every command entered from the client
 * @author Kamil Osuch
 * @version 1.0
 */
public class Command {

    private final CommandType type;
    private final String[] arguments;

    /**
     * Creates Command with specified {@link CommandType} and arguments connected with it
     * @param type Type of Command
     * @param arguments Table of arguments connected with specified command
     */
    public Command(CommandType type, String[] arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    /**
     * Returns type of specific {@link Command}
     * @return {@link CommandType} of Command
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Returns one of the arguments of {@link Command} on specific index
     * @param i Index of specified argument in arguments table
     * @return Argument on the i-th position in table of arguments
     */
    public String getArgument(int i) {
        return arguments[i];
    }

    @Override
    public String toString() {
        return "Command: "+ type +
                ", arguments=" + Arrays.toString(arguments);
    }

    /**
     * Checks if specified {@link Command} have correct argument amount, which is specified in {@link CommandType}
     * @return True if have correct number of arguments, false otherwise
     */
    public boolean hasCorrectArgumentsAmount(){
        return type.getNumberOfArgs() == arguments.length;
    }
}
