package dev.jacob6707.carrentalsystem.app;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.services.AppService;
import dev.jacob6707.carrentalsystem.services.CarService;
import dev.jacob6707.carrentalsystem.services.CustomerService;
import dev.jacob6707.carrentalsystem.services.RentalService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CarRentalSystemApp {

    static void main() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Car Rental System");

        System.out.println("Insert information of the first employee:");
        System.out.print("First Name: ");
        String employeeFirstName = sc.nextLine();
        System.out.print("Last Name: ");
        String employeeLastName = sc.nextLine();
        System.out.print("Email: ");
        String employeeEmail = sc.nextLine();
        System.out.print("Date of birth (dd.MM.yyyy): ");
        LocalDate dateOfBirth = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Salary: ");
        BigDecimal employeeSalary = sc.nextBigDecimal();
        sc.nextLine();

        Employee employee = new Employee.EmployeeBuilder()
                .firstName(employeeFirstName)
                .lastName(employeeLastName)
                .email(employeeEmail)
                .dateOfBirth(dateOfBirth)
                .salary(employeeSalary)
                .build();

        System.out.print("Enter number of customers: ");
        Integer numberOfCustomers = sc.nextInt();
        sc.nextLine();
        Customer[] customers = CustomerService.generateCustomers(sc, numberOfCustomers);
        Person[] people = new Person[customers.length + 1];
        people[0] = employee;
        System.arraycopy(customers, 0, people, 1, customers.length);
        System.out.print("Enter number of cars: ");
        Integer numberOfCars = sc.nextInt();
        sc.nextLine();
        Car[] cars = CarService.generateCars(sc, numberOfCars);
        System.out.print("Enter number of SUVs: ");
        Integer numberOfSUVs = sc.nextInt();
        sc.nextLine();
        SUV[] suvs = CarService.generateSUVs(sc, numberOfSUVs);
        Rentable[] rentables = new Rentable[suvs.length + cars.length];
        System.arraycopy(suvs, 0, rentables, 0, suvs.length);
        System.arraycopy(cars, 0, rentables, suvs.length, cars.length);
        System.out.print("Enter number of rentals: ");
        Integer numberOfRentals = sc.nextInt();
        sc.nextLine();
        Rental[] rentals = RentalService.generateRentals(sc, customers, rentables, numberOfRentals);

        RentalService.printAllRentals(rentals);

        String confirmation;
        do {
            AppService.chooseAction(sc, people, rentables, rentals);

            System.out.print("Do you want to continue (Y/N)? ");
            confirmation = sc.nextLine();
        } while (confirmation.equalsIgnoreCase("y"));

        sc.close();
    }
}
