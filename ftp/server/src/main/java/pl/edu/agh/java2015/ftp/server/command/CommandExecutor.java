package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * One of the main classes in application. Takes all commands provided by the client and starts action
 * specified for specific {@link CommandType}
 * @author Kamil Osuch
 * @version 1.0
 */
public class CommandExecutor {
    private final Session session;
    private final Filesystem filesystem;
    private boolean wasAborted = false;

    /**
     * Creates {@link CommandExecutor} for the specified {@link Session} of client, and {@link Filesystem}
     * specified that client
     * @param session {@link Session} for the specific client
     * @param filesystem {@link Filesystem} object for the specified client
     */
    public CommandExecutor(Session session, Filesystem filesystem){
        this.session = session;
        this.filesystem = filesystem;
    }

    /**
     * Takes command provided by the client and starts action specified for specific {@link CommandType},
     * and sends {@link pl.edu.agh.java2015.ftp.server.response.Response} to client
     * @param command {@link Command} provided by the client
     */
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
                fileList();
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

    /**
     * Changes file permissions to those which where given and sends response
     * @param file Path(or filename) to file in FTP filesystem
     * @param numbers Specified permissions in format: XX, where first number is owner permissions,
     *                second is group permissions(0-nothing, 1-read, 2-write, 3-read & write)
     */
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

    /**
     * Checks if numbers for changing file permissions are in correct format
     * @param numbers String that has to be checked
     * @return True if matches pattern, false otherwise
     */
    private boolean numbersArgsAreValid(String numbers) {
        return numbers.matches("[0-3]{2}");
    }

    /**
     * Aborts session with a client
     */
    private void abort() {
        session.abortPassiveConnection();
    }

    /**
     * Main function that starts action of sending file to client(RETR command)
     * @param path Path to file which will be sent
     */
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

    /**
     * Main function that starts action of adding file to the server(STOR and APPE commands)
     * @param path Path to file which will be stored on the server
     * @param append True if will append to file, false if overwrite it
     */
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

    /**
     * Main function that starts action of deleting a regular file from the server(DELE command)
     * @param path Path to file which will be deleted
     */
    private void removeRegularFile(String path) {
        removeCommand(path,false,"DELE");
    }

    /**
     * Command that starts action of deleting file or directory(DELE or RMD command)
     * @param path path to file or directory which will be deleted
     * @param isDirectory true if directory, false otherwise
     * @param commandName Name of specific delete command type
     * @see ResponseType
     */
    private void removeCommand(String path, boolean isDirectory, String commandName) {
        try{
            filesystem.remove(path,isDirectory);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL,commandName);
        }catch (FileException e){
            session.sendResponse(e.getResponse());
        }
    }

    /**
     * Main function that starts action of deleting a directory(RMD command)
     * @param path path to directory which will be deleted
     */
    private void removeDirectory(String path) {
        removeCommand(path,true,"RMD");
    }

    /**
     * Main function that starts action of showing current working directory(PWD command)
     * @param currentDirectoryPath current working directory path
     */
    private void showCWD(Path currentDirectoryPath) {
        String path = currentDirectoryPath.toString();
        session.sendResponse(ResponseType.CURRENT_DIRECTORY, "/"+ path);
    }

    /**
     * Main function that starts action of listing all files and directories in current working directory
     * (LIST command)
     */
    private void fileList() {
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

    /**
     * Main function that changes current working directory to specified(CWD command)
     * @param path path to new current working directory
     */
    private void changeDirectory(String path){
        try {
            filesystem.changeDirectory(path);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL, "CWD");
        }catch (FileException e){
            session.sendResponse(e.getResponse());
        }
    }

    /**
     * Main function that creates new directory current working directory or on speicifed path(MKD command)
     * @param path Path to new directory
     */
    private void createDirectory(String path){
        try {
            filesystem.createDirectory(path);
            session.sendResponse(ResponseType.REQUEST_SUCCESSFUL, "MKD");
        } catch (FileException e) {
            session.sendResponse(e.getResponse());
        }
    }

    /**
     * Main function that sets the permission manager in {@link Filesystem} for specified user
     * @param user Specified user, which will be checked in all file operations, if is it owner
     */
    public void setPermissionManager(User user) {
        filesystem.setPermissions(user);
    }
}
