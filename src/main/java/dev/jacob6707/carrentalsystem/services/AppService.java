package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.*;
import dev.jacob6707.carrentalsystem.exception.InvalidNumericValueException;
import dev.jacob6707.carrentalsystem.exception.VehicleBookingException;
import dev.jacob6707.carrentalsystem.repository.AuditRepo;
import dev.jacob6707.carrentalsystem.repository.BackupRepo;
import dev.jacob6707.carrentalsystem.repository.JsonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;



/**
 * Service that handles application logic.
 */
public class AppService {
    public static final Integer NUMBER_OF_CHOICES = 9;
    private static final Logger logger = LoggerFactory.getLogger(AppService.class);

    private enum Choice {
        ADD_CAR("Add a car to the repository"),
        ADD_SUV("Add an SUV to the repository"),
        ADD_EMPLOYEE("Add an employee to the repository"),
        ADD_CUSTOMER("Add a customer to the repository"),
        MAKE_RENTAL("Make a rental"),
        CREATE_BACKUP("Create a backup of the database"),
        RESTORE_BACKUP("Restore database from backup"),
        PRINT_DATA("Print all data from the database"),
        PRINT_RENTABLE_CARS("Print all rentable cars"),
        PRINT_RENTALS("Print all rentals"),
        SEARCH_RENTALS_BY_BRAND("Search rentals by car brand"),
        SEARCH_RENTALS_BY_USER("Search rentals made by a user"),
        PRINT_YOUNGEST_PERSON("Print youngest person"),
        PRINT_OLDEST_PERSON("Print oldest person"),
        SORT_RENTALS_BY_PRICE("Sort rentals by price"),
        SORT_RENTALS_BY_DATE("Sort rentals by date of return"),
        EXIT("Exit the application");

        final String name;
        Choice(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Selects an operation to do:
     */
    private static Choice selectOperation(Scanner sc) {
        Choice[] choices = Choice.values();
        System.out.println("What would you like to do?");
        for (int i = 0; i < choices.length; i++) {
            System.out.println(i + 1 + ") " + choices[i].getName());
        }
        int id = sc.nextInt();
        sc.nextLine();
        return choices[id - 1];
    }

    /**
     * Handles the main application logic for user interaction.
     * Presents menu choices and executes the selected action.
     * Includes exception handling for invalid input with a retry mechanism.
     *
     * @param sc Scanner object for reading user input
     * @param customersRepository Repository for storing customer data
     * @param employeeRepository Repository for storing employee data
     * @param rentalRepository Repository for storing rental data
     * @param vehicleRepository Repository for storing vehicle data
     */
    public static void chooseAction(Scanner sc, Employee employee, JsonRepository<Customer> customersRepository, JsonRepository<Employee> employeeRepository, JsonRepository<Vehicle> vehicleRepository, JsonRepository<Rental> rentalRepository) {
        logger.debug("Processing {} customers, {} employees, {} vehicles, {} rentals", customersRepository.findAll().size(), employeeRepository.findAll().size(), vehicleRepository.findAll().size(), rentalRepository.findAll().size());

        BackupRepo backupRepo = new BackupRepo(List.of(customersRepository, employeeRepository, vehicleRepository, rentalRepository));

        Choice choice = Choice.EXIT;
        boolean validChoice = false;

        // Checked exception handling with retry logic
        while (!validChoice) {
            try {
                choice = selectOperation(sc);
                if (choice != null) validChoice = true;
                logger.info("User selected choice: {}", choice);
            } catch (NullPointerException e) {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        rentalRepository.findAll().forEach(rental -> {
            if (rental.getEndDate().isAfter(LocalDateTime.now())) vehicleRepository.save(rental.getVehicle());
            else if (rental.getVehicle() instanceof Rentable r) {
                try {
                    r.returnBack();
                    vehicleRepository.save(rental.getVehicle());
                } catch (VehicleBookingException e) {
                    logger.warn("Vehicle {} is not booked.", r.toString(), e);
                }
            }
        });

        List<Rentable> rentableVehicles = vehicleRepository.findAll().stream().filter(v -> v instanceof Rentable).map(Rentable.class::cast).toList();
        List<Person> peopleList = new ArrayList<>(customersRepository.findAll());
        peopleList.addAll(employeeRepository.findAll());

        try {
            switch(choice) {
                case ADD_CAR -> vehicleRepository.save(CarService.generateCar(sc));
                case ADD_SUV -> vehicleRepository.save(CarService.generateSUV(sc));
                case ADD_EMPLOYEE -> employeeRepository.save(EmployeeService.createEmployee(sc));
                case ADD_CUSTOMER -> customersRepository.save(CustomerService.generateCustomer(sc));
                case MAKE_RENTAL -> rentalRepository.save(RentalService.generateRental(sc, customersRepository.findAll(), rentableVehicles));
                case CREATE_BACKUP -> backupRepo.backup();
                case RESTORE_BACKUP -> backupRepo.restore();
                case PRINT_DATA -> DataService.printAllRepositories(customersRepository, employeeRepository, vehicleRepository, rentalRepository);
                case PRINT_RENTABLE_CARS -> CarService.printRentableCarsList(rentableVehicles);
                case PRINT_RENTALS -> RentalService.printAllRentals(rentalRepository.findAll());
                case SEARCH_RENTALS_BY_BRAND -> {
                    System.out.print("Enter car brand: ");
                    String brand = sc.nextLine();
                    List<Rental> rentalsByCarBrand = RentalService.findRentalsByCarBrand(brand, rentalRepository.findAll());
                    RentalService.printFoundRentals(rentalsByCarBrand);
                }
                case SEARCH_RENTALS_BY_USER -> {
                    Customer customer = CustomerService.selectCustomer(sc, customersRepository.findAll());
                    List<Rental> rentalsByUser = RentalService.findRentalsByUser(customer, rentalRepository.findAll());
                    RentalService.printFoundRentals(rentalsByUser);
                }
                case PRINT_OLDEST_PERSON -> {
                    Optional<? extends Person> oldestPerson = PersonService.findOldestPerson(peopleList);
                    if (oldestPerson.isEmpty()) {
                        System.out.println("No people found");
                        return;
                    }
                    System.out.println("Oldest person: " + oldestPerson.get());
                }
                case PRINT_YOUNGEST_PERSON -> {
                    Optional<? extends Person> youngestPerson = PersonService.findYoungestPerson(peopleList);
                    if (youngestPerson.isEmpty()) {
                        System.out.println("No people found");
                        return;
                    }
                    System.out.println("Youngest person: " + youngestPerson.get());
                }
                case SORT_RENTALS_BY_PRICE -> {
                    rentalRepository.saveAll(rentalRepository.findAll().stream().sorted().toList());
                    RentalService.printAllRentals(rentalRepository.findAll());
                }
                case SORT_RENTALS_BY_DATE -> {
                    rentalRepository.saveAll(rentalRepository.findAll().stream().sorted((r1, r2) -> r1.getEndDate().compareTo(r2.getEndDate())).toList());
                    RentalService.printAllRentals(rentalRepository.findAll());
                }
                case EXIT -> System.out.println("Goodbye!");
                default -> System.out.println("Not yet implemented");
            }
            AuditRepo.log(choice.getName(), employee);
        } catch (Exception e) {
            logger.error("Unexpected error during action execution", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
