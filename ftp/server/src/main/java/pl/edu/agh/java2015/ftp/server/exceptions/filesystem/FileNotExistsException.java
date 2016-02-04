package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Extended {@link FileException} when File not exists in FTP
 * @author Kamil Osuch
 * @version 1.0
 */
public class FileNotExistsException extends FileException {

    public FileNotExistsException(String path) {
        super(ResponseType.FILE_OR_DIR_NOT_FOUND, path);
    }
}
