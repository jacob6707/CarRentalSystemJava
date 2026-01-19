package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.rental.RentalDTO;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class DatabaseRentalsRepository extends H2DBRepository<RentalDTO> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseRentalsRepository.class);

    public DatabaseRentalsRepository() {
        super("rentals", RentalDTO.class, Path.of("src/main/resources/rentals.sql"));
    }

    @Override
    public void save(RentalDTO entity) {
        try {
            DatabaseUtils.runUpdate("INSERT INTO " + this.tableName + "(vehicle_id, customer_id, start_date, end_date) VALUES (?, ?, ?, ?)",
                    entity.getVehicleId(),
                    entity.getCustomerId(),
                    entity.getStartDate(),
                    entity.getEndDate());
        } catch (IOException e) {
            log.error("Failed to save customer to database", e);
        }
    }
}
