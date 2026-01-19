package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.person.Customer;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class DatabaseCustomersRepository extends H2DBRepository<Customer> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseCustomersRepository.class);

    public DatabaseCustomersRepository() {
        super("customers", Customer.class, Path.of("src/main/resources/customers.sql"));
    }

    @Override
    public void save(Customer entity) {
        try {
            DatabaseUtils.runUpdate("INSERT INTO " + this.tableName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    entity.getId(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPhoneNumber(),
                    entity.getIdNumber(),
                    entity.getLocation().address(),
                    entity.getLocation().city(),
                    entity.getLocation().state(),
                    entity.getLocation().postalCode(),
                    entity.getLocation().country(),
                    entity.getDateOfBirth(),
                    entity.getDiscountRate());
        } catch (IOException e) {
            log.error("Failed to save customer to database", e);
        }
    }

}
