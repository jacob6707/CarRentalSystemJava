package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.vehicle.Vehicle;

import java.nio.file.Path;

public class VehiclesRepository extends JsonRepository<Vehicle> {
    private static final VehiclesRepository INSTANCE = new VehiclesRepository();

    private VehiclesRepository() {
        super(Path.of("data/vehicles.json"), Vehicle[].class);
    }

    public static VehiclesRepository getInstance() {
        return INSTANCE;
    }
}
