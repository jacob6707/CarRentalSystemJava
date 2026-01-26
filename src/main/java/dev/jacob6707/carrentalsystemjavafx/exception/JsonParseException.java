package dev.jacob6707.carrentalsystemjavafx.exception;

/**
 * Represents a runtime exception that occurs during JSON parsing operations.
 * This exception is typically used to indicate issues such as malformed JSON,
 * incorrect data structure, or unexpected deserialization errors.
 *
 * Extends {@code RuntimeException}, allowing it to be used as an unchecked
 * exception that does not require mandatory handling in the program flow.
 */
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
