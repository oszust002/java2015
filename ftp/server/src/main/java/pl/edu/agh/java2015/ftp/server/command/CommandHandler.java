package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.command.Command;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Kamil on 14.01.2016.
 */
public class CommandHandler {
    private Socket socket;
    private final Scanner scanner;
    private final PrintStream output;

    public CommandHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.output = new PrintStream(socket.getOutputStream());
    }

    public Command parseCommand(){
        String userCommand;
        try {
            userCommand = scanner.nextLine();
        }catch (NoSuchElementException e){
            return null;
        }

        String[] splittedCommand = userCommand.split(" ");
        Command.CommandType type;
        try {
            type = Command.CommandType.valueOf(splittedCommand[0]);
        }catch (IllegalArgumentException e){
            System.out.println("Command unknown");
            type = Command.CommandType.NOTHANDLED;
        }
        final String[] arguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
        Command command = new Command(type, arguments);
        System.out.println("Command accepted: " + command);
        return command;
    }

    public void sendResponse(){}//FIXME: implement

    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSocketRunning(){
        return !socket.isClosed();
    }
}
