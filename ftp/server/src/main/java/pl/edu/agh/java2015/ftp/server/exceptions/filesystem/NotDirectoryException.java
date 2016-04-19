package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Extended {@link FileException} when File is not regular directory
 * @author Kamil Osuch
 * @version 1.0
 */
public class NotDirectoryException extends FileException{

    public NotDirectoryException(String path) {
        super(ResponseType.NOT_REGULAR_DIRECTORY, path);
    }
}
