package pl.edu.agh.java2015.ftp.server.command;

import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;
import pl.edu.agh.java2015.ftp.server.session.Session;

/**
 * Created by Kamil on 14.01.2016.
 */
public class CommandExecutor {
    private final Session session;

    public CommandExecutor(Session session){
        this.session = session;
    }

    public void executeCommand(Command command){
        if(!command.hasCorrectArgumentsAmount()){
            System.out.println("Error in command: " + command + ", Invalid number of args");
            //session.sendResponse(new Response());
        }
    }
}
