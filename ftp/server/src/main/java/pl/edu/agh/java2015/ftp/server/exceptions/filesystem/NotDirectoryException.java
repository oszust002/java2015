package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 25.01.2016.
 */
public class NotDirectoryException extends FileException{

    public NotDirectoryException(String path) {
        super(ResponseType.NOT_REGULAR_DIRECTORY, path);
    }
}
