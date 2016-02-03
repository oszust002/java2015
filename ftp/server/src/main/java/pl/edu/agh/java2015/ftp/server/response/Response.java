package pl.edu.agh.java2015.ftp.server.response;

import java.util.Arrays;

/**
 * Created by Kamil on 14.01.2016.
 */
public class Response {
    private final ResponseType type;
    private final Object[] arguments;

    public Response(ResponseType type, Object... arguments){
        this.type = type;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    public String toSendString(){
        return String.valueOf(type.getCode()) + " " + String.format(type.getMessage(), arguments) +"\r\n";
    }
}
