package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.IOException;

/**
 * Created by Kamil on 25.01.2016.
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

    public Response getResponse(){
        return response;
    }
}
