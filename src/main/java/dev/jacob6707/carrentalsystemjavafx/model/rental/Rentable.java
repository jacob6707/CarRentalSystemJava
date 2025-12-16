package dev.jacob6707.carrentalsystemjavafx.model.rental;

import dev.jacob6707.carrentalsystemjavafx.exception.VehicleBookingException;

import java.math.BigDecimal;

public interface Rentable {
    boolean isAvailable();
    void rent() throws VehicleBookingException;
    void returnBack() throws VehicleBookingException;
    BigDecimal getDailyPrice();
}
