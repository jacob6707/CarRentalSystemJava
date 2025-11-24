package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Service for working with rentals.
 *
 * @see Rental
 */
public class RentalService {

    /**
     * Generates a rental through user input.
     *
     * @param sc Scanner object for reading user input
     * @param customers Array of customers
     * @param rentableCars Array of rentable cars
     * @return {@link Rental} object
     */
    public static Rental generateRental(Scanner sc, List<Customer> customers, List<Rentable> rentableCars) {
        Customer customer = CustomerService.selectCustomer(sc, customers);
        Rentable rentableCar = CarService.selectCar(sc, rentableCars);
        System.out.print("Enter how many days the car will be rented for: ");
        int numOfDays = sc.nextInt();
        sc.nextLine();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(numOfDays);
        return new Rental(customer, rentableCar, start, end);
    }

    /**
     * Finds the most expensive rental.
     *
     * @param rentals The list of rentals
     * @return The most expensive rental
     */
    public static Optional<Rental> findMostExpensiveRental(List<Rental> rentals) {
        return rentals
                .stream()
                .max(Comparator.comparing(Rental::getPrice));
    }

    /**
     * Finds the least expensive rental.
     *
     * @param rentals The list of rentals
     * @return The least expensive rental
     */
    public static Optional<Rental> findLeastExpensiveRental(List<Rental> rentals) {
        return rentals
                .stream()
                .min(Comparator.comparing(Rental::getPrice));
    }

    /**
     * Finds rentals by car brand.
     *
     * @param brand The brand of the car
     * @param rentals The array of rentals
     * @return The array of rentals with the specified brand
     */
    public static List<Rental> findRentalsByCarBrand(String brand, List<Rental> rentals) {
        return rentals.stream()
                .filter(rental -> {
                    Optional<Vehicle> vehicle = rental.getVehicle() instanceof Vehicle v ? Optional.of(v) : Optional.empty();
                    return vehicle.map(value -> value.getBrand().equalsIgnoreCase(brand)).orElse(false);
                })
                .toList();
    }

    /**
     * Finds rentals by user.
     *
     * @param customer The customer
     * @param rentals The array of rentals
     * @return The array of rentals with the specified user
     */
    public static List<Rental> findRentalsByUser(Customer customer, List<Rental> rentals) {
        return rentals.stream()
                .filter(rental -> rental.getCustomer().equals(customer))
                .toList();
    }

    /**
     * Prints the found rentals.
     *
     * @param foundRentals The array of found rentals
     */
    public static void printFoundRentals(List<Rental> foundRentals) {
        if (foundRentals.isEmpty()) {
            System.out.println("No rentals found with the specified criteria.");
            return;
        }
        for(Rental rental : foundRentals) {
            System.out.println("Rental:");
            rental.print();
        }
    }

    /**
     * Prints all rentals.
     *
     * @param rentals The array of rentals
     */
    public static void printAllRentals(List<Rental> rentals) {
        int ordinal = 1;
        for (Rental rental : rentals) {
            System.out.println("=====RENTAL #" + ordinal + "=====");
            rental.print();
            ordinal++;
        }
    }
}
