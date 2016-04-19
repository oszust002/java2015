package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.File;

/**
 * Extended {@link FileException} when file is not regular file
 * @author Kamil Osuch
 * @version 1.0
 */
public class NotFileException extends FileException{
    public NotFileException(String file) {
        super(ResponseType.NOT_REGULAR_FILE, file);
    }
}
