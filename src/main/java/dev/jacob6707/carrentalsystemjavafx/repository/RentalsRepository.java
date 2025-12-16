package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.rental.Rental;

import java.nio.file.Path;

public class RentalsRepository extends JsonRepository<Rental> {
    private static final RentalsRepository INSTANCE = new RentalsRepository();

    private RentalsRepository() {
        super(Path.of("data/rentals.json"), Rental[].class);
    }

    public static RentalsRepository getInstance() {
        return INSTANCE;
    }
}
