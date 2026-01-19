package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.vehicle.VehicleDTO;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class DatabaseVehiclesRepository extends H2DBRepository<VehicleDTO> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseVehiclesRepository.class);

    public DatabaseVehiclesRepository() {
        super("vehicles", VehicleDTO.class, Path.of("src/main/resources/vehicles.sql"));
    }

    @Override
    public void save(VehicleDTO entity) {
        try {
            DatabaseUtils.runUpdate("INSERT INTO " + this.tableName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    entity.getId(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt(),
                    entity.getBrand(),
                    entity.getModel(),
                    entity.getLicensePlate(),
                    entity.getYear(),
                    entity.getMileage(),
                    entity.getDailyPrice(),
                    entity.getType());
        } catch (IOException e) {
            log.error("Failed to save customer to database", e);
        }
    }
}
