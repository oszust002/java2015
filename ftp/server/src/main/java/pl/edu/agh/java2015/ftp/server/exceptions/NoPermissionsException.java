package pl.edu.agh.java2015.ftp.server.exceptions;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 29.01.2016.
 */
public class NoPermissionsException extends FileException {
    public NoPermissionsException(String path) {
        super(ResponseType.FILE_NO_ACCESS, path);
    }
}
