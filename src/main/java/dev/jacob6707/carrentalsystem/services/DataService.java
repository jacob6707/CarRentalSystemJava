package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Customer;
import dev.jacob6707.carrentalsystem.entities.Employee;
import dev.jacob6707.carrentalsystem.entities.Rental;
import dev.jacob6707.carrentalsystem.entities.Vehicle;
import dev.jacob6707.carrentalsystem.repository.JsonRepository;

import java.util.List;

public class DataService {
    public static void printAllRepositories(JsonRepository<Customer> customerRepository, JsonRepository<Employee> employeeRepository, JsonRepository<Vehicle> vehicleRepository, JsonRepository<Rental> rentalRepository) {
        List<Customer> customers = customerRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Rental> rentals = rentalRepository.findAll();
        System.out.println(customers.size() + " Customers:");
        customers.forEach(System.out::println);
        System.out.println(employees.size() + " Employees:");
        employees.forEach(System.out::println);
        System.out.println(vehicles.size() + " Vehicles:");
        vehicles.forEach(System.out::println);
        System.out.println(rentals.size() + " Rentals:");
        rentals.forEach(Rental::print);
    }
}
