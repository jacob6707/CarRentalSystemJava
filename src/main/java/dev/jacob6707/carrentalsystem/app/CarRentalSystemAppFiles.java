package dev.jacob6707.carrentalsystem.app;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.exception.JsonParseException;
import dev.jacob6707.carrentalsystem.repository.JsonRepository;
import dev.jacob6707.carrentalsystem.services.AppService;
import dev.jacob6707.carrentalsystem.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Scanner;

public class CarRentalSystemAppFiles {
    private static final Logger log = LoggerFactory.getLogger(CarRentalSystemAppFiles.class);
    static void main() {
        log.info("=== Car Rental System Started ===");
        JsonRepository<Vehicle> vehicleRepository = new JsonRepository<>(Paths.get("data/vehicles.json"), Vehicle.class);
        JsonRepository<Customer> customerRepository = new JsonRepository<>(Paths.get("data/customers.json"), Customer.class);
        JsonRepository<Employee> employeeRepository = new JsonRepository<>(Paths.get("data/employees.json"), Employee.class);
        JsonRepository<Rental> rentalRepository = new JsonRepository<>(Paths.get("data/rentals.json"), Rental.class);

        try (Scanner sc = new Scanner(System.in)) {
            if (employeeRepository.findAll().isEmpty()) {
                log.info("No employees found. Creating default employee...");
                System.out.println("No employees found. Enter your information as the first employee:");
                Employee employee = EmployeeService.createEmployee(sc);
                try {
                    employeeRepository.save(employee);
                } catch (JsonParseException e) {
                    log.error("Error saving employee: {}", e.getMessage());
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
            }

            Employee selectedEmployee = null;
            while (selectedEmployee == null) {
                try {
                    System.out.println("Select employee:");
                    for (Employee employee : employeeRepository.findAll()) {
                        System.out.println(employee.getId() + ") " + employee.getFirstName() + " " + employee.getLastName());
                    }
                    Long selectedEmployeeId = sc.nextLong();
                    selectedEmployee = employeeRepository.findById(selectedEmployeeId).orElse(null);
                } catch (NullPointerException e) {
                    System.out.println("Invalid employee ID. Please try again.");
                    log.warn("Invalid employee ID: {}", e.getMessage());
                }
            }
            System.out.println("Welcome " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + "!");

            String confirmation;
            do {
                AppService.chooseAction(sc, selectedEmployee, customerRepository, employeeRepository, vehicleRepository, rentalRepository);
                System.out.print("Do you want to continue (Y/N)? ");
                confirmation = sc.nextLine();
                log.debug("User confirmation: {}", confirmation);
            } while (confirmation.equalsIgnoreCase("y"));

            log.info("=== Car Rental System Ended Normally ===");
        } catch (Exception e) {
            log.error("Critical error in main application", e);
            System.out.println("A critical error occurred: " + e.getMessage());
        }
    }
}
