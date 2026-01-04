package dev.jacob6707.carrentalsystemjavafx.util;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;

import java.util.List;
import java.util.function.Predicate;

/**
 * Utility class for handling rentals.
 */
public class RentalUtils {
    private RentalUtils() {}

    /**
     * Searches for rentals based on the search term.
     * @param rentals The list of rentals to search through
     * @param searchTerm The search term
     * @return A list of rentals that match the search term
     */
    public static List<Rental> searchRentals(List<Rental> rentals, String searchTerm) {
        return rentals.stream().filter(rentalFilter(searchTerm)).toList();
    }

    /**
     * Builds predicate matching rentals by vehicle, customer, or fields
     */
    public static Predicate<Rental> rentalFilter(String searchTerm) {
        return rental ->
                VehicleUtils.vehicleFilter(searchTerm).test(rental.getVehicle())
                        || CustomerUtils.customerFilter(searchTerm).test(rental.getCustomer())
                        || rental.getId().toString().toLowerCase().contains(searchTerm.toLowerCase())
                        || rental.getStartDate().toString().toLowerCase().contains(searchTerm.toLowerCase())
                        || rental.getEndDate().toString().toLowerCase().contains(searchTerm.toLowerCase())
                        || String.valueOf(rental.getPrice()).contains(searchTerm);
    }
}
