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
import java.util.Scanner;

/**
 * Main application class for the Car Rental System.
 * Handles initialization, user input, and main application flow.
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

        Employee employee = null;
        boolean employeeCreated = false;

        while (!employeeCreated) {
            try {
                logger.debug("Starting employee information collection");
                System.out.println("Insert information of the first employee:");

                System.out.print("First Name: ");
                String employeeFirstName = sc.nextLine();
                logger.trace("Employee first name: {}", employeeFirstName);

                System.out.print("Last Name: ");
                String employeeLastName = sc.nextLine();
                logger.trace("Employee last name: {}", employeeLastName);

                System.out.print("Email: ");
                String employeeEmail = sc.nextLine();
                logger.trace("Employee email: {}", employeeEmail);

                LocalDate dateOfBirth = InputService.readValidatedDate(sc, "Date of birth (dd.MM.yyyy): ", "dd.MM.yyyy");

                BigDecimal employeeSalary = InputService.readPositiveBigDecimal(sc, "Salary: ");

                employee = new Employee.EmployeeBuilder()
                        .firstName(employeeFirstName)
                        .lastName(employeeLastName)
                        .email(employeeEmail)
                        .dateOfBirth(dateOfBirth)
                        .salary(employeeSalary)
                        .build();

                employeeCreated = true;
                logger.info("Employee created successfully: {} {}", employeeFirstName, employeeLastName);
            } catch (InvalidDateFormatException | InvalidNumericValueException e) {
                logger.warn("Error creating employee: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (NegativeValueException e) {
                logger.warn("Negative value error: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }

        try {
            logger.debug("Starting data collection for system entities");

            Integer numberOfCustomers = InputService.readPositiveInteger(sc, "Enter number of customers: ");
            Customer[] customers = CustomerService.generateCustomers(sc, numberOfCustomers);
            logger.info("Generated {} customers", numberOfCustomers);

            Person[] people = new Person[customers.length + 1];
            people[0] = employee;
            System.arraycopy(customers, 0, people, 1, customers.length);

            Integer numberOfCars = InputService.readPositiveInteger(sc, "Enter number of cars: ");
            Car[] cars = CarService.generateCars(sc, numberOfCars);
            logger.info("Generated {} cars", numberOfCars);

            Integer numberOfSUVs = InputService.readPositiveInteger(sc, "Enter number of SUVs: ");
            SUV[] suvs = CarService.generateSUVs(sc, numberOfSUVs);
            logger.info("Generated {} SUVs", numberOfSUVs);

            Rentable[] rentables = new Rentable[suvs.length + cars.length];
            System.arraycopy(suvs, 0, rentables, 0, suvs.length);
            System.arraycopy(cars, 0, rentables, suvs.length, cars.length);

            Integer numberOfRentals = InputService.readPositiveInteger(sc, "Enter number of rentals: ");
            Rental[] rentals = RentalService.generateRentals(sc, customers, rentables, numberOfRentals);
            logger.info("Generated {} rentals", numberOfRentals);

            RentalService.printAllRentals(rentals);

            String confirmation;
            do {
                AppService.chooseAction(sc, people, rentables, rentals);

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
