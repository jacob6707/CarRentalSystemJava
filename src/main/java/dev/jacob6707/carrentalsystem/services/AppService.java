package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Service that handles application logic.
 */
public class AppService {
    public static final Integer NUMBER_OF_CHOICES = 9;
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
        System.out.println("8) Sort rentals by price");
        System.out.println("9) Sort rentals by date of return");
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
    public static void chooseAction(Scanner sc, Map<PersonRole, List<Person>> people, List<Rentable> rentableCars, List<Rental> rentals) {
        logger.debug("Processing {} people, {} rentable cars, {} rentals", people.size(), rentableCars.size(), rentals.size());

        List<Customer> customers = people.get(PersonRole.CUSTOMER).stream()
                .filter(p -> p instanceof Customer)
                .map(p -> (Customer) p)
                .toList();
        logger.debug("Found {} customers", customers.size());

        List<Person> peopleList = people.values().stream().flatMap(Collection::stream).toList();

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
                    Optional<Rental> mostExpensiveRental = RentalService.findMostExpensiveRental(rentals);
                    if (mostExpensiveRental.isEmpty()) {
                        System.out.println("No rentals found");
                        return;
                    }
                    System.out.println("Most expensive rental:");
                    mostExpensiveRental.get().print();
                }
                case 2 -> {
                    Optional<Rental> leastExpensiveRental = RentalService.findLeastExpensiveRental(rentals);
                    if (leastExpensiveRental.isEmpty()) {
                        System.out.println("No rentals found");
                        return;
                    }
                    System.out.println("Least expensive rental:");
                    leastExpensiveRental.get().print();
                }
                case 3 -> {
                    System.out.print("Enter car brand: ");
                    String brand = sc.nextLine();
                    List<Rental> rentalsByCarBrand = RentalService.findRentalsByCarBrand(brand, rentals);
                    RentalService.printFoundRentals(rentalsByCarBrand);
                }
                case 4 -> {
                    Customer customer = CustomerService.selectCustomer(sc, customers);
                    List<Rental> rentalsByUser = RentalService.findRentalsByUser(customer, rentals);
                    RentalService.printFoundRentals(rentalsByUser);
                }
                case 5 -> CarService.printRentableCarsList(rentableCars);
                case 6 -> {
                    Optional<? extends Person> youngestPerson = PersonService.findYoungestPerson(peopleList);
                    if (youngestPerson.isEmpty()) {
                        System.out.println("No people found");
                        return;
                    }
                    System.out.println("Youngest person: " + youngestPerson.get());
                }
                case 7 -> {
                    Optional<? extends Person> oldestPerson = PersonService.findOldestPerson(peopleList);
                    if (oldestPerson.isEmpty()) {
                        System.out.println("No people found");
                        return;
                    }
                    System.out.println("Oldest person: " + oldestPerson.get());
                }
                case 8 -> {
                    rentals.sort(Comparator.comparing(Rental::getPrice));
                    RentalService.printAllRentals(rentals);
                }
                case 9 -> {
                    rentals.sort((r1, r2) -> r1.getEndDate().compareTo(r2.getEndDate()));
                    RentalService.printAllRentals(rentals);
                }

                default -> System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            logger.error("Unexpected error during action execution", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
