package dev.jacob6707.carrentalsystemjavafx.model.rental;

import dev.jacob6707.carrentalsystemjavafx.exception.VehicleBookingException;

import java.math.BigDecimal;

/**
 * Represents an entity that can be rented, such as a vehicle.
 * Provides methods to check availability, rent the entity, return it,
 * and retrieve the daily price for renting.
 */
public interface Rentable {
    /**
     * Checks if the entity is available for renting.
     * @return true if available, false if not
     */
    boolean isAvailable();

    /**
     * Rents the entity.
     *
     * @throws VehicleBookingException If the entity is not available
     */
    void rent() throws VehicleBookingException;

    /**
     * Returns the entity to the available state.
     * @throws VehicleBookingException If the entity is not rented
     */
    void returnBack() throws VehicleBookingException;

    /**
     * Gets the daily price for renting the entity.
     * @return daily price
     */
    BigDecimal getDailyPrice();
}
