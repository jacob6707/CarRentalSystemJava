package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents an abstract vehicle that can be serviced.
 * @see Serviceable
 */
@JsonbTypeInfo({
        @JsonbSubtype(alias = "Car", type=Car.class),
        @JsonbSubtype(alias = "SUV", type=SUV.class)
})
public abstract non-sealed class Vehicle extends Entity implements Serviceable {
    protected String licensePlate;
    protected String brand;
    protected String model;
    protected int year;
    protected int mileage;

    protected Vehicle() {}

    protected Vehicle(UUID id, String licensePlate, String brand, String model, int year, int mileage) {
        super(id);
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    /**
     * Gets the license plate of the vehicle.
     * @return license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Gets the brand of the vehicle.
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the model of the vehicle.
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the year of the vehicle.
     * @return year of manufacture
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the mileage of the vehicle.
     * @return mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the mileage of the vehicle.
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Sets the license plate of the vehicle.
     * @param licensePlate license plate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * Sets the brand of the vehicle.
     * @param brand brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Sets the model of the vehicle.
     * @param model model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets the year of the vehicle.
     * @param year year of manufacture
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Determines equality based on fields
     *
     * @param o object to compare to
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return getYear() == vehicle.getYear() && getMileage() == vehicle.getMileage() && Objects.equals(getLicensePlate(), vehicle.getLicensePlate()) && Objects.equals(getBrand(), vehicle.getBrand()) && Objects.equals(getModel(), vehicle.getModel());
    }

    /**
     * Generates hashcode based on fields
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getLicensePlate(), getBrand(), getModel(), getYear(), getMileage());
    }

    /**
     * Returns a string representation of the vehicle.
     * @return year brand model (license plate)
     */
    @Override
    public String toString() {
        return getYear() + " " + getBrand() + " " + getModel() + " (" + getLicensePlate() + ")";
    }
}
