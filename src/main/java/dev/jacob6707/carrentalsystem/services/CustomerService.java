package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.exception.InvalidDateFormatException;
import dev.jacob6707.carrentalsystem.exception.NoCustomersFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Service for getting customers.
 *
 * @see Customer
 */
public class CustomerService {

    /**
     * Generates a customer through user input.
     *
     * @param sc Scanner object for reading user input
     * @return The generated customer
     */
    public static Customer generateCustomer(Scanner sc) {
        System.out.println("Enter customer's first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter customer's last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter customer's email: ");
        String email = sc.nextLine();
        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            try {
                dateOfBirth = InputService.readValidatedDate(sc, "Enter customer's date of birth (dd.MM.yyyy): ", "dd.MM.yyyy");
            } catch (InvalidDateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Customer.CustomerBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .dateOfBirth(dateOfBirth)
                .build();
    }

    /**
     * Selects a customer from a list of customers.
     *
     * @param sc Scanner object for reading user input
     * @param customers Array of customers
     * @return The selected customer
     */
    public static Customer selectCustomer(Scanner sc, List<Customer> customers) {
        if (customers.isEmpty()) throw new NoCustomersFoundException("No customers available.");
        Integer ordinal = 1;

        System.out.println("Available customers:");
        for (Customer customer : customers) {
            System.out.println(ordinal + ") " + customer.getFirstName() + " " + customer.getLastName() + "(" + customer.getEmail() + ")");
            ordinal++;
        }

        int selection;
        do {
            System.out.print("Select customer> ");
            selection = sc.nextInt();
            sc.nextLine();
        } while (selection < 1 || selection > customers.size());

        return customers.get(selection-1);
    }

}
