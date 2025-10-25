package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;

public final class Car implements Rentable, Serviceable {
    private final String brand;
    private final String model;
    private final Integer year;
    private final BigDecimal dailyPrice;
    private Boolean available;

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

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void rent() {
        available = false;
    }

    @Override
    public void returnBack() {
        available = true;
    }

    @Override
    public void service() {
        System.out.println("Servicing car: " + brand + " " + model);
    }
}
