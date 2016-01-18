package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

import java.io.IOException;

/**
 * Created by Kamil on 15.01.2016.
 */
public class FilesystemException extends RuntimeException{
    private final Response response;

    public FilesystemException(){
        this.response = new Response(ResponseType.ACTION_ABORTED_LOCAL);
    }

    public FilesystemException(IOException e){
        super(e);
        this.response =  new Response(ResponseType.ACTION_ABORTED_LOCAL);
    }

    public Response getResponse() {
        return response;
    }
}
