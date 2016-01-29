package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.File;

/**
 * Created by Kamil on 29.01.2016.
 */
public class NotFileException extends FileException{
    public NotFileException(String file) {
        super(ResponseType.NOT_REGULAR_FILE, file);
    }
}
