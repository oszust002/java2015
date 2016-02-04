package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that parses all commands from the client and sends responses directly to client
 * @author Kamil Osuch
 * @version 1.0
 */
public class CommandHandler {
    private Socket socket;
    private final Scanner scanner;
    private final PrintStream output;

    /**
     * Creates handler for specified socket connection
     * @param socket Specified socket connection with user
     * @throws IOException
     */
    public CommandHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.output = new PrintStream(socket.getOutputStream());
    }

    /**
     * Parses text got from client, and parses it to the {@link Command}
     * @return Command with specified {@link CommandType} and arguments
     */
    public Command parseCommand(){
        String userCommand;
        try {
            userCommand = scanner.nextLine();
        }catch (NoSuchElementException e){
            return null;
        }

        String[] splittedCommand = userCommand.split(" ");
        CommandType type;
        try {
            type = CommandType.valueOf(splittedCommand[0]);
        }catch (IllegalArgumentException e){
            System.out.println("Command unknown: "+splittedCommand[0]);
            type = CommandType.NOTHANDLED;
        }
        final String[] arguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
        Command command = new Command(type, arguments);
        System.out.println("Command accepted: " + command);
        return command;
    }

    /**
     * Sends specified {@link Response} to the client
     * @param response {@link Response} which will be sent
     */
    public void sendResponse(Response response){
        output.print(response.toSendString());
        System.out.println("Server response: "+response);
    }

    /**
     * Closes socket connection with the client
     */
    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if socket connection is running
     * @return True if connection exists, false otherwise
     */
    public boolean isSocketRunning(){
        return !socket.isClosed();
    }
}
