package pl.edu.agh.java2015.ftp.server.exceptions;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 29.01.2016.
 */
public class NotEmptyDirectoryException extends FileException {
    public NotEmptyDirectoryException(String file) {
        super(ResponseType.DIRECTORY_NOT_EMPTY, file);
    }
}
