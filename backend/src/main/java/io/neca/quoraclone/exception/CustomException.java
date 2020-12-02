package io.neca.quoraclone.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Exception exception) {
        super(message, exception);
    }

}
