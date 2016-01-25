package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 25.01.2016.
 */
public class FileNotExistsException extends FileException {

    public FileNotExistsException(String path) {
        super(ResponseType.FILE_OR_DIR_NOT_FOUND, path);
    }
}
