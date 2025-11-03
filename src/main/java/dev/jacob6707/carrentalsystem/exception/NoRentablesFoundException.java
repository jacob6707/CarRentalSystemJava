package dev.jacob6707.carrentalsystem.exception;

/**
 * Exception that is thrown when no rentables are found.
 * Extends RuntimeException so it doesn't need to be declared or caught.
 */
public class NoRentablesFoundException extends RuntimeException {
    /**
     * Constructs a new NoRentablesFoundException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public NoRentablesFoundException(String message) {
        super(message);
    }
}
