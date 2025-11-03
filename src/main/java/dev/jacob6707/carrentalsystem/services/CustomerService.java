package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.NoCustomersFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Service for getting customers.
 *
 * @see Customer
 */
public class CustomerService {
    /**
     * Generates a list of customers through user input.
     *
     * @param sc Scanner object for reading user input
     * @param numberOfCustomers Number of customers to generate
     * @return Array of generated customers
     * @throws InvalidNumericValueException if the number of customers is less than 1
     */
    public static Customer[] generateCustomers(Scanner sc, Integer numberOfCustomers) throws InvalidNumericValueException {
        if (numberOfCustomers < 1) throw new InvalidNumericValueException("Number of customers must be greater than 0.");
        Customer[] customers = new Customer[numberOfCustomers];
        for (Integer i = 0; i < numberOfCustomers; i++) {
            System.out.println("===CUSTOMER #" + (i+1) + " INPUT===");
            System.out.print("Enter customer #" + (i+1) + "'s first name: ");
            String firstName = sc.nextLine();
            System.out.print("Enter customer #" + (i+1) + "'s last name: ");
            String lastName = sc.nextLine();
            System.out.print("Enter customer #" + (i+1) + "'s email: ");
            String email = sc.nextLine();
            System.out.print("Enter customer #" + (i+1) + "'s date of birth (dd.MM.yyyy): ");
            LocalDate dateOfBirth = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            customers[i] = new Customer.CustomerBuilder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .dateOfBirth(dateOfBirth)
                    .build();
        }
        return customers;
    }

    /**
     * Selects a customer from a list of customers.
     *
     * @param sc Scanner object for reading user input
     * @param customers Array of customers
     * @return The selected customer
     */
    public static Customer selectCustomer(Scanner sc, Customer[] customers) {
        if (customers.length == 0) throw new NoCustomersFoundException("No customers available.");
        Integer ordinal = 1;

        System.out.println("Available customers:");
        for (Customer customer : customers) {
            System.out.println(ordinal + ") " + customer.getFirstName() + " " + customer.getLastName() + "(" + customer.getEmail() + ")");
            ordinal++;
        }

        Integer selection;
        do {
            System.out.print("Select customer> ");
            selection = sc.nextInt();
            sc.nextLine();
        } while (selection < 1 || selection > customers.length);

        return customers[selection-1];
    }

}
