package dev.jacob6707.carrentalsystem.entities;

import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.util.Objects;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "Car", type=Car.class),
        @JsonbSubtype(alias = "SUV", type=SUV.class)
})
public abstract class Vehicle extends Entity {
    protected String licensePlate;
    protected String brand;
    protected String model;
    protected int year;
    protected int mileage;

    public Vehicle() {}

    public Vehicle(Long id, String licensePlate, String brand, String model, int year, int mileage) {
        super(id);
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

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
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
