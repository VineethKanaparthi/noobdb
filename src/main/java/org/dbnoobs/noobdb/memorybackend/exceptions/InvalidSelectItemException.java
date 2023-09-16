package org.dbnoobs.noobdb.memorybackend.exceptions;

public class InvalidSelectItemException extends RuntimeException{
    public InvalidSelectItemException() {
        super("Select item is not valid");
    }

    public InvalidSelectItemException(String message) {
        super(message);
    }

    public InvalidSelectItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSelectItemException(Throwable cause) {
        super(cause);
    }

    protected InvalidSelectItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
