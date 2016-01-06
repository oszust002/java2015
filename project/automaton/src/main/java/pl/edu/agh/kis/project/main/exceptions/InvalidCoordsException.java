package pl.edu.agh.kis.project.main.exceptions;

/**
 * Created by Kamil on 06.01.2016.
 */
public class InvalidCoordsException extends IllegalArgumentException {
    public InvalidCoordsException(){
        super();
    }

    public InvalidCoordsException(String message){
        super(message);
    }
}
