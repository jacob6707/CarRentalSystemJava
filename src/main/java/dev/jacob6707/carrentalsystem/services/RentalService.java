package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Car;
import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.Rental;

import java.time.LocalDateTime;
import java.util.Scanner;

public class RentalService {
    public static Rental[] generateRentals(Scanner sc, Customer[] customers, Rentable[] rentableCars, Integer numberOfRentals) {
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

    public static void printAllRentals(Rental[] rentals) {
        Integer ordinal = 1;
        for (Rental rental : rentals) {
            System.out.println("=====RENTAL #" + ordinal + "=====");
            rental.print();
            ordinal++;
        }
    }
}
