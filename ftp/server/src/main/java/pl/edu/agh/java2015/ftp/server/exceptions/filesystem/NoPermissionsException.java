package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Extended {@link FileException} when permissions to file are denied
 * @author Kamil Osuch
 * @version 1.0
 */
public class NoPermissionsException extends FileException {
    public NoPermissionsException(String path) {
        super(ResponseType.FILE_NO_ACCESS, path);
    }
}
