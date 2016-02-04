package pl.edu.agh.java2015.ftp.server.response;

import java.util.Arrays;

/**
 * Class which represents every response which is sent as answer to {@link pl.edu.agh.java2015.ftp.server.command.Command}
 * @author Kamil Osuch
 * @version 1.0
 */
public class Response {
    private final ResponseType type;
    private final Object[] arguments;

    /**
     * Creates new response with specified {@link ResponseType} and arguments
     * @param type {@link ResponseType}
     * @param arguments arguments specified for response
     */
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

    /**
     * returns String with response sended to client
     * @return response message for client
     */
    public String toSendString(){
        return String.valueOf(type.getCode()) + " " + String.format(type.getMessage(), arguments) +"\r\n";
    }
}
