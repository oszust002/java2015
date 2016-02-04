package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Extended {@link FileException} when File already exists in FTP
 * @author Kamil Osuch
 * @version 1.0
 */
public class FileAlreadyExistsException extends FileException {

    public FileAlreadyExistsException(String path) {
        super(ResponseType.FILE_ALREADY_EXIST, path);
    }
}
