package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.entities.Person;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.Rental;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Service that handles application logic.
 */
public class AppService {
    public static final Integer NUMBER_OF_CHOICES = 7;
    private static final Logger logger = LoggerFactory.getLogger(AppService.class);

    /**
     * Prints main menu choices to the user.
     */
    private static void printChoices() {
        System.out.println("What do you want to do?");
        System.out.println("1) Print the most expensive rental");
        System.out.println("2) Print the cheapest rental");
        System.out.println("3) Search rentals by car brand");
        System.out.println("4) Search rentals made by a user");
        System.out.println("5) Check car availability");
        System.out.println("6) Print youngest person");
        System.out.println("7) Print oldest person");
    }

    /**
     * Handles the main application logic for user interaction.
     * Presents menu choices and executes the selected action.
     * Includes exception handling for invalid input with a retry mechanism.
     *
     * @param sc Scanner object for reading user input
     * @param people Array of Person objects (employees and customers)
     * @param rentableCars Array of available Rentable vehicles
     * @param rentals Array of existing Rental records
     */
    public static void chooseAction(Scanner sc, Person[] people, Rentable[] rentableCars, Rental[] rentals) {
        logger.debug("Processing {} people, {} rentable cars, {} rentals", people.length, rentableCars.length, rentals.length);
        // extract customers from array of people
        Integer numberOfCustomers = 0;
        for (Person person : people) {
            if (person instanceof Customer) {
                numberOfCustomers++;
            }
        }
        logger.debug("Found {} customers", numberOfCustomers);

        Customer[] customers = new Customer[numberOfCustomers];
        for (Person person : people) {
            if (person instanceof Customer customer) {
                customers[numberOfCustomers - 1] = customer;
                numberOfCustomers--;
            }
        }
        // reverse the array
        for (int i = 0; i < numberOfCustomers / 2; i++) {
            Customer temp = customers[i];
            customers[i] = customers[numberOfCustomers - 1 - i];
            customers[numberOfCustomers - 1 - i] = temp;
        }
        Integer choice = 0;
        boolean validChoice = false;

        // Checked exception handling with retry logic
        while (!validChoice) {
            try {
                printChoices();
                choice = InputService.readValidatedInteger(sc, 1, NUMBER_OF_CHOICES);
                validChoice = true;
                logger.info("User selected choice: {}", choice);
            } catch (InvalidNumericValueException e) {
                logger.warn("Invalid input received: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }

        try {
            switch (choice) {
                case 1 -> {
                    Rental mostExpensiveRental = RentalService.findMostExpensiveRental(rentals);
                    System.out.println("Most expensive rental:");
                    mostExpensiveRental.print();
                }
                case 2 -> {
                    Rental leastExpensiveRental = RentalService.findLeastExpensiveRental(rentals);
                    System.out.println("Least expensive rental:");
                    leastExpensiveRental.print();
                }
                case 3 -> {
                    System.out.print("Enter car brand: ");
                    String brand = sc.nextLine();
                    Rental[] rentalsByCarBrand = RentalService.findRentalsByCarBrand(brand, rentals);
                    RentalService.printFoundRentals(rentalsByCarBrand);
                }
                case 4 -> {
                    Customer customer = CustomerService.selectCustomer(sc, customers);
                    Rental[] rentalsByUser = RentalService.findRentalsByUser(customer, rentals);
                    RentalService.printFoundRentals(rentalsByUser);
                }
                case 5 -> CarService.printRentableCarsList(rentableCars);
                case 6 -> {
                    Person youngestPerson = people[0];
                    for(int i = 1; i < people.length; i++) {
                        if (people[i].getDateOfBirth().isAfter(youngestPerson.getDateOfBirth())) {
                            youngestPerson = people[i];
                        }
                    }
                    System.out.println("Youngest person: " + youngestPerson);
                }
                case 7 -> {
                    Person oldestPerson = people[0];
                    for(int i = 1; i < people.length; i++) {
                        if (people[i].getDateOfBirth().isBefore(oldestPerson.getDateOfBirth())) {
                            oldestPerson = people[i];
                        }
                    }
                    System.out.println("Oldest person: " + oldestPerson);
                }

                default -> System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            logger.error("Unexpected error during action execution", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
