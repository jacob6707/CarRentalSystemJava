package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;

import java.nio.file.Path;

public class CustomersRepository extends JsonRepository<Customer> {
    private static final CustomersRepository INSTANCE = new CustomersRepository();

    private CustomersRepository() {
        super(Path.of("data/customers.json"), Customer[].class);
    }

    public static CustomersRepository getInstance() {
        return INSTANCE;
    }
}
