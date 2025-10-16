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
            rental.print();
            ordinal++;
        }

        String confirmation;
        do {
            Integer choice;
            do {
                System.out.println("What do you want to do?");
                System.out.println("1) Print the most expensive rental");
                System.out.println("2) Print the cheapest rental");
                System.out.println("3) Search rentals by car brand");
                System.out.println("4) Search rentals made by a user");
                System.out.println("5) Check car availability");
                choice = sc.nextInt();
                sc.nextLine();
            } while(choice < 1 || choice > 5);

            switch (choice) {
                case 1:
                    Rental mostExpensiveRental = Rental.findMostExpensiveRental(rentals);
                    System.out.println("Most expensive rental:");
                    mostExpensiveRental.print();
                    break;
                case 2:
                    Rental leastExpensiveRental = Rental.findLeastExpensiveRental(rentals);
                    System.out.println("Least expensive rental:");
                    leastExpensiveRental.print();
                    break;
                case 3:
                    System.out.print("Enter car brand: ");
                    String brand = sc.nextLine();
                    Rental[] rentalsByCarBrand = Rental.findRentalsByCarBrand(brand, rentals);
                    Rental.printFoundRentals(rentalsByCarBrand);
                    break;
                case 4:
                    User user = selectUser(sc, users);
                    Rental[] rentalsByUser = Rental.findRentalsByUser(user, rentals);
                    Rental.printFoundRentals(rentalsByUser);
                    break;
                case 5:
                    printCarsList(cars);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

            System.out.print("Do you want to continue (Y/N)? ");
            confirmation = sc.nextLine();
        } while (confirmation.equalsIgnoreCase("y"));

        sc.close();
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

        Integer selection;
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

        Integer selection;
        do {
            System.out.print("Select car> ");
            selection = sc.nextInt();
            sc.nextLine();
            if (selection >= 1 && selection <= cars.length && !cars[selection-1].getAvailable()) {
                System.out.println("Car #" + selection + " is not available");
            }
        }  while (selection < 1 || selection > cars.length || !cars[selection-1].getAvailable());

        cars[selection-1].setAvailable(false);
        return cars[selection-1];
    }

    private static void printCarsList(Car[] cars) {
        Integer ordinal = 1;
        System.out.println("Car availability:");
        for(Car car : cars) {
            System.out.println(ordinal + ") " + car.getYear() + " " + car.getBrand() + " " + car.getModel() + "(EUR" + car.getDailyPrice() + "/day): " + (car.getAvailable() ? "AVAILABLE" : "NOT AVAILABLE"));
            ordinal++;
        }
    }
}
