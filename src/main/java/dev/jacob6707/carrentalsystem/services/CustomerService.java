package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;

public class CustomerService {
    public static Customer[] generateCustomers(Scanner sc, Integer numberOfCustomers) {
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

    public static Customer selectCustomer(Scanner sc, Customer[] customers) {
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
