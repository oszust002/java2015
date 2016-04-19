package pl.edu.agh.java2015.ftp.server;

import pl.edu.agh.java2015.ftp.server.database.DBConnectionsManager;
import pl.edu.agh.java2015.ftp.server.database.DBFilesManager;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.session.SessionsManager;

/**
 * Created by Kamil on 11.01.2016.
 */
public class Main {
    public static void main(String[] args){
        System.out.println("Starting server");
        DBConnectionsManager connectionsManager = DBConnectionsManager.createInstance();
        DBUserManager userManager = new DBUserManager(connectionsManager);
        SessionsManager sessionsManager = new SessionsManager(
                userManager,
                8,
                new DBFilesManager(
                        connectionsManager,
                        userManager,
                        new DBGroupManager(connectionsManager)));
        sessionsManager.start();

    }
}
