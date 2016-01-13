package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Created by Kamil on 11.01.2016.
 */
public class DBUserManager {
    private final DBConnectionsManager connections;

    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    public DBUserManager(DBConnectionsManager connections) {
        this.connections = connections;
    }

    public User authenticateUser(String username, String password) throws SQLException {
        Connection connection = null;
        try {
        connection = connections.createConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_USER_QUERY);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            String salt, hash;
            salt = resultSet.getString("salt");
            hash = generateHash(password, salt);
            Integer id = resultSet.getInt("id");
            if (!hash.equals(resultSet.getString("password")))
                return null;
            return new User(username, password, salt, id);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }finally {
            connections.releaseConncection(connection);
        }
    }

    public User findUserById(Integer id){
        Connection connection = null;
        try {
            connection = connections.createConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID);
            ps.setInt(1,id);

            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                return null;
            return new User(resultSet.getString("username"),resultSet.getString("password"),
                    resultSet.getString("salt"),id);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }finally {
            connections.releaseConncection(connection);
        }
    }

    public User findUserByUsername(String username){
        Connection connection = null;
        try {
            connection = connections.createConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_QUERY);
            ps.setString(1,username);

            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                return null;
            return new User(username,resultSet.getString("password"),
                    resultSet.getString("salt"),resultSet.getInt("id"));

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }finally {
            connections.releaseConncection(connection);
        }
    }

    public String generateHash(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD-5");
        md5.update(salt.getBytes());
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte oneByte : bytes)
            builder.append(Integer.toString((oneByte & 0xff)+0x100,16).substring(1));
        return builder.toString();
    }
}
