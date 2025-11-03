package dev.jacob6707.carrentalsystem.exception;

/**
 * Checked exception thrown when an invalid numeric value is provided.
 * Extends Exception to force handling at compile time.
 */
public class InvalidNumericValueException extends Exception {
    /**
     * Constructs a new InvalidNumericValueException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public InvalidNumericValueException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidNumericValueException with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause the cause of the exception
     */
    public InvalidNumericValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
