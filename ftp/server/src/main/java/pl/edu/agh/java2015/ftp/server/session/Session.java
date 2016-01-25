package pl.edu.agh.java2015.ftp.server.session;

import pl.edu.agh.java2015.ftp.server.Filesystem;
import pl.edu.agh.java2015.ftp.server.Timeout;
import pl.edu.agh.java2015.ftp.server.command.CommandExecutor;
import pl.edu.agh.java2015.ftp.server.command.PassiveConnection;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.command.Command;
import pl.edu.agh.java2015.ftp.server.command.CommandHandler;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by Kamil on 14.01.2016.
 */
public class Session implements Runnable{
    private final CommandHandler commandHandler;
    private final Timeout timeout;
    private User user = null;
    private Boolean isRunning = true;
    private final CommandExecutor commandExecutor;
    private final DBUserManager userManager;
    private PassiveConnection passiveConnection = null;
    private final ExecutorService executorService;

    public Session(Socket socket, DBUserManager userManager, Filesystem filesystem,
                   ExecutorService executorService) throws IOException {
        this.commandHandler = new CommandHandler(socket);
        commandExecutor = new CommandExecutor(this, filesystem);
        timeout = new Timeout(this, 60);
        commandHandler.sendResponse(new Response(ResponseType.HELLO));
        this.userManager = userManager;
        this.executorService = executorService;

    }

    @Override
    public void run() {
        try{
            while (isRunning){
                Command command = commandHandler.parseCommand();
                if(command != null) {
                    commandExecutor.executeCommand(command);
                    timeout.reset();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Closing session..");
            if(commandHandler.isSocketRunning())
                commandHandler.closeSocket();
            System.out.println("Session closed successfully");
        }
    }

    public void quit(){
        isRunning = false;
    }

    public void sendResponse(Response response){
        commandHandler.sendResponse(response);
    }

    public void sendResponse(ResponseType responseType, Object... arguments){
        commandHandler.sendResponse(new Response(responseType,arguments));
    }

    public boolean isAuthenticated() {
        return user != null && user.getPassword() != null;
    }

    public void disconnect(){
        sendResponse(ResponseType.BYE);
        isRunning = false;
        commandHandler.closeSocket();
    }

    public void setUsername(String username){
        if(user != null) {
            sendResponse(ResponseType.BAD_SEQUENCE_OF_COMMANDS);
            return;
        }
        if(userManager.userExist(username)) {
            user = new User(username);
            sendResponse(ResponseType.PASSWORD_REQUIRED);
        }
        else
            sendResponse(ResponseType.INVALID_USER_OR_PASSWORD);
    }

    public void checkPassword(String password) {
        if(!isAuthenticated()){
            boolean authenticate = userManager.authenticateUser(user,password);
            if(authenticate) {
                commandExecutor.setPermissionManager(user);
                sendResponse(ResponseType.USER_LOGGED_IN);
            }
            else
                sendResponse(ResponseType.INVALID_USER_OR_PASSWORD);
        }else
            sendResponse(ResponseType.BAD_SEQUENCE_OF_COMMANDS);
    }

    public void createPassiveConnection() {
            passiveConnection = new PassiveConnection(this,executorService);
            int port = passiveConnection.getPort();
            sendResponse(ResponseType.ENTERING_PASSIVE_MODE, 127, 0, 0, 1, port / 256, port % 256);
    }

    public PassiveConnection getPassiveConnection(){
        return passiveConnection;
    }

    public boolean passiveConnectionExist(){
        return passiveConnection != null;
    }
}
