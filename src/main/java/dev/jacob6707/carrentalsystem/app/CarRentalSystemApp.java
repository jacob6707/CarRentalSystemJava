package dev.jacob6707.carrentalsystem.app;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.InvalidDateFormatException;
import dev.jacob6707.carrentalsystem.exception.NegativeValueException;
import dev.jacob6707.carrentalsystem.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Main application class for the Car Rental System.
 * Handles initialization, user input, and the main application flow.
 */
public class CarRentalSystemApp {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalSystemApp.class);

    /**
     * Main entry point for the Car Rental System application.
     * Initializes the system, collects user input, and manages the main application loop.
     */
    static void main() {
        logger.info("=== Car Rental System Started ===");
        Scanner sc = new Scanner(System.in);

        System.out.println("Car Rental System");

        Employee employee = EmployeeService.createEmployee(sc);

        try {
            logger.debug("Starting data collection for system entities");

            List<Customer> customers = new ArrayList<>();

            Integer numberOfCustomers = InputService.readPositiveInteger(sc, "Enter number of customers: ");
            for (int i = 0; i < numberOfCustomers; i++) {
                System.out.println("===CUSTOMER #" + (i+1) + " INPUT===");
                customers.add(CustomerService.generateCustomer(sc));
            }
            logger.info("Generated {} customers", customers.size());

            Map<PersonRole, List<Person>> people = new HashMap<>();

            people.put(PersonRole.EMPLOYEE, Collections.singletonList(employee));
            people.put(PersonRole.CUSTOMER, List.copyOf(customers));

            Set<Car> cars = new HashSet<>();
            Integer numberOfCars = InputService.readPositiveInteger(sc, "Enter number of cars: ");
            for (int i = 0; i < numberOfCars; i++) {
                System.out.println("===CAR #" + (i+1) + " INPUT===");
                cars.add(CarService.generateCar(sc));
            }
            logger.info("Generated {} cars", cars.size());

            Set<SUV> suvs = new HashSet<>();
            Integer numberOfSUVs = InputService.readPositiveInteger(sc, "Enter number of SUVs: ");
            for (int i = 0; i < numberOfSUVs; i++) {
                System.out.println("===SUV #" + (i+1) + " INPUT===");
                suvs.add(CarService.generateSUV(sc));
            }
            logger.info("Generated {} SUVs", suvs.size());

            List<Rentable> rentables = new ArrayList<>();

            rentables.addAll(cars);
            rentables.addAll(suvs);

            List<Rental> rentals = new ArrayList<>();
            Integer numberOfRentals = InputService.readPositiveInteger(sc, "Enter number of rentals: ");
            for (int i = 0; i < numberOfRentals; i++) {
                System.out.println("===RENTAL #" + (i + 1) + " INPUT===");
                rentals.add(RentalService.generateRental(sc, customers, rentables));
            }
            logger.info("Generated {} rentals", rentals.size());

            RentalService.printAllRentals(rentals);

            String confirmation;
            do {
                //AppService.chooseAction(sc, people, rentables, rentals);

                System.out.print("Do you want to continue (Y/N)? ");
                confirmation = sc.nextLine();
                logger.debug("User confirmation: {}", confirmation);
            } while (confirmation.equalsIgnoreCase("y"));

            logger.info("=== Car Rental System Ended Normally ===");
        } catch (Exception e) {
            logger.error("Critical error in main application", e);
            System.out.println("A critical error occurred: " + e.getMessage());
        } finally {
            sc.close();
            logger.debug("Scanner closed");
        }
    }

}
