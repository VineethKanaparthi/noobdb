package org.dbnoobs.noobdb.memorybackend.exceptions;

public class MissingValuesException extends RuntimeException {
    public MissingValuesException() {
        super("Missing values");
    }

    public MissingValuesException(String message) {
        super(message);
    }

    public MissingValuesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingValuesException(Throwable cause) {
        super(cause);
    }

    protected MissingValuesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
