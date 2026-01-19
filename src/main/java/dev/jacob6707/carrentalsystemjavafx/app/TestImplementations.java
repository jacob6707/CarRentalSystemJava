package dev.jacob6707.carrentalsystemjavafx.app;

import dev.jacob6707.carrentalsystemjavafx.model.rental.RentalDTO;
import dev.jacob6707.carrentalsystemjavafx.model.vehicle.VehicleDTO;
import dev.jacob6707.carrentalsystemjavafx.repository.*;

@SuppressWarnings("all")
public class TestImplementations {
    private TestImplementations() {}

    static void main() {
        CustomersRepository customersRepository = CustomersRepository.getInstance();
        DatabaseCustomersRepository databaseCustomersRepository = new DatabaseCustomersRepository();
        customersRepository.findAll().forEach(databaseCustomersRepository::save);
        System.out.println("Saved " + customersRepository.findAll().size() + " customers to database");

        VehiclesRepository vehiclesRepository = VehiclesRepository.getInstance();
        DatabaseVehiclesRepository databaseVehiclesRepository = new DatabaseVehiclesRepository();
        vehiclesRepository.findAll().stream().map(VehicleDTO::new).forEach(databaseVehiclesRepository::save);
        System.out.println("Saved " + vehiclesRepository.findAll().size() + " vehicles to database");

        RentalsRepository rentalsRepository = RentalsRepository.getInstance();
        DatabaseRentalsRepository databaseRentalsRepository = new DatabaseRentalsRepository();
        rentalsRepository.findAll().stream().map(RentalDTO::new).forEach(databaseRentalsRepository::save);
        System.out.println("Saved " + rentalsRepository.findAll().size() + " rentals to database");

        databaseRentalsRepository.findAll().forEach(System.out::println);
    }
}
