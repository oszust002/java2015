package pl.edu.agh.java2015.ftp.server.exceptions;

import pl.edu.agh.java2015.ftp.server.exceptions.filesystem.FileException;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 26.01.2016.
 */
public class FileAlreadyExistsException extends FileException {


    public FileAlreadyExistsException(String path) {
        super(ResponseType.FILE_ALREADY_EXIST, path);
    }
}
