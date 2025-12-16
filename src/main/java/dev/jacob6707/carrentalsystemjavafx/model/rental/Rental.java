package dev.jacob6707.carrentalsystemjavafx.model.rental;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;
import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Rental extends Entity {
    private Customer customer;
    @JsonbProperty("rentedVehicle")
    private Vehicle vehicle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Rental() {}

    /**
     * Constructs a new rental.
     *
     * @param customer The customer that rented the car
     * @param vehicle The rented car
     * @param startDate The start date of the rental
     * @param endDate The end date of the rental
     */
    public Rental(Customer customer, Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        super(UUID.randomUUID());
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        BigDecimal days = BigDecimal.valueOf(Duration.between(this.startDate, this.endDate).toDays());
        if (vehicle instanceof Rentable r) {
            return days.multiply(r.getDailyPrice());
        }
        return BigDecimal.ZERO;
    }
}
