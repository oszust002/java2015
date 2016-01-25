package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
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
                fileList(session);
                break;
            case PASV:
                session.createPassiveConnection();
                break;
            case PWD:
                showCWD(filesystem.getCurrentDirectoryPath());
                break;
            case CWD:
                changeDirectory(command.getArgument(0));
        }
    }

    private void showCWD(Path currentDirectoryPath) {
        String path = currentDirectoryPath.toString();
        if(path.equals(""))
            session.sendResponse(ResponseType.CURRENT_DIRECTORY, "/");
        else
            session.sendResponse(ResponseType.CURRENT_DIRECTORY, path.toString());
    }

    private void fileList(Session session) {
        if(session.passiveConnectionExist()){
                session.getPassiveConnection().sendFileList(filesystem.showFilesOnPath(Paths.get("")));
                session.sendResponse(ResponseType.PASSIVE_CONNECTION, "ASCII", "/bin/ls");
        }
        else{
            session.sendResponse(ResponseType.CANT_OPEN_DATA_CONNECTION);
        }
    }

    private void changeDirectory(String path){
        try {
            filesystem.changeDirectory(path);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL, "CWD");
        }catch (FileException e){
            session.sendResponse(e.getResponse());
        }
    }
}
