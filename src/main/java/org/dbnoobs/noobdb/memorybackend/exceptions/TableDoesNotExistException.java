package org.dbnoobs.noobdb.memorybackend.exceptions;

public class TableDoesNotExistException extends RuntimeException{

    public TableDoesNotExistException() {
        super("Table does not exist");
    }

    public TableDoesNotExistException(String message) {
        super(message);
    }

    public TableDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected TableDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
