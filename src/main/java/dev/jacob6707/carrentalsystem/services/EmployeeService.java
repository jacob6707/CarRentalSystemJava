package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Employee;
import dev.jacob6707.carrentalsystem.exception.InvalidDateFormatException;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.NegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    public static Employee createEmployee(Scanner sc) {
        Employee employee = null;
        boolean employeeCreated = false;

        while (!employeeCreated) {
            try {
                log.debug("Starting employee information collection");
                System.out.println("Insert information of the first employee:");

                System.out.print("First Name: ");
                String employeeFirstName = sc.nextLine();
                log.trace("Employee first name: {}", employeeFirstName);

                System.out.print("Last Name: ");
                String employeeLastName = sc.nextLine();
                log.trace("Employee last name: {}", employeeLastName);

                System.out.print("Email: ");
                String employeeEmail = sc.nextLine();
                log.trace("Employee email: {}", employeeEmail);

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
                log.info("Employee created successfully: {} {}", employeeFirstName, employeeLastName);
            } catch (InvalidDateFormatException | InvalidNumericValueException e) {
                log.warn("Error creating employee: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (NegativeValueException e) {
                log.warn("Negative value error: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
        return employee;
    }
}