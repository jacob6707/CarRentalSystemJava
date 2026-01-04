package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;

import java.nio.file.Path;

/**
 * A singleton repository for managing {@link Customer} entities persisted in a JSON file.
 * This class extends {@code JsonRepository<Customer>} to provide functionality for
 * reading, writing, and maintaining a collection of customers in a thread-safe manner.
 */
public class CustomersRepository extends JsonRepository<Customer> {
    private static final CustomersRepository INSTANCE = new CustomersRepository();

    private CustomersRepository() {
        super(Path.of("data/customers.json"), Customer[].class);
    }

    /**
     * Gets the singleton instance of the CustomersRepository.
     * @return instance of CustomersRepository
     */
    public static CustomersRepository getInstance() {
        return INSTANCE;
    }
}
