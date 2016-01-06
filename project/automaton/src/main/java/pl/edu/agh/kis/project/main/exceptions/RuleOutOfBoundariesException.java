package pl.edu.agh.kis.project.main.exceptions;

/**
 * Created by Kamil on 06.01.2016.
 */
public class RuleOutOfBoundariesException extends IllegalArgumentException {
    public RuleOutOfBoundariesException(){
        super();
    }

    public RuleOutOfBoundariesException(String message){
        super(message);
    }
}
