package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Car;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.SUV;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.NoRentablesFoundException;
import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Service that handles vehicle generation and selection.
 *
 * @see Car
 * @see SUV
 */
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    /**
     * Generates a list of cars through user input
     *
     * @param sc Scanner object for reading user input
     * @param numberOfCars Number of cars to generate
     * @return Array of generated cars
     * @throws InvalidNumericValueException if the number of cars is less than 1
     */
    public static Car[] generateCars(Scanner sc, Integer numberOfCars) throws InvalidNumericValueException {
        if (numberOfCars < 1) throw new InvalidNumericValueException("Number of cars must be greater than 0.");
        Car[] cars = new Car[numberOfCars];
        for (Integer i = 0; i < numberOfCars; i++) {
            System.out.println("===CAR #" + (i+1) + " INPUT===");
            System.out.print("Enter car #" + (i+1) + "'s brand: ");
            String brand = sc.nextLine();
            System.out.print("Enter car #" + (i+1) + "'s model: ");
            String model = sc.nextLine();
            System.out.print("Enter car #" + (i+1) + "'s year of manufacture: ");
            Integer year = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter car #" + (i+1) + "'s daily price: ");
            BigDecimal dailyPrice = sc.nextBigDecimal();
            sc.nextLine();
            cars[i] = new Car(brand,model,year,dailyPrice,true);
        }
        return cars;
    }

    /**
     * Generates a list of SUVs through user input
     *
     * @param sc Scanner object for reading user input
     * @param numberOfSUVs Number of SUVs to generate
     * @return Array of generated SUVs
     * @throws InvalidNumericValueException if the number of SUVs is less than 1
     */
    public static SUV[] generateSUVs(Scanner sc, Integer numberOfSUVs) throws InvalidNumericValueException {
        if (numberOfSUVs < 1) throw new InvalidNumericValueException("Number of SUVs must be greater than 0.");
        SUV[] cars = new SUV[numberOfSUVs];
        for (Integer i = 0; i < numberOfSUVs; i++) {
            System.out.println("===SUV #" + (i+1) + " INPUT===");
            System.out.print("Enter SUV #" + (i+1) + "'s brand: ");
            String brand = sc.nextLine();
            System.out.print("Enter SUV #" + (i+1) + "'s model: ");
            String model = sc.nextLine();
            System.out.print("Enter SUV #" + (i+1) + "'s year of manufacture: ");
            Integer year = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter SUV #" + (i+1) + "'s daily price: ");
            BigDecimal dailyPrice = sc.nextBigDecimal();
            sc.nextLine();
            cars[i] = new SUV(brand,model,year,dailyPrice,true);
        }
        return cars;
    }

    /**
     * Selects a car from the list of rentable cars.
     *
     * @param sc Scanner object for reading user input
     * @param rentableCars Array of rentable cars
     * @return The selected rentable car
     */
    public static Rentable selectCar(Scanner sc, Rentable[] rentableCars) {
        if (rentableCars.length == 0) throw new NoRentablesFoundException("No rentable cars available.");
        Integer ordinal = 1;

        System.out.println("Rentable Cars:");
        for (Rentable rentable : rentableCars) {
            if (rentable.isAvailable()) {
                if (rentable instanceof Car car) {
                    System.out.println(ordinal + ") " + car.getYear() + " " + car.getBrand() + " " + car.getModel() + "(EUR" + car.getDailyPrice() + "/day)");
                }
                else if (rentable instanceof SUV suv) {
                    System.out.println(ordinal + ") " + suv.getYear() + " " + suv.getBrand() + " " + suv.getModel() + "(EUR" + suv.getDailyPrice() + "/day)");
                }
            }
            ordinal++;
        }

        Integer selection;
        do {
            System.out.print("Select car> ");
            selection = sc.nextInt();
            sc.nextLine();
            try {
                rentableCars[selection-1].rent();
                break;
            } catch (VehicleBookingException e) {
                logger.error("Vehicle {} is already booked.", rentableCars[selection - 1].toString(), e);
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.error("Invalid selection: {}", selection, e);
            }
        } while (selection < 1 || selection > rentableCars.length || !rentableCars[selection-1].isAvailable());

        return rentableCars[selection-1];
    }

    /**
     * Prints the list of rentable cars.
     *
     * @param rentableCars The array of rentable cars
     */
    public static void printRentableCarsList(Rentable[] rentableCars) {
        if (rentableCars.length == 0) throw new NoRentablesFoundException("No rentable cars available.");
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
