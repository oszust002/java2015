package pl.edu.agh.java2015.ftp.server.exceptions;

import java.sql.SQLException;

/**
 * Created by Kamil on 13.01.2016.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(SQLException e){
        super(e);
    }
}
