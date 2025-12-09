package dev.jacob6707.carrentalsystem.entities;

import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.math.BigDecimal;

/**
 * Interface that represents a rentable vehicle.
 * Rentable vehicles can be rented and returned and have a daily price.
 */
public interface Rentable {
    boolean isAvailable();
    void rent() throws VehicleBookingException;
    void returnBack() throws VehicleBookingException;
    BigDecimal getDailyPrice();
}
