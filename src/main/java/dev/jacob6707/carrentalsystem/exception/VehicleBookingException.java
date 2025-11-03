package dev.jacob6707.carrentalsystem.exception;

/**
 * Checked exception that represents a problem when booking a vehicle.
 * Extends Exception to force handling at compile time.
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
