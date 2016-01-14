package pl.edu.agh.java2015.ftp.server.session;

import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.command.Command;
import pl.edu.agh.java2015.ftp.server.command.CommandHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Kamil on 14.01.2016.
 */
public class Session implements Runnable{
    private final CommandHandler commandHandler;
    private final User user = null;
    private Boolean isRunning = true;

    public Session(Socket socket) throws IOException {
        this.commandHandler = new CommandHandler(socket);
    }


    @Override
    public void run() {
        try{
            while (isRunning){
                Command command = commandHandler.parseCommand();
                //FIXME: create class to process commands;
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
}
