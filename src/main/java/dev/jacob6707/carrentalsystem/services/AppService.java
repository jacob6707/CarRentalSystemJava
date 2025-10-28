package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.entities.Person;
import dev.jacob6707.carrentalsystem.entities.Rentable;
import dev.jacob6707.carrentalsystem.entities.Rental;

import java.util.Scanner;

public class AppService {
    public static final Integer NUMBER_OF_CHOICES = 7;

    public static void printChoices() {
        System.out.println("What do you want to do?");
        System.out.println("1) Print the most expensive rental");
        System.out.println("2) Print the cheapest rental");
        System.out.println("3) Search rentals by car brand");
        System.out.println("4) Search rentals made by a user");
        System.out.println("5) Check car availability");
        System.out.println("6) Print youngest person");
        System.out.println("7) Print oldest person");
    }

    public static void chooseAction(Scanner sc, Person[] people, Rentable[] rentableCars, Rental[] rentals) {
        // extract customers from array of people
        Integer numberOfCustomers = 0;
        for (Person person : people) {
            if (person instanceof Customer) {
                numberOfCustomers++;
            }
        }
        Customer[] customers = new Customer[numberOfCustomers];
        for (Person person : people) {
            if (person instanceof Customer customer) {
                customers[numberOfCustomers - 1] = customer;
                numberOfCustomers--;
            }
        }
        // reverse the array
        for (int i = 0; i < numberOfCustomers / 2; i++) {
            Customer temp = customers[i];
            customers[i] = customers[numberOfCustomers - 1 - i];
            customers[numberOfCustomers - 1 - i] = temp;
        }
        Integer choice;
        do {
            printChoices();
            choice = sc.nextInt();
            sc.nextLine();
        } while(choice < 1 || choice > NUMBER_OF_CHOICES);

        switch (choice) {
            case 1 -> {
                Rental mostExpensiveRental = Rental.findMostExpensiveRental(rentals);
                System.out.println("Most expensive rental:");
                mostExpensiveRental.print();
            }
            case 2 -> {
                Rental leastExpensiveRental = Rental.findLeastExpensiveRental(rentals);
                System.out.println("Least expensive rental:");
                leastExpensiveRental.print();
            }
            case 3 -> {
                System.out.print("Enter car brand: ");
                String brand = sc.nextLine();
                Rental[] rentalsByCarBrand = Rental.findRentalsByCarBrand(brand, rentals);
                Rental.printFoundRentals(rentalsByCarBrand);
            }
            case 4 -> {
                Customer customer = CustomerService.selectCustomer(sc, customers);
                Rental[] rentalsByUser = Rental.findRentalsByUser(customer, rentals);
                Rental.printFoundRentals(rentalsByUser);
            }
            case 5 -> CarService.printRentableCarsList(rentableCars);
            case 6 -> {
                Person youngestPerson = people[0];
                for(int i = 1; i < people.length; i++) {
                    if (people[i].getDateOfBirth().isAfter(youngestPerson.getDateOfBirth())) {
                        youngestPerson = people[i];
                    }
                }
                System.out.println("Youngest person: " + youngestPerson);
            }
            case 7 -> {
                Person oldestPerson = people[0];
                for(int i = 1; i < people.length; i++) {
                    if (people[i].getDateOfBirth().isBefore(oldestPerson.getDateOfBirth())) {
                        oldestPerson = people[i];
                    }
                }
                System.out.println("Oldest person: " + oldestPerson);
            }

            default -> System.out.println("Invalid choice");
        }

    }
}
