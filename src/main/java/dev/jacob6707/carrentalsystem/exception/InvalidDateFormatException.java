package dev.jacob6707.carrentalsystem.exception;

/**
 * Checked exception thrown when an invalid date format is provided.
 * Extends Exception to force handling at compile time.
 */
public class InvalidDateFormatException extends Exception {
    /**
     * Constructs a new InvalidDateFormatException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public InvalidDateFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidDateFormatException with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause the cause of the exception
     */
    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
