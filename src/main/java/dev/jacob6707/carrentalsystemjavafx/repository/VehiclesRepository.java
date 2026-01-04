package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;

import java.nio.file.Path;

/**
 * A singleton repository for managing {@link Vehicle} entities persisted in a JSON file.
 * This class extends {@code JsonRepository<Vehicle>} to provide functionality for
 * reading, writing, and maintaining a collection of rentals in a thread-safe manner.
 */
public class VehiclesRepository extends JsonRepository<Vehicle> {
    private static final VehiclesRepository INSTANCE = new VehiclesRepository();

    private VehiclesRepository() {
        super(Path.of("data/vehicles.json"), Vehicle[].class);
    }

    /**
     * Gets the singleton instance of the VehiclesRepository.
     * @return instance of VehiclesRepository
     */
    public static VehiclesRepository getInstance() {
        return INSTANCE;
    }
}
