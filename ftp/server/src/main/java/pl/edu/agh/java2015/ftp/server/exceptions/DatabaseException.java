package pl.edu.agh.java2015.ftp.server.exceptions;

import java.sql.SQLException;

/**
 * Exception thrown when problem with database shows up
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(SQLException e){
        super(e);
    }
}
