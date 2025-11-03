package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.NoCustomersFoundException;
import dev.jacob6707.carrentalsystem.exception.NoRentablesFoundException;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Service for working with rentals.
 *
 * @see Rental
 */
public class RentalService {
    /**
     * Generates a list of rentals through user input.
     *
     * @param sc Scanner object for reading user input
     * @param customers Array of customers
     * @param rentableCars Array of rentable cars
     * @param numberOfRentals Number of rentals to generate
     * @return Array of generated rentals
     * @throws InvalidNumericValueException if the number of rentals is less than 1
     */
    public static Rental[] generateRentals(Scanner sc, Customer[] customers, Rentable[] rentableCars, Integer numberOfRentals) throws InvalidNumericValueException {
        if (numberOfRentals < 1) throw new InvalidNumericValueException("Number of rentals must be greater than 0.");
        if (rentableCars.length == 0) throw new NoRentablesFoundException("No rentable cars available.");
        if (customers.length == 0) throw new NoCustomersFoundException("No customers available.");
        Rental[] rentals = new Rental[numberOfRentals];

        for (int i = 0; i < numberOfRentals; i++) {
            System.out.println("===RENTAL #" + (i + 1) + " INPUT===");
            Customer customer = CustomerService.selectCustomer(sc, customers);
            Rentable rentableCar = CarService.selectCar(sc, rentableCars);
            System.out.print("Enter how many days the car will be rented for: ");
            Integer numOfDays = sc.nextInt();
            sc.nextLine();
            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = start.plusDays(numOfDays);
            rentals[i] = new Rental(customer, rentableCar, start, end);
        }

        return rentals;
    }

    /**
     * Finds the most expensive rental.
     *
     * @param rentals The array of rentals
     * @return The most expensive rental
     */
    public static Rental findMostExpensiveRental(Rental[] rentals) {
        Rental mostExpensiveRental = rentals[0];
        for(int i = 1; i < rentals.length; i++) {
            if (rentals[i].getPrice().compareTo(mostExpensiveRental.getPrice()) > 0) {
                mostExpensiveRental = rentals[i];
            }
        }
        return mostExpensiveRental;
    }

    /**
     * Finds the least expensive rental.
     *
     * @param rentals The array of rentals
     * @return The least expensive rental
     */
    public static Rental findLeastExpensiveRental(Rental[] rentals) {
        Rental leastExpensiveRental = rentals[0];
        for(int i = 1; i < rentals.length; i++) {
            if (rentals[i].getPrice().compareTo(leastExpensiveRental.getPrice()) < 0) {
                leastExpensiveRental = rentals[i];
            }
        }
        return leastExpensiveRental;
    }

    /**
     * Finds rentals by car brand.
     *
     * @param brand The brand of the car
     * @param rentals The array of rentals
     * @return The array of rentals with the specified brand
     */
    public static Rental[] findRentalsByCarBrand(String brand, Rental[] rentals) {
        Rental[] foundRentals = new Rental[rentals.length];
        Integer i = 0;
        for (Rental rental : rentals) {
            String carBrand;
            if (rental.getVehicle() instanceof SUV suv) {
                carBrand = suv.getBrand();
            } else if (rental.getVehicle() instanceof Car car) {
                carBrand = car.getBrand();
            } else {
                continue;
            }
            if (carBrand.equalsIgnoreCase(brand)) {
                foundRentals[i] = rental;
                i++;
            }
        }

        return foundRentals;
    }

    /**
     * Finds rentals by user.
     *
     * @param customer The customer
     * @param rentals The array of rentals
     * @return The array of rentals with the specified user
     */
    public static Rental[] findRentalsByUser(Customer customer, Rental[] rentals) {
        Rental[] foundRentals = new Rental[rentals.length];
        Integer i = 0;
        for (Rental rental : rentals) {
            if (rental.getUser().equals(customer)) {
                foundRentals[i] = rental;
                i++;
            }
        }

        return foundRentals;
    }

    /**
     * Prints the found rentals.
     *
     * @param foundRentals The array of found rentals
     */
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

    /**
     * Prints all rentals.
     *
     * @param rentals The array of rentals
     */
    public static void printAllRentals(Rental[] rentals) {
        Integer ordinal = 1;
        for (Rental rental : rentals) {
            System.out.println("=====RENTAL #" + ordinal + "=====");
            rental.print();
            ordinal++;
        }
    }
}
