package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;

import java.nio.file.Path;

/**
 * A singleton repository for managing {@link Rental} entities persisted in a JSON file.
 * This class extends {@code JsonRepository<Rental>} to provide functionality for
 * reading, writing, and maintaining a collection of rentals in a thread-safe manner.
 */
public class RentalsRepository extends JsonRepository<Rental> {
    private static final RentalsRepository INSTANCE = new RentalsRepository();

    private RentalsRepository() {
        super(Path.of("data/rentals.json"), Rental[].class);
    }

    /**
     * Gets the singleton instance of the RentalsRepository.
     * @return instance of RentalsRepository
     */
    public static RentalsRepository getInstance() {
        return INSTANCE;
    }
}
