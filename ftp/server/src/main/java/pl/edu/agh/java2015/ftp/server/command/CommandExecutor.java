package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kamil on 14.01.2016.
 */
public class CommandExecutor {
    private final Session session;
    private final Filesystem filesystem;

    public CommandExecutor(Session session, Filesystem filesystem){
        this.session = session;
        this.filesystem = filesystem;
    }

    public void executeCommand(Command command){
        if (!session.isAuthenticated() && (command.getType() != CommandType.PASS && command.getType() != CommandType.USER
                && command.getType() != CommandType.QUIT)){
            session.sendResponse(ResponseType.BAD_SEQUENCE_OF_COMMANDS);
            return;
        }
        if( !command.hasCorrectArgumentsAmount()){
            System.out.println("Error in command: " + command + ", Invalid number of args");
            session.sendResponse(ResponseType.SYNTAX_ERROR);
            return;
        }

        switch (command.getType()){
            case USER:
                session.setUsername(command.getArgument(0));
                break;
            case PASS:
                session.checkPassword(command.getArgument(0));
                break;
            case NOOP:
                session.sendResponse(ResponseType.COMMAND_SUCCESSFUL);
                break;
            case QUIT:
                session.disconnect();
                break;
            case LIST:
                System.out.print(filesystem.showFilesOnPath(Paths.get("")));
                break;
            case PASV:
                session.passiveConnection();
                break;
        }
    }
}
