package dev.jacob6707.carrentalsystem.entities;

import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;

import java.math.BigDecimal;

/**
 * Class that represents a car.
 * Cars are rentable and serviceable.
 *
 * @see Rentable
 * @see Serviceable
 */
public final class Car implements Rentable, Serviceable {
    private final String brand;
    private final String model;
    private final Integer year;
    private final BigDecimal dailyPrice;
    private Boolean available;

    /**
     * Constructor for Car class.
     *
     * @param brand Brand of the car
     * @param model Model of the car
     * @param year Year of manufacture of the car
     * @param dailyPrice Daily price of the car
     * @param available Availability of the car
     */
    public Car(String brand, String model, Integer year, BigDecimal dailyPrice, Boolean available) {
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
     * Checks if the car is available.
     *
     * @return true if available, false if not
     */
    @Override
    public boolean isAvailable() {
        return available;
    }

    /**
     * Rents the car.
     *
     * @throws VehicleBookingException If the car is already booked
     */
    @Override
    public void rent() throws VehicleBookingException {
        if (!available) throw new VehicleBookingException("Car is already booked, can't rent it");
        available = false;
    }

    /**
     * Returns the car to the available state.
     *
     * @throws VehicleBookingException If the car is not booked
     */
    @Override
    public void returnBack() throws VehicleBookingException {
        if (available) throw new VehicleBookingException("Car is not booked, can't return it!");
        available = true;
    }

    /**
     * Services the car.
     */
    @Override
    public void service() {
        System.out.println("Servicing car: " + brand + " " + model);
    }
}
