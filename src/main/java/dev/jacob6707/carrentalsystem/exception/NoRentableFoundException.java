package dev.jacob6707.carrentalsystem.exception;

/**
 * Exception that is thrown when no rentable is found.
 * Extends RuntimeException so it doesn't need to be declared or caught.
 */
public class NoRentableFoundException extends RuntimeException {
    /**
     * Constructs a new NoRentableFoundException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public NoRentableFoundException(String message) {
        super(message);
    }
}
