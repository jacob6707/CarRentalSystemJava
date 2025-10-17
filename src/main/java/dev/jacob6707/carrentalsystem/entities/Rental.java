package dev.jacob6707.carrentalsystem.entities;

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

    public void print() {
        System.out.println("Rented by: " + this.getUser().getFirstName() + " " + this.getUser().getLastName());
        System.out.println("Car: " + this.getCar().getYear() + " " + this.getCar().getBrand() + " " + this.getCar().getModel());
        System.out.println("Rented until: " + this.getEndDate());
        System.out.println("Price: EUR" + this.getPrice());
    }

    public static Rental findMostExpensiveRental(Rental[] rentals) {
        Rental mostExpensiveRental = rentals[0];
        for(int i = 1; i < rentals.length; i++) {
            if (rentals[i].getPrice().compareTo(mostExpensiveRental.getPrice()) > 0) {
                mostExpensiveRental = rentals[i];
            }
        }
        return mostExpensiveRental;
    }

    public static Rental findLeastExpensiveRental(Rental[] rentals) {
        Rental leastExpensiveRental = rentals[0];
        for(int i = 1; i < rentals.length; i++) {
            if (rentals[i].getPrice().compareTo(leastExpensiveRental.getPrice()) < 0) {
                leastExpensiveRental = rentals[i];
            }
        }
        return leastExpensiveRental;
    }

    public static Rental[] findRentalsByCarBrand(String brand, Rental[] rentals) {
        Rental[] foundRentals = new Rental[rentals.length];
        Integer i = 0;
        for (Rental rental : rentals) {
            if (rental.getCar().getBrand().equalsIgnoreCase(brand)) {
                foundRentals[i] = rental;
                i++;
            }
        }

        return foundRentals;
    }

    public static Rental[] findRentalsByUser(User user, Rental[] rentals) {
        Rental[] foundRentals = new Rental[rentals.length];
        Integer i = 0;
        for (Rental rental : rentals) {
            if (rental.getUser().equals(user)) {
                foundRentals[i] = rental;
                i++;
            }
        }

        return foundRentals;
    }

    public static void printFoundRentals(Rental[] foundRentals) {
        for (int i = 0; i < foundRentals.length; i++) {
            if (foundRentals[i] != null) {
                System.out.println("Rental #" + i + ":");
                foundRentals[i].print();
            } else if(i == 0) {
                System.out.println("No rentals found with the specified criteria.");
                break;
            }
        }
    }
}
