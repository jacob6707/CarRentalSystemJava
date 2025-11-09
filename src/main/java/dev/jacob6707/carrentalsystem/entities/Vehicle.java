package dev.jacob6707.carrentalsystem.entities;

import java.util.Objects;

public abstract class Vehicle {
    protected final String licensePlate;
    protected final String brand;
    protected final String model;
    protected final int year;
    protected int mileage;

    public Vehicle(String licensePlate, String brand, String model, int year, int mileage) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return getYear() == vehicle.getYear() && getMileage() == vehicle.getMileage() && Objects.equals(getLicensePlate(), vehicle.getLicensePlate()) && Objects.equals(getBrand(), vehicle.getBrand()) && Objects.equals(getModel(), vehicle.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLicensePlate(), getBrand(), getModel(), getYear(), getMileage());
    }

    @Override
    public String toString() {
        return getYear() + " " + getBrand() + " " + getModel() + " (" + getLicensePlate() + ")";
    }
}
