package dev.jacob6707.carrentalsystemjavafx.util;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;

import java.util.List;
import java.util.function.Predicate;

public class RentalUtils {
    private RentalUtils() {}

    public static List<Rental> searchRentals(List<Rental> rentals, String searchTerm) {
        return rentals.stream().filter(rentalFilter(searchTerm)).toList();
    }

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
