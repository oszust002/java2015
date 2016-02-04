package pl.edu.agh.java2015.ftp.server.gui;

import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.gui.tables.GroupsTable;
import pl.edu.agh.java2015.ftp.server.gui.tables.UserGroupTable;
import pl.edu.agh.java2015.ftp.server.gui.tables.UsersTable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 03.02.2016.
 */
public class TabbedPane extends JPanel{
    UsersTable usersTable;
    GroupsTable groupsTable;
    UserGroupTable userGroupTable;
    JFrame parent;

    public TabbedPane(DBUserManager userManager, DBGroupManager groupManager, JFrame parent){
        super(new GridLayout(1,1));
        this.parent = parent;
        usersTable = new UsersTable(userManager);
        groupsTable = new GroupsTable(groupManager);
        userGroupTable = new UserGroupTable(userManager,groupManager);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Users",usersTable.getPanel());
        tabbedPane.addTab("Groups",groupsTable.getPanel());
        tabbedPane.addTab("User-Group",userGroupTable.getPanel());
        add(tabbedPane);
    }
}
