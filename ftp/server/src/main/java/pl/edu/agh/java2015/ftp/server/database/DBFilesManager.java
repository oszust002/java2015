package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that manages files database
 * @author Kamil Osuch
 * @version 1.0
 */
public class DBFilesManager {

    private final DBConnectionsManager connectionsManager;
    private final DBUserManager userManager;
    private final DBGroupManager groupManager;

    private static final String CHECK_FILE_QUERY = "SELECT 1 FROM files WHERE filename = ?";
    private static final String ADD_FILE_QUERY = "INSERT INTO files (filename, owner_id, group_id) VALUES(?,?,?)";
    private static final String GET_FILE_QUERY = "SELECT * FROM files WHERE filename = ?";
    private static final String DELETE_FILE_WHERE_FILENAME = "DELETE FROM files WHERE filename=?";
    private static final String UPDATE_FILE_PERMS_QUERY =
            "UPDATE files SET user_read=?, user_write=?, group_read=?," +
            " group_write=? WHERE filename=?";

    /**
     * Creates database file manager with specified {@link DBConnectionsManager}, {@link DBGroupManager},
     * {@link DBUserManager}
     * @param connectionsManager specified connection manager
     * @param userManager specified user manager
     * @param groupManager specified group manager
     */
    public DBFilesManager(DBConnectionsManager connectionsManager, DBUserManager userManager,
                          DBGroupManager groupManager) {
        this.connectionsManager = connectionsManager;
        this.userManager = userManager;
        this.groupManager = groupManager;
    }

    /**
     * Checks if file exists in database
     * @param pathToFile path to file to check if exists
     * @return true if file exists, false otherwise
     */
    public boolean fileExists(String pathToFile){
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            String normalized = pathToFile.replace("/","\\");
            PreparedStatement statement = connection.prepareStatement(CHECK_FILE_QUERY);
            statement.setString(1,normalized);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Gets {@link DatabaseFileRecord} from database, with all info about file
     * @param pathToFile path to file which will be taken
     * @return {@link DatabaseFileRecord} from given path
     */
    public DatabaseFileRecord getFile(String pathToFile){
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            String normalized = pathToFile.replace("/", "\\");
            PreparedStatement statement = connection.prepareStatement(GET_FILE_QUERY);
            statement.setString(1,normalized);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
                return null;
            User user = userManager.findUserById(resultSet.getInt("owner_id"));
            Group group = groupManager.findGroupById(resultSet.getInt("group_id"));
            return new DatabaseFileRecord(resultSet.getInt("id"),normalized,user,group,
                    resultSet.getBoolean("user_read"), resultSet.getBoolean("user_write"),
                    resultSet.getBoolean("group_read"),resultSet.getBoolean("group_write"));
        }catch (SQLException e){
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Add files or directory to the files database
     * @param path path to the file
     * @param user Owner of the file
     */
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

    /**
     * Checks if user is in group that can manage file
     * @param user {@link User} to check
     * @param file {@link DatabaseFileRecord} file with information about file
     * @return true if user is in group, false otherwise
     */
    public boolean isUserInFileGroup(User user,DatabaseFileRecord file){
        return groupManager.isUserInGroup(user.getId(),file.getGroup().getId());
    }

    /**
     * Removes file from database
     * @param path path to file which will be removed
     */
    public void removeFileByName(Path path) {
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            String normalized = path.toString().replace("/","\\");
            PreparedStatement statement = connection.prepareStatement(DELETE_FILE_WHERE_FILENAME);
            statement.setString(1,normalized);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Sets permissions to file
     * @param newPath path to a file
     * @param perms Integer array of new permissions
     */
    public void setFilePerms(Path newPath, int[] perms) {
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            String normalized = newPath.toString().replace("/","\\");
            PreparedStatement statement = connection.prepareStatement(UPDATE_FILE_PERMS_QUERY);
            statement.setInt(1,perms[0]);
            statement.setInt(2,perms[1]);
            statement.setInt(3,perms[2]);
            statement.setInt(4,perms[3]);;
            statement.setString(5,normalized);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }
}
