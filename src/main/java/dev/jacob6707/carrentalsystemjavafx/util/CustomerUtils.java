package dev.jacob6707.carrentalsystemjavafx.util;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;

/**
 * Utility class for handling customers.
 */
public class CustomerUtils {
    private CustomerUtils() {}

    /**
     * Searches for customers based on the search term.
     * @param customers The list of customers to search through
     * @param searchTerm The search term
     * @return A list of customers that match the search term
     */
    public static List<Customer> searchCustomers(List<Customer> customers, String searchTerm) {
        return customers.stream().filter(customerFilter(searchTerm)).toList();
    }


    /**
     * Builds case‑insensitive predicate matching customer properties against the search term
     */
    public static Predicate<Customer> customerFilter(String searchTerm) {
        return customer ->
                customer.getFirstName().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getLastName().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getEmail().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getPhoneNumber().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getLocation().toString().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getIdNumber().toLowerCase().contains(searchTerm.toLowerCase())
                        || customer.getDateOfBirth().toString().toLowerCase().contains(searchTerm.toLowerCase())
                        || String.valueOf(customer.getDiscountRate()).contains(searchTerm)
                        || customer.getId().toString().toLowerCase().contains(searchTerm.toLowerCase());
    }

    /**
     * Validates customer input fields for non‑null, blank, format
     */
    public static boolean validateInput(String firstName, String lastName, String email, String phoneNumber, String idNumber, String location, String dateOfBirth) {
        if (firstName == null || lastName == null || email == null || phoneNumber == null || idNumber == null || location == null || dateOfBirth == null) { return false; }
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || phoneNumber.isBlank() || idNumber.isBlank() || location.isBlank() || dateOfBirth.isBlank()) { return false; }
        StringTokenizer locationTokenizer = new StringTokenizer(location, ",");
        if (locationTokenizer.countTokens() != 4) { return false; }
        try {
            LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException _) { return false; }
        return true;
    }
}
