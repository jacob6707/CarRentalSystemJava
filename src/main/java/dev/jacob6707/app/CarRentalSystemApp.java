package dev.jacob6707.app;

import dev.jacob6707.entities.Car;
import dev.jacob6707.entities.Rental;
import dev.jacob6707.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CarRentalSystemApp {

    private static final Integer NUMBER_OF_USERS = 5;
    private static final Integer NUMBER_OF_CARS = 5;
    private static final Integer NUMBER_OF_RENTALS = 5;

    static void main() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Car Rental System");

        User[] users = generateUsers(sc, NUMBER_OF_USERS);
        Car[] cars = generateCars(sc, NUMBER_OF_CARS);
        Rental[] rentals = new Rental[NUMBER_OF_RENTALS];

        for (int i = 0; i < NUMBER_OF_RENTALS; i++) {
            System.out.println("===RENTAL #" + (i + 1) + " INPUT===");
            User user = selectUser(sc, users);
            Car car = selectCar(sc, cars);
            System.out.print("Enter how many days the car will be rented for: ");
            Integer numOfDays = sc.nextInt();
            sc.nextLine();
            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = start.plusDays(numOfDays);
            rentals[i] = new Rental(user, car, start, end);
        }

        Integer ordinal = 1;
        for (Rental rental : rentals) {
            System.out.println("=====RENTAL #" + ordinal + "=====");
            System.out.println("Rented by: " + rental.getUser().getFirstName() + " " + rental.getUser().getLastName());
            System.out.println("Car: " + rental.getCar().getYear() + " " + rental.getCar().getBrand() + " " + rental.getCar().getModel());
            System.out.println("Rented until: " + rental.getEndDate());
            System.out.println("Price: EUR" + rental.getPrice());
            ordinal++;
        }
    }

    private static User[] generateUsers(Scanner sc, Integer count) {
        User[] users = new User[count];
        for (Integer i = 0; i < count; i++) {
            System.out.println("===USER #" + (i+1) + " INPUT===");
            System.out.print("Enter user #" + (i+1) + "'s first name: ");
            String name = sc.nextLine();
            System.out.print("Enter user #" + (i+1) + "'s last name: ");
            String lastName = sc.nextLine();
            System.out.print("Enter user #" + (i+1) + "'s email: ");
            String email = sc.nextLine();
            users[i] = new User(name, lastName, email);
        }
        return users;
    }

    private static Car[] generateCars(Scanner sc, Integer count) {
        Car[] cars = new Car[count];
        for (Integer i = 0; i < count; i++) {
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

    private static User selectUser(Scanner sc, User[] users) {
        Integer ordinal = 1;

        System.out.println("Available users:");
        for (User user : users) {
            System.out.println(ordinal + ") " + user.getFirstName() + " " + user.getLastName() + "(" + user.getEmail() + ")");
            ordinal++;
        }

        Integer selection = 0;
        do {
            System.out.print("Select user> ");
            selection = sc.nextInt();
            sc.nextLine();
        } while (selection < 1 || selection > users.length);

        return users[selection-1];
    }

    private static Car selectCar(Scanner sc, Car[] cars) {
        Integer ordinal = 1;

        System.out.println("Available Cars:");
        for (Car car : cars) {
            if (car.getAvailable()) {
                System.out.println(ordinal + ") " + car.getYear() + " " + car.getBrand() + " " + car.getModel() + "(EUR" + car.getDailyPrice() + "/day)");
            }
            ordinal++;
        }

        Integer selection = 0;
        do {
            System.out.print("Select car> ");
            selection = sc.nextInt();
            sc.nextLine();
            if (!cars[selection-1].getAvailable()) {
                System.out.println("Car #" + selection + " is not available");
            }
        }  while (selection < 1 || selection > cars.length || !cars[selection-1].getAvailable());

        cars[selection-1].setAvailable(false);
        return cars[selection-1];
    }
}
