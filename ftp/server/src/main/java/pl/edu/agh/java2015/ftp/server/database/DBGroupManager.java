package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kamil on 13.01.2016.
 */
public class DBGroupManager {
    private static final String IS_USER_IN_GROUP_QUERY=
            "SELECT * FROM user_group WHERE user_id = ? AND group_id = ?";

    private static final String SELECT_FROM_GROUPS_WHERE_ID = "SELECT * FROM groups WHERE id = ?";

    private final DBConnectionsManager connectionsManager;

    public DBGroupManager(DBConnectionsManager connectionsManager){
        this.connectionsManager = connectionsManager;
    }

    public boolean isUserInGroup(User user, Group group){
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(IS_USER_IN_GROUP_QUERY);
            statement.setInt(1,user.getId());
            statement.setInt(2,user.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            connectionsManager.releaseConncection(connection);
        }
    }

    public Group findGroupById(int id){
        Connection connection = null;
        try {
            connection = connectionsManager.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_GROUPS_WHERE_ID);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
                return null;
            String groupname = resultSet.getString("group_name");
            return new Group(id,groupname);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


}