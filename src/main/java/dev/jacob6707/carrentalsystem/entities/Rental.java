package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class that represents a rental.
 */
public class Rental {
    private Customer customer;
    private Rentable car;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs a new rental.
     *
     * @param customer The customer that rented the car
     * @param car The rented car
     * @param startDate The start date of the rental
     * @param endDate The end date of the rental
     */
    public Rental(Customer customer, Rentable car, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Rentable getVehicle() {
        return car;
    }

    public void setCar(Rentable car) {
        this.car = car;
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
        return car.getDailyPrice().multiply(days);
    }

    /**
     * Prints the rental information.
     */
    public void print() {
        System.out.println("Rented by: " + this.getCustomer().getFirstName() + " " + this.getCustomer().getLastName());
        if (this.getVehicle() instanceof Vehicle vehicle) {
            System.out.println("Vehicle: " + vehicle.getYear() + " " + vehicle.getBrand() + " " + vehicle.getModel());
        }
        System.out.println("Rented until: " + this.getEndDate());
        System.out.println("Price: EUR" + this.getPrice());
    }
}
