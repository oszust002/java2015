package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Class that manages connections with database
 * @author Kamil Osuch
 * @version 1.0
 */
public class DBConnectionsManager {
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

    /**
     * Creates instance of connection with the database
     * @return Connection to database
     * @throws SQLException when connection will not success
     */
    public Connection connect() throws SQLException {
        ServerConfig config = ServerConfig.getInstance();
        return DriverManager.getConnection(config.getUrl(),config.getLogin(),config.getPassword());
    }

    /**
     * Creates connection, adds it to reserved connections
     * @return Connection to database
     * @throws SQLException when connection will not success
     */
    public Connection createConnection() throws SQLException {
        if(freeConnections.isEmpty()){
            freeConnections.add(connect());
        }
        Connection connection = freeConnections.get(0);
        usedConnections.add(connection);
        return connection;
    }

    /**
     * create tables in database if they don't exist
     * @throws SQLException when connection will not success
     */
    public void createTables() {
        Connection dbConnection = null;
        try {
            dbConnection = connect();
            dbConnection.createStatement().executeUpdate(CREATE_USERS_TABLE_QUERY);
            dbConnection.createStatement().executeUpdate(CREATE_GROUPS_TABLE_QUERY);
            dbConnection.createStatement().executeUpdate(CREATE_FILES_TABLE_QUERY);
            dbConnection.createStatement().executeUpdate(CREATE_USER_GROUP_TABLE_QUERY);
            dbConnection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Releases connection with database, which can be used in the future
     * @param connection connection that is released
     */
    public void releaseConncection(Connection connection){
        if(usedConnections.contains(connection)){
            usedConnections.remove(connection);
            freeConnections.add(connection);
        }
    }

    /**
     * Creates instance of {@link DBConnectionsManager}
     * @return {@link DBConnectionsManager} instance
     */
    public static DBConnectionsManager createInstance(){
        if(instance == null)
            instance = new DBConnectionsManager();
        return instance;
    }

    /**
     * Tests if database with specified info exists
     * @param url url of JDBC SQL address
     * @param login login to database
     * @param password password to database
     * @return true if database exists, false otherwise
     */
    public boolean testConnection(String url, String login, String password) {
        try {
            Connection connection = DriverManager.getConnection(url,login,password);
            connection.close();
            return true;
        } catch (SQLException ignore) {

        }
        return false;
    }
}
