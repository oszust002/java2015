package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.exceptions.DatabaseException;

import javax.xml.crypto.Data;
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
    private static final String CREATE_USER_QUERY = "INSERT INTO users (username, password, salt) VALUES (?,?,?)";

    public DBUserManager(DBConnectionsManager connections) {
        this.connections = connections;
    }

    public boolean authenticateUser(User user, String password){
        if(user == null || user.getPassword() != null)
            throw new IllegalArgumentException("User not exist or is already authenticated");
        Connection connection = null;
        try {
            connection = connections.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_QUERY);
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return false;
            String salt, hash;
            salt = resultSet.getString("salt");
            hash = generateHash(password, salt);
            Integer id = resultSet.getInt("id");
            if (!hash.equals(resultSet.getString("password"))) {
                System.out.println("Wrong password for user: " + user + ", actual: " + hash +
                        ", should be: "+resultSet.getString("password"));
                return false;
            }
            user.setPassword(hash);
            user.setSalt(salt);
            user.setId(id);
            return true;
        } catch (SQLException e) {
            throw new DatabaseException(e);
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

    private String generateHash(String password, String salt){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(salt.getBytes());
            byte[] bytes = md5.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte oneByte : bytes)
                builder.append(Integer.toString((oneByte & 0xff)+0x100,16).substring(1));
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int createUser(String username, String password, String salt){
        if(userExist(username))
            return -1;
        Connection connection = null;
        try {
            connection = connections.createConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            String hashedPassword = generateHash(password, salt);
            statement.setString(1, username);
            statement.setString(2,hashedPassword);
            statement.setString(3,salt);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                return resultSet.getInt(1);
            else
                throw new DatabaseException("Failed to create user: " + username);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }finally {
            connections.releaseConncection(connection);
        }
    }

    public boolean userExist(String username){
        if(username == null)
            return false;
        Connection connection = null;
        try {
            connection = connections.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_QUERY);
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }finally {
            connections.releaseConncection(connection);
        }
    }
}
