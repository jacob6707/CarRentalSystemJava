package dev.jacob6707.carrentalsystem.entities;

import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;

import java.math.BigDecimal;

/**
 * Class that represents an SUV.
 * SUVs are rentable and serviceable.
 * @see Rentable
 * @see Serviceable
 */
public final class SUV extends Vehicle implements Rentable, Serviceable {
    private final BigDecimal dailyPrice;
    private Boolean available;

    private SUV(SUVBuilder builder) {
        super(builder.licensePlate, builder.brand, builder.model, builder.year, builder.mileage);
        this.dailyPrice = builder.dailyPrice;
        this.available = builder.available;
    }

    /**
     * Builder for the {@link SUV} class.
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
     * Builds a {@link SUV} object when {@link #build()} is called.
     */
    public static class SUVBuilder {
        private String licensePlate;
        private String brand;
        private String model;
        private Integer year;
        private Integer mileage = 0;
        private BigDecimal dailyPrice = BigDecimal.ZERO;
        private Boolean available = true;

        public SUVBuilder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public SUVBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public SUVBuilder model(String model) {
            this.model = model;
            return this;
        }

        public SUVBuilder year(Integer year) {
            this.year = year;
            return this;
        }

        public SUVBuilder mileage(Integer mileage) {
            this.mileage = mileage;
            return this;
        }

        public SUVBuilder dailyPrice(BigDecimal dailyPrice) {
            this.dailyPrice = dailyPrice;
            return this;
        }

        public SUVBuilder available(Boolean available) {
            this.available = available;
            return this;
        }

        /**
         * Builds the SUV object.
         * @return {@link SUV}
         */
        public SUV build() {
            return new SUV(this);
        }
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
