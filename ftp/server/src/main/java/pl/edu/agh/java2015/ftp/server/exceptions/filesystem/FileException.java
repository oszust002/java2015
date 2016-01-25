package pl.edu.agh.java2015.ftp.server.exceptions.filesystem;

import pl.edu.agh.java2015.ftp.server.response.Response;
import pl.edu.agh.java2015.ftp.server.response.ResponseType;

/**
 * Created by Kamil on 25.01.2016.
 */
public class FileException extends Exception{
    private final Response response;

    public FileException(ResponseType responseType, String file){
        response = new Response(responseType,file);
    }

    public Response getResponse(){
        return response;
    }
}
