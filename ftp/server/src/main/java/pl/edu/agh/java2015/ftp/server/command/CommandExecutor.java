package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kamil on 14.01.2016.
 */
public class CommandExecutor {
    private final Session session;
    private final Filesystem filesystem;
    private boolean wasAborted = false;

    public CommandExecutor(Session session, Filesystem filesystem){
        this.session = session;
        this.filesystem = filesystem;
    }

    public void executeCommand(Command command){
        if (!session.isAuthenticated() && (command.getType() != CommandType.PASS &&
                command.getType() != CommandType.USER && command.getType() != CommandType.QUIT)){
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
                break;
            case MKD:
                createDirectory(command.getArgument(0));
                break;
            case RMD:
                removeDirectory(command.getArgument(0));
                break;
            case DELE:
                removeRegularFile(command.getArgument(0));
                break;
            case STOR:
                addFileToServer(command.getArgument(0),false);
                break;
            case APPE:
                addFileToServer(command.getArgument(0),true);
                break;
            case RETR:
                sendFile(command.getArgument(0));
                break;
            case ABOR: {
                wasAborted = true;
                abort();
                }
                break;
            case CHMOD:
                changeFilePermissions(command.getArgument(0), command.getArgument(1));
                break;
            case NOTHANDLED:
                session.sendResponse(ResponseType.COMMAND_NOT_IMPLEMENTED);
        }
    }

    private void changeFilePermissions(String file, String numbers) {
        if(numbersArgsAreValid(numbers)){
            try {
                filesystem.chmod(file, numbers);
                session.sendResponse(ResponseType.REQUEST_SUCCESSFUL, "CHMOD");
            } catch (FileException e) {
                session.sendResponse(e.getResponse());
            }
        }else {
            session.sendResponse(ResponseType.SYNTAX_ERROR);
        }

    }

    private boolean numbersArgsAreValid(String numbers) {
        return numbers.matches("[0-3]{2}");
    }

    private void abort() {
        session.abortPassiveConnection();
    }

    private void sendFile(String path){
        wasAborted = false;
        if(session.passiveConnectionExist()){
            try {
                final InputStream stream = filesystem.getInputStream(path);
                session.getPassiveConnection().sendData(stream);
                session.sendResponse(ResponseType.PASSIVE_CONNECTION, "binary", path);
            } catch (FileException e) {
                if(!wasAborted)
                    session.sendResponse(e.getResponse());
            }
        }else
            session.sendResponse(ResponseType.CANT_OPEN_DATA_CONNECTION);
    }

    private void addFileToServer(String path, boolean append) {
        wasAborted = false;
        if(session.passiveConnectionExist()) {
            try {
                final OutputStream stream = filesystem.getOutputStream(path, append);
                session.getPassiveConnection().getData(stream);
                session.sendResponse(ResponseType.PASSIVE_CONNECTION, "binary", path);
            } catch (FileException e) {
                if(!wasAborted)
                    session.sendResponse(e.getResponse());
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }else
            session.sendResponse(ResponseType.CANT_OPEN_DATA_CONNECTION);
    }

    private void removeRegularFile(String path) {
        removeCommand(path,false,"DELE");
    }

    private void removeCommand(String path, boolean isDirectory, String commandName) {
        try{
            filesystem.remove(path,isDirectory);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL,commandName);
        }catch (FileException e){
            session.sendResponse(e.getResponse());
        }
    }

    private void removeDirectory(String path) {
        removeCommand(path,true,"RMD");
    }

    private void showCWD(Path currentDirectoryPath) {
        String path = currentDirectoryPath.toString();
        session.sendResponse(ResponseType.CURRENT_DIRECTORY, "/"+ path);
    }

    private void fileList(Session session) {
        if(session.passiveConnectionExist()){
            try {
                String filesOnPath = filesystem.showFilesOnPath(Paths.get(""));
                session.getPassiveConnection().sendFileList(filesOnPath);
                session.sendResponse(ResponseType.PASSIVE_CONNECTION, "ASCII", "/bin/ls");
            }catch (FileException e){
                session.sendResponse(e.getResponse());
            }
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

    private void createDirectory(String path){
        try {
            filesystem.createDirectory(path);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL, "MKD");
        } catch (FileException e) {
            session.sendResponse(e.getResponse());
        }
    }

    public void setPermissionManager(User user) {
        filesystem.setPermissions(user);
    }
}
