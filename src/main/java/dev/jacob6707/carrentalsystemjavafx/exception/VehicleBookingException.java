package dev.jacob6707.carrentalsystemjavafx.exception;

/**
 * Represents an exception that occurs during vehicle booking operations.
 * This exception is designed to provide meaningful error messages for scenarios
 * where booking a vehicle fails or is deemed invalid. It should be used in cases
 * where business logic determines that the booking process cannot proceed.
 */
public class VehicleBookingException extends Exception {
    /**
     * Constructs a new VehicleBookingException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public VehicleBookingException(String message) {
        super(message);
    }
}
