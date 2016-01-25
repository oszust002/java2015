package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kamil on 24.01.2016.
 */
public class DBFilesManager {

    private final DBConnectionsManager connectionsManager;
    private final DBUserManager userManager;
    private final DBGroupManager groupManager;

    private static final String GET_FILE_QUERY = "SELECT 1 FROM files WHERE filename = ?";
    private static final String ADD_FILE_QUERY = "INSERT INTO files (filename, owner_id, group_id) VALUES(?,?,?)";

    public DBFilesManager(DBConnectionsManager connectionsManager, DBUserManager userManager,
                          DBGroupManager groupManager) {
        this.connectionsManager = connectionsManager;
        this.userManager = userManager;
        this.groupManager = groupManager;
    }

    public boolean fileExists(String pathToFile){
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            String normalized = pathToFile.replace("/","\\");
            PreparedStatement statement = connection.prepareStatement(GET_FILE_QUERY);
            statement.setString(1,normalized);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    public void addFileOrDirectory(Path path, User user){
        Connection connection = null;
        try{
            connection = connectionsManager.createConnection();
            String normalized = path.toString().replace("/","\\");
            PreparedStatement statement = connection.prepareStatement(ADD_FILE_QUERY);
            statement.setString(1,normalized);
            statement.setInt(2,user.getId());
            statement.setInt(3,groupManager.getUserGroupId(user));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

}
