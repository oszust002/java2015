package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.UserGroup;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to manage groups and user_group databases
 * @author Kamil Osuch
 * @version 1.0
 */
public class DBGroupManager {
    private static final String IS_USER_IN_GROUP_QUERY =
            "SELECT * FROM user_group WHERE user_id = ? AND group_id = ?";

    private static final String SELECT_FROM_GROUPS_WHERE_ID = "SELECT * FROM groups WHERE id = ?";
    private static final String SELECT_GROUP_FROM_USER_GROUP_WHERE_USER_ID =
            "SELECT group_id FROM user_group WHERE user_id = ?";

    private static final String SELECT_FROM_GROUPS = "SELECT * FROM groups WHERE group_name=?";

    private static final String GET_ALL_FROM_GROUPS = "SELECT * FROM groups";
    private static final String GET_ALL_FROM_USERGROUP = "SELECT * FROM user_group";
    private static final String INSERT_TO_GROUPS = "INSERT INTO groups (group_name) VALUES(?)";
    private static final String INSERT_USER_TO_GROUP = "INSERT INTO user_group (user_id, group_id) VALUES(?,?)";
    private static final String DELETE_FROM_USER_GROUP_WHERE_USER_ID = "DELETE FROM user_group WHERE user_id=?";
    private static final String DELETE_FROM_GROUPS_WHERE_ID = "DELETE FROM groups WHERE id=?";
    private static final String DELETE_FROM_USER_GROUP_WHERE_GROUP_ID = "DELETE FROM user_group WHERE user_id=?";
    private static final String DELETE_FROM_USER_GROUP_WHERE_USER_ID_GROUP_ID =
            "DELETE FROM user_group WHERE user_id=? AND group_id=?";

    private final DBConnectionsManager connectionsManager;

    /**
     * Creates {@link DBGroupManager} with specified {@link DBConnectionsManager}
     * @param connectionsManager database connection manager
     */
    public DBGroupManager(DBConnectionsManager connectionsManager) {
        this.connectionsManager = connectionsManager;
    }

    /**
     * Gets all {@link UserGroup} pairs from the user_group database
     * @return List of {@link UserGroup} pairs
     */
    public List<UserGroup> getUserGroup() {
        Connection connection = null;
        List<UserGroup> userGroups = new LinkedList<>();
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_FROM_USERGROUP);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                userGroups.add(new UserGroup(resultSet.getInt("user_id"), resultSet.getInt("group_id")));

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return userGroups;
    }

    /**
     * Gets all {@link Group} from the groups database
     * @return List of all {@link Group} in database
     */
    public List<Group> getGroups() {
        Connection connection = null;
        List<Group> groups = new LinkedList<>();
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_FROM_GROUPS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                groups.add(new Group(resultSet.getInt("id"), resultSet.getString("group_name")));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
        return groups;
    }

    /**
     * Checks if user is in group
     * @param userID ID of a specified {@link User}
     * @param groupID ID of a specified {@link Group}
     * @return true if user is in group, false otherwise
     */
    public boolean isUserInGroup(Integer userID, Integer groupID) {
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(IS_USER_IN_GROUP_QUERY);
            statement.setInt(1, userID);
            statement.setInt(2, groupID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Gets group with a specific ID;
     * @param id ID of a group
     * @return null if not exists, {@link Group} with specified ID otherwise
     */
    public Group findGroupById(int id) {
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_GROUPS_WHERE_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            String groupname = resultSet.getString("group_name");
            return new Group(id, groupname);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Gets users groupID
     * @param user user which belongs to the group
     * @return ID of a {@link Group} where user exists
     */
    public int getUserGroupId(User user) {
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_GROUP_FROM_USER_GROUP_WHERE_USER_ID);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("group_id");
            else
                throw new DatabaseException("User " + user.getUsername() + " is not in any group(should have " +
                        "been in at least one)");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Creates group in database
     * @param groupname name of group which will be added
     * @return index of added group
     */
    public int createGroup(String groupname) {
        if (groupExist(groupname))
            return -1;
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_TO_GROUPS,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, groupname);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else
                throw new DatabaseException("Failed to create group: " + groupname);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Checks if group exists in database
     * @param groupname name of the group to check
     * @return true if group exists, false otherwise
     */
    private boolean groupExist(String groupname) {
        if (groupname == null)
            return false;
        Connection connection = null;
        //noinspection Duplicates
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_GROUPS);
            statement.setString(1, groupname);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Add user to specified group
     * @param userIndex index of user
     * @param groupIndex index of group
     */
    public void addUserToGroup(int userIndex, int groupIndex) {
        Connection connection = null;
        //noinspection Duplicates
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_TO_GROUP);
            statement.setInt(1, userIndex);
            statement.setInt(2, groupIndex);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Deletes all rows where user is from user_groups
     * @param index user index
     */
    public void deleteUserFromUserGroups(Integer index) {
        delete(index, DELETE_FROM_USER_GROUP_WHERE_USER_ID);
    }

    /**
     * Delete all rows where group is from user_groups
     * @param index group index
     */
    public void deleteGroupFromUserGroup(Integer index) {
        delete(index, DELETE_FROM_USER_GROUP_WHERE_GROUP_ID);
    }

    /**
     * Deletes rows from user_groups
     * @param Index index of user or group
     * @param statement specified statement
     */
    private void delete(Integer index, String statement) {
        Connection connection = null;
        //noinspection Duplicates
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, index);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    /**
     * Deletes group from groups with specified group index
     * @param groupIndex index of a group to delete
     */
    public void deleteGroup(Integer groupIndex) {
        delete(groupIndex, DELETE_FROM_GROUPS_WHERE_ID);
    }

    /**
     * Delete {@link UserGroup} with specified user ID and group ID
     * @param uID ID of {@link User}
     * @param gID ID of {@link Group}
     */
    public void deleteUserGroup(Integer uID, Integer gID) {
        Connection connection = null;
        //noinspection Duplicates
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_FROM_USER_GROUP_WHERE_USER_ID_GROUP_ID);
            preparedStatement.setInt(1, uID);
            preparedStatement.setInt(2, gID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }
}
