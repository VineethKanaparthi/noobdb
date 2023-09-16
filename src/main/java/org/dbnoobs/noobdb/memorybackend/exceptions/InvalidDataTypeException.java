package org.dbnoobs.noobdb.memorybackend.exceptions;

public class InvalidDataTypeException extends RuntimeException{

    public InvalidDataTypeException() {
        super("Invalid datatype");
    }

    public InvalidDataTypeException(String message) {
        super(message);
    }

    public InvalidDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataTypeException(Throwable cause) {
        super(cause);
    }

    protected InvalidDataTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
