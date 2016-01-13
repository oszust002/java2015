package pl.edu.agh.java2015.ftp.server;

import pl.edu.agh.java2015.ftp.server.database.DBConnectionsManager;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.sql.SQLException;

/**
 * Created by Kamil on 11.01.2016.
 */
public class Main {
    public static void main(String[] args){
        DBConnectionsManager connectionsManager = DBConnectionsManager.createInstance();
        try {
            connectionsManager.createTables();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
