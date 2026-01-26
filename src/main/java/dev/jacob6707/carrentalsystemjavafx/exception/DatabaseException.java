package dev.jacob6707.carrentalsystemjavafx.exception;

/**
 * Represents a runtime exception that occurs during database operations.
 * This exception can be used to wrap underlying database-related errors
 * and provide additional context when such issues arise.
 *
 * Extends {@code RuntimeException}, allowing it to be used for unchecked
 * exceptions that do not require explicit handling in code.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
