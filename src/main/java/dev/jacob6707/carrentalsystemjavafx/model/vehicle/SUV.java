package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

import dev.jacob6707.carrentalsystemjavafx.exception.VehicleBookingException;
import dev.jacob6707.carrentalsystemjavafx.model.rental.Rentable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that represents an SUV.
 */
public final class SUV extends Vehicle implements Rentable {
    private static final Logger log = LoggerFactory.getLogger(SUV.class);

    private BigDecimal dailyPrice;
    private Boolean available = true;

    public SUV() {}

    private SUV(SUVBuilder builder) {
        super(builder.id, builder.licensePlate, builder.brand, builder.model, builder.year, builder.mileage);
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
        private UUID id;
        private String licensePlate;
        private String brand;
        private String model;
        private Integer year;
        private Integer mileage = 0;
        private BigDecimal dailyPrice = BigDecimal.ZERO;
        private Boolean available = true;

        public SUVBuilder id(UUID id) {
            this.id = id;
            return this;
        }

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
        if (Boolean.FALSE.equals(available)) throw new VehicleBookingException("SUV is already booked");
        available = false;
    }

    /**
     * Returns the SUV to the available state.
     * @throws VehicleBookingException If the SUV is not booked
     */
    @Override
    public void returnBack() throws VehicleBookingException {
        if (Boolean.TRUE.equals(available)) throw new VehicleBookingException("SUV is not booked, can't return it!");
        available = true;
    }

    /**
     * Services the SUV.
     */
    @Override
    public void service() {
        log.info("Servicing SUV: {} {}",brand, model);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SUV suv = (SUV) o;
        return Objects.equals(getDailyPrice(), suv.getDailyPrice()) && Objects.equals(available, suv.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDailyPrice(), available);
    }
}
