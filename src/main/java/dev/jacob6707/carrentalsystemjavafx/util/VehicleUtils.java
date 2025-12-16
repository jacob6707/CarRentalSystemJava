package dev.jacob6707.carrentalsystemjavafx.util;

import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

public class VehicleUtils {
    private VehicleUtils() {}

    public static List<Vehicle> searchVehicles(List<Vehicle> vehicles, String searchTerm) {
        return vehicles.stream().filter(vehicleFilter(searchTerm)).toList();
    }

    public static Predicate<Vehicle> vehicleFilter(String searchTerm) {
        return vehicle ->
                vehicle.getBrand().toLowerCase().contains(searchTerm.toLowerCase())
                        || vehicle.getModel().toLowerCase().contains(searchTerm.toLowerCase())
                        || vehicle.getLicensePlate().toLowerCase().contains(searchTerm.toLowerCase())
                        || String.valueOf(vehicle.getYear()).contains(searchTerm)
                        || String.valueOf(vehicle.getMileage()).contains(searchTerm)
                        || vehicle.getId().toString().toLowerCase().contains(searchTerm.toLowerCase());
    }

    public static boolean validateInput(String type, String brand, String model, String licensePlate, Integer year, Integer mileage, BigDecimal dailyPrice) {
        if (type == null || brand == null || model == null || licensePlate == null || year == null || mileage == null || dailyPrice == null) { return false; }
        if (type.isBlank() || brand.isBlank() || model.isBlank() || licensePlate.isBlank() || year.equals(0) || mileage.equals(0) || dailyPrice.equals(BigDecimal.ZERO)) { return false; }
        if (!(type.equals("SUV") || type.equals("Car"))) return false;
        return dailyPrice.compareTo(BigDecimal.ZERO) >= 0 && mileage.compareTo(0) >= 0 && year.compareTo(1900) >= 0 && year.compareTo(2030) <= 0;
    }
}
