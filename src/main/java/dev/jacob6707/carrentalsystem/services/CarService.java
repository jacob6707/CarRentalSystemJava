package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Car;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.SUV;
import dev.jacob6707.carrentalsystem.entities.Vehicle;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.NoRentableFoundException;
import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Service that handles vehicle generation and selection.
 *
 * @see Car
 * @see SUV
 */
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    public static Car generateCar(Scanner sc) {
        System.out.print("Enter car's brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter car's model: ");
        String model = sc.nextLine();
        System.out.print("Enter car's year of manufacture: ");
        Integer year = sc.nextInt();
        sc.nextLine();
        BigDecimal dailyPrice = null;
        while (dailyPrice == null) {
            try {
                dailyPrice = InputService.readPositiveBigDecimal(sc, "Enter car's daily price: ");
            } catch (InvalidNumericValueException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.print("Enter car's license plate:");
        String licensePlate = sc.nextLine();

        return new Car.CarBuilder()
                .licensePlate(licensePlate)
                .brand(brand)
                .model(model)
                .year(year)
                .dailyPrice(dailyPrice)
                .build();
    }

    public static SUV generateSUV(Scanner sc) {
        System.out.print("Enter SUV's brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter SUV's model: ");
        String model = sc.nextLine();
        System.out.print("Enter SUV's year of manufacture: ");
        Integer year = sc.nextInt();
        sc.nextLine();
        BigDecimal dailyPrice = null;
        while (dailyPrice == null) {
            try {
                dailyPrice = InputService.readPositiveBigDecimal(sc, "Enter SUV's daily price: ");
            } catch (InvalidNumericValueException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.print("Enter SUV's license plate:");
        String licensePlate = sc.nextLine();

        return new SUV.SUVBuilder()
                .licensePlate(licensePlate)
                .brand(brand)
                .model(model)
                .year(year)
                .dailyPrice(dailyPrice)
                .build();
    }

    /**
     * Selects a car from the list of rentable cars.
     *
     * @param sc Scanner object for reading user input
     * @param rentableCars Array of rentable cars
     * @return The selected rentable car
     */
    public static Rentable selectCar(Scanner sc, List<? extends Rentable> rentableCars) {
        if (rentableCars.isEmpty()) throw new NoRentableFoundException("No rentable cars available.");
        Integer ordinal = 1;

        System.out.println("Rentable Cars:");
        for (Rentable rentable : rentableCars) {
            if (rentable.isAvailable()) {
                if (rentable instanceof Car car) {
                    System.out.println(ordinal + ") " + car.getYear() + " " + car.getBrand() + " " + car.getModel() + "(EUR" + car.getDailyPrice() + "/day)");
                } else if (rentable instanceof SUV suv) {
                    System.out.println(ordinal + ") " + suv.getYear() + " " + suv.getBrand() + " " + suv.getModel() + "(EUR" + suv.getDailyPrice() + "/day)");
                }
            }
            ordinal++;
        }

        int selection;
        do {
            System.out.print("Select car> ");
            selection = sc.nextInt();
            sc.nextLine();
            try {
                rentableCars.get(selection - 1).rent();
                break;
            } catch (VehicleBookingException e) {
                logger.error("Vehicle {} is already booked.", rentableCars.get(selection - 1).toString(), e);
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.error("Invalid selection: {}", selection, e);
            }
        } while (selection < 1 || selection > rentableCars.size() || !rentableCars.get(selection - 1).isAvailable());

        return rentableCars.get(selection - 1);
    }

    /**
     * Prints the list of rentable cars.
     *
     * @param rentableCars The array of rentable cars
     */
    public static void printRentableCarsList(List<? extends Rentable> rentableCars) {
        if (rentableCars.isEmpty()) {
            System.out.println("No rentable cars available.");
            return;
        }
        Integer ordinal = 1;
        System.out.println("Car availability:");
        for(Rentable rentableCar : rentableCars) {
            if (rentableCar instanceof Car car) {
                System.out.println(ordinal + ") " + car.getYear() + " " + car.getBrand() + " " + car.getModel() + "(EUR" + car.getDailyPrice() + "/day) " + (car.isAvailable() ? "AVAILABLE" : "NOT AVAILABLE"));
            }
            else if (rentableCar instanceof SUV suv) {
                System.out.println(ordinal + ") " + suv.getYear() + " " + suv.getBrand() + " " + suv.getModel() + "(EUR" + suv.getDailyPrice() + "/day) " + (suv.isAvailable() ? "AVAILABLE" : "NOT AVAILABLE"));
            }
            ordinal++;
        }
    }
}
