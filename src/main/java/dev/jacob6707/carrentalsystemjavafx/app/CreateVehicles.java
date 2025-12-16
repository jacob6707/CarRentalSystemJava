package dev.jacob6707.carrentalsystemjavafx.app;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;
import dev.jacob6707.carrentalsystemjavafx.repository.CustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.repository.RentalsRepository;
import dev.jacob6707.carrentalsystemjavafx.repository.VehiclesRepository;

import java.time.LocalDateTime;
import java.util.List;

public class CreateVehicles {
    private CreateVehicles() {}

    static void main() {
        List<Vehicle> vehicles = VehiclesRepository.getInstance().findAll();
        List<Customer> customers = CustomersRepository.getInstance().findAll();

        RentalsRepository.getInstance().save(new Rental(customers.get(0), vehicles.get(3), LocalDateTime.now(), LocalDateTime.now().plusDays(7)));
    }
}
