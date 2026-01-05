package dev.jacob6707.carrentalsystemjavafx.util;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

/**
 * Utility class for handling vehicles.
 */
public class VehicleUtils {
    private VehicleUtils() {}

    /**
     * Searches for vehicles based on the search term.
     * @param vehicles The list of vehicles to search through
     * @param searchTerm The search term
     * @return A list of vehicles that match the search term
     */
    public static List<Vehicle> searchVehicles(List<Vehicle> vehicles, String searchTerm) {
        return vehicles.stream().filter(vehicleFilter(searchTerm)).toList();
    }

    /**
     * Filters vehicles to only include available vehicles by searching through a list of active rentals and filtering any vehicle present in them.
     *
     * @param vehicles The list of vehicles to filter
     * @param rentals The list of rentals
     * @return An immutable list of available vehicles
     */
    public static List<Vehicle> getAvailableVehicles(List<Vehicle> vehicles, List<Rental> rentals) {
        List<Vehicle> currentlyRentedVehicles = RentalUtils.getActiveRentals(rentals).stream().map(Rental::getVehicle).toList();
        return vehicles.stream().filter(vehicle -> !currentlyRentedVehicles.contains(vehicle)).toList();
    }

    /**
     * Builds case‑insensitive predicate matching vehicle properties
     */
    public static Predicate<Vehicle> vehicleFilter(String searchTerm) {
        return vehicle ->
                vehicle.getBrand().toLowerCase().contains(searchTerm.toLowerCase())
                        || vehicle.getModel().toLowerCase().contains(searchTerm.toLowerCase())
                        || vehicle.getLicensePlate().toLowerCase().contains(searchTerm.toLowerCase())
                        || String.valueOf(vehicle.getYear()).contains(searchTerm)
                        || String.valueOf(vehicle.getMileage()).contains(searchTerm)
                        || vehicle.getId().toString().toLowerCase().contains(searchTerm.toLowerCase());
    }

    /**
     * Validates vehicle input criteria are satisfied
     */
    public static boolean validateInput(String type, String brand, String model, String licensePlate, Integer year, Integer mileage, BigDecimal dailyPrice) {
        if (type == null || brand == null || model == null || licensePlate == null || year == null || mileage == null || dailyPrice == null) { return false; }
        if (type.isBlank() || brand.isBlank() || model.isBlank() || licensePlate.isBlank() || year.equals(0) || mileage.equals(0) || dailyPrice.equals(BigDecimal.ZERO)) { return false; }
        if (!(type.equals("SUV") || type.equals("Car"))) return false;
        return dailyPrice.compareTo(BigDecimal.ZERO) >= 0 && mileage.compareTo(0) >= 0 && year.compareTo(1900) >= 0 && year.compareTo(2030) <= 0;
    }
}
