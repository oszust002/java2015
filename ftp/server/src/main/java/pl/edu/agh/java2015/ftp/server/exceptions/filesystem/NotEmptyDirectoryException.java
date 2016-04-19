package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Extended {@link FileException} when directory is not empty
 * @author Kamil Osuch
 * @version 1.0
 */
public class NotEmptyDirectoryException extends FileException {
    public NotEmptyDirectoryException(String file) {
        super(ResponseType.DIRECTORY_NOT_EMPTY, file);
    }
}
