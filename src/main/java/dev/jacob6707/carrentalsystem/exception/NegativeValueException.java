package dev.jacob6707.carrentalsystem.exception;

/**
 * Unchecked exception thrown when a negative value is provided where only positive values are allowed.
 * Extends RuntimeException so it doesn't need to be declared or caught.
 */
public class NegativeValueException extends RuntimeException {
    /**
     * Constructs a new NegativeValueException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public NegativeValueException(String message) {
        super(message);
    }
}
