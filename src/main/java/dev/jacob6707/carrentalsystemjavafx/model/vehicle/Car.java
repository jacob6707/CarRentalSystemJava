package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rentable;
import dev.jacob6707.carrentalsystemjavafx.exception.VehicleBookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that represents a car.
 */
public final class Car extends Vehicle implements Rentable {
    private static final Logger log = LoggerFactory.getLogger(Car.class);
    
    private BigDecimal dailyPrice;
    private Boolean available = true;

    public Car() {}

    private Car(CarBuilder builder) {
        super(builder.id, builder.licensePlate, builder.brand, builder.model, builder.year, builder.mileage);
        this.dailyPrice = builder.dailyPrice;
        this.available = builder.available;
    }

    /**
     * Builder for the {@link Car} class.
     * <p>
     * Uses the following attributes:
     * - licensePlate(String licensePlate)
     * - brand(String brand)
     * - model(String model)
     * - year(Integer year)
     * - mileage(Integer mileage)
     * - dailyPrice(BigDecimal dailyPrice)
     * - available(Boolean available)
     * <p>
     * Builds a {@link Car} object when {@link #build()} is called.
     */
    public static class CarBuilder {
        private UUID id;
        private String licensePlate;
        private String brand;
        private String model;
        private Integer year;
        private Integer mileage = 0;
        private BigDecimal dailyPrice = BigDecimal.ZERO;
        private Boolean available = true;

        public CarBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public CarBuilder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder year(Integer year) {
            this.year = year;
            return this;
        }

        public CarBuilder mileage(Integer mileage) {
            this.mileage = mileage;
            return this;
        }

        public CarBuilder dailyPrice(BigDecimal dailyPrice) {
            this.dailyPrice = dailyPrice;
            return this;
        }

        public CarBuilder available(Boolean available) {
            this.available = available;
            return this;
        }

        /**
         * Builds the Car object.
         * @return {@link Car}
         */
        public Car build() {
            return new Car(this);
        }
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
        if (Boolean.FALSE.equals(available)) throw new VehicleBookingException("Car is already booked, can't rent it");
        available = false;
    }

    /**
     * Returns the car to the available state.
     *
     * @throws VehicleBookingException If the car is not booked
     */
    @Override
    public void returnBack() throws VehicleBookingException {
        if (Boolean.TRUE.equals(available)) throw new VehicleBookingException("Car is not booked, can't return it!");
        available = true;
    }

    /**
     * Services the car.
     */
    @Override
    public void service() {
        log.info("Servicing car: {} {}", brand, model);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Objects.equals(getDailyPrice(), car.getDailyPrice()) && Objects.equals(available, car.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDailyPrice(), available);
    }
}
