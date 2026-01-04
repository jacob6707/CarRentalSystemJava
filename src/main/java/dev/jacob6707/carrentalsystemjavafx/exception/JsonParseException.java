package dev.jacob6707.carrentalsystemjavafx.exception;

/**
 * Exception thrown when there is an error parsing JSON.
 */
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
