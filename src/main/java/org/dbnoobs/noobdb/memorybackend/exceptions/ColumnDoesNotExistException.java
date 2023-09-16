package org.dbnoobs.noobdb.memorybackend.exceptions;

public class ColumnDoesNotExistException extends RuntimeException{
    public ColumnDoesNotExistException() {
        super("Column does not exist");
    }

    public ColumnDoesNotExistException(String message) {
        super(message);
    }

    public ColumnDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumnDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected ColumnDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
