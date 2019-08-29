package com.cubodrom.metacollector.exception;

public class FileParseException extends RuntimeException {

    public FileParseException() {
    }

    public FileParseException(String message) {
        super(message);
    }

    public FileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
