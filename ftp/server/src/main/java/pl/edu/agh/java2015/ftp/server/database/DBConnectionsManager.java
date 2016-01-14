package pl.edu.agh.java2015.ftp.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Kamil on 11.01.2016.
 */
public class DBConnectionsManager {
    private String username;
    private String password;
    private String host;
    private String databaseName;

    //private Connection dbConnection = null;
    private LinkedList<Connection> freeConnections = new LinkedList<Connection>();
    private LinkedList<Connection> usedConnections = new LinkedList<Connection>();
    private static DBConnectionsManager instance = null;


    private static final String CREATE_USERS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `users` (" +
                    "  `id` INT(1) NOT NULL AUTO_INCREMENT," +
                    "  `username` VARCHAR(10) NOT NULL," +
                    "  `password` VARCHAR(45) NOT NULL," +
                    "  `salt` VARCHAR(20) NOT NULL," +
                    "  PRIMARY KEY  (`id`)," +
                    "  UNIQUE KEY `username` (`username`)" +
                    ") ENGINE=MyISAM  DEFAULT CHARSET=utf8";

    private static final String CREATE_GROUPS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `groups` ("+
                    "`id` INT(1) NOT NULL AUTO_INCREMENT," +
                    "  `group_name` VARCHAR(10) NOT NULL," +
                    "  PRIMARY KEY  (`id`)," +
                    "  UNIQUE KEY `group_name` (`group_name`)" +
                    ") ENGINE=MyISAM  DEFAULT CHARSET=utf8;";

    private static final String CREATE_FILES_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `files` (" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `filename` VARCHAR(50) NOT NULL," +
                    "  `owner_id` INT(11) NOT NULL," +
                    "  `group_id` INT(11) NOT NULL," +
                    "  `user_read` tinyint(1) NOT NULL DEFAULT '1'," +
                    "  `user_write` tinyint(1) NOT NULL DEFAULT '1'," +
                    "  `group_read` tinyint(1) NOT NULL DEFAULT '0'," +
                    "  `group_write` tinyint(1) NOT NULL DEFAULT '0'," +
                    "  PRIMARY KEY  (`id`)," +
                    "  UNIQUE KEY `filename` (`filename`)" +
                    ") ENGINE=MyISAM  DEFAULT CHARSET=utf8;";

    private static final String CREATE_USER_GROUP_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `user_group` (" +
                    "  `user_id` INT(11) NOT NULL," +
                    "  `group_id` INT(11) NOT NULL" +
                    ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";



    private DBConnectionsManager(String username, String password, String host, String databaseName) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.databaseName = databaseName;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+host+"/"+databaseName,username,password);
    }

    public Connection createConnection() throws SQLException {
        if(freeConnections.isEmpty()){
            freeConnections.add(connect());
        }
        Connection connection = freeConnections.get(0);
        usedConnections.add(connection);
        return connection;
    }

    public void createTables() throws SQLException {
        Connection dbConnection = connect();
        dbConnection.createStatement().executeUpdate(CREATE_USERS_TABLE_QUERY);
        dbConnection.createStatement().executeUpdate(CREATE_GROUPS_TABLE_QUERY);
        dbConnection.createStatement().executeUpdate(CREATE_FILES_TABLE_QUERY);
        dbConnection.createStatement().executeUpdate(CREATE_USER_GROUP_TABLE_QUERY);
        dbConnection.close();
    }

    public void releaseConncection(Connection connection){
        if(usedConnections.contains(connection)){
            usedConnections.remove(connection);
            freeConnections.add(connection);
        }
    }

    public static DBConnectionsManager createInstance(){
        if(instance == null)
            instance = new DBConnectionsManager("oszust","KZiuB2mV", "mysql.agh.edu.pl","oszust");
        return instance;
    }
}
