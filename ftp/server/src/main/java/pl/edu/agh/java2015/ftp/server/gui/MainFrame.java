package pl.edu.agh.java2015.ftp.server.gui;

import pl.edu.agh.java2015.ftp.server.database.DBConnectionsManager;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.session.SessionsManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 03.02.2016.
 */
public class MainFrame extends JFrame{

    public MainFrame(SessionsManager manager, DBUserManager userManager, DBGroupManager groupManager,
                     DBConnectionsManager connectionsManager){
        this.setSize(700,600);
        this.setMinimumSize(new Dimension(750,600));
        this.setLayout(new BorderLayout(5,5));
        DatabaseOptions optionsPanel = new DatabaseOptions(this, manager, connectionsManager);
        this.add(optionsPanel.getPanel(),BorderLayout.NORTH);
        TabbedPane pane = new TabbedPane(userManager, groupManager, this);
        this.add(pane);
    }
}
