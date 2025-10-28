package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Car;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.SUV;

import java.math.BigDecimal;
import java.util.Scanner;

public class CarService {
    public static Car[] generateCars(Scanner sc, Integer numberOfCars) {
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

    public static SUV[] generateSUVs(Scanner sc, Integer numberOfSUVs) {
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


    public static Rentable selectCar(Scanner sc, Rentable[] rentableCars) {
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
            if (selection >= 1 && selection <= rentableCars.length && !rentableCars[selection-1].isAvailable()) {
                System.out.println("Car #" + selection + " is not available");
            }
        }  while (selection < 1 || selection > rentableCars.length || !rentableCars[selection-1].isAvailable());

        rentableCars[selection-1].rent();
        return rentableCars[selection-1];
    }

    public static void printRentableCarsList(Rentable[] rentableCars) {
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
