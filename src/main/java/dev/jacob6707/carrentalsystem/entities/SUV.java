package dev.jacob6707.carrentalsystem.entities;

import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;

import java.math.BigDecimal;

/**
 * Class that represents an SUV.
 * SUVs are rentable and serviceable.
 * @see Rentable
 * @see Serviceable
 */
public final class SUV implements Rentable, Serviceable {
    private final String brand;
    private final String model;
    private final Integer year;
    private final BigDecimal dailyPrice;
    private Boolean available;

    public SUV(String brand, String model, Integer year, BigDecimal dailyPrice, Boolean available) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyPrice = dailyPrice;
        this.available = available;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Checks if the SUV is available.
     * @return true if available, false if not
     */
    @Override
    public boolean isAvailable() {
        return available;
    }

    /**
     * Rents the SUV.
     * @throws VehicleBookingException If the SUV is already booked
     */
    @Override
    public void rent() throws VehicleBookingException {
        if (!available) throw new VehicleBookingException("SUV is already booked");
        available = false;
    }

    /**
     * Returns the SUV to the available state.
     * @throws VehicleBookingException If the SUV is not booked
     */
    @Override
    public void returnBack() throws VehicleBookingException {
        if (available) throw new VehicleBookingException("SUV is not booked, can't return it!");
        available = true;
    }

    /**
     * Services the SUV.
     */
    @Override
    public void service() {
        System.out.println("Servicing SUV: " + brand + " " + model);
    }
}
