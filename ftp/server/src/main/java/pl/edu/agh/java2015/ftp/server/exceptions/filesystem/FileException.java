package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.IOException;

/**
 * Exception class when something with files or database file record has happened
 * @author Kamil Osuch
 * @version 1.0
 */
public class FileException extends Exception{
    private final Response response;

    public FileException(ResponseType responseType, String file){
        response = new Response(responseType,file);
    }

    public FileException(IOException e) {
        super(e);
        response = new Response(ResponseType.ACTION_ABORTED_LOCAL);
    }

    /**
     * Gets response of the specified exception
     * @return {@link Response} for the specified exception
     */
    public Response getResponse(){
        return response;
    }
}
