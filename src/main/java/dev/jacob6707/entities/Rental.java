package dev.jacob6707.entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class Rental {
    private User user;
    private Car car;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Rental(User user, Car car, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
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
}
