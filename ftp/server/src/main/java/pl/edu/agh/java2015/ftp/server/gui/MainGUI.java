package pl.edu.agh.java2015.ftp.server.gui;

import pl.edu.agh.java2015.ftp.server.database.DBConnectionsManager;
import pl.edu.agh.java2015.ftp.server.database.DBFilesManager;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.session.SessionsManager;

import javax.swing.*;

/**
 * Created by Kamil on 03.02.2016.
 */
public class MainGUI {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                DBConnectionsManager connectionsManager = DBConnectionsManager.createInstance();
                connectionsManager.createTables();
                DBUserManager userManager = new DBUserManager(connectionsManager);
                DBGroupManager groupManager = new DBGroupManager(connectionsManager);
                SessionsManager sessionsManager = new SessionsManager(
                        userManager,
                        8,
                        new DBFilesManager(
                                connectionsManager,
                                userManager,
                                groupManager));
                MainFrame frame = new MainFrame(sessionsManager, userManager, groupManager,connectionsManager);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
