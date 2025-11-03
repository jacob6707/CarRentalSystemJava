package dev.jacob6707.carrentalsystem.exception;

/**
 * Unchecked exception that is thrown when no customers are found.
 * Extends RuntimeException so it doesn't need to be declared or caught.
 */
public class NoCustomersFoundException extends RuntimeException {
    /**
     * Constructs a new NoCustomersFoundException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public NoCustomersFoundException(String message) {
        super(message);
    }
}
