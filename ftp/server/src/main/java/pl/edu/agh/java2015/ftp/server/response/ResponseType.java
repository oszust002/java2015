package pl.edu.agh.java2015.ftp.server.response;

/**
 * Enum class that holds all response type which are implemented
 * @author Kamil Osuch
 * @version 1.0
 */
public enum ResponseType {
    PASSWORD_REQUIRED(          331, "Password required", 0),
    BAD_SEQUENCE_OF_COMMANDS(   503, "Bad sequence of commands", 0),
    USER_LOGGED_IN(             230, "User logged in", 0),
    BYE(                        221, "Bye", 0),
    COMMAND_SUCCESSFUL(         200, "Command successful",0),
    ENTERING_PASSIVE_MODE(      227, "Entering passive mode (%d,%d,%d,%d,%d,%d)",6),
    TRANSFER_COMPLETE(          150, "Transfer complete", 0),
    REQUEST_SUCCESSFUL(         250, "%s was succesful",1),
    CURRENT_DIRECTORY(          257, "\"%s\" is current directory", 1),
    HELLO(                      220, "Hello", 0),
    PASSIVE_CONNECTION(         150, "Opening %s mode data connection for '%s'",2),
    COMMAND_NOT_IMPLEMENTED(    502, "Command not implemented", 0),
    CANT_OPEN_DATA_CONNECTION(  425, "Can't open data connection", 0),
    ABORTED(                    426, "Transfer was aborted", 0),
    INVALID_USER_OR_PASSWORD(   430, "Invalid username or password", 0),
    ACTION_ABORTED_LOCAL(       451, "Requested action aborted. Local error in processing", 0),
    SYNTAX_ERROR(               501, "Syntax error in parameters or arguments",0),
    FILE_ALREADY_EXIST(         550, "%s: File already exists", 1),
    FILE_OR_DIR_NOT_FOUND(      550, "%s: Not found", 1),
    FILE_NO_ACCESS(             550, "%s: No access to file", 1),
    NOT_REGULAR_FILE(           550, "%s: Not regular file", 1),
    NOT_REGULAR_DIRECTORY(      550, "%s: Not regular directory", 1),
    DIRECTORY_NOT_EMPTY(        550, "%s: Directory is not empty", 1);



    private final int code;
    private final String message;
    private final int numberOfParams;

    ResponseType(int code, String message, int numberOfParams) {
        this.code = code;
        this.message = message;
        this.numberOfParams = numberOfParams;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
