package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends Entity> {
    void save(T entity);
    Optional<T> findById(UUID id);
    List<T> findAll();
    void delete(T entity);
    void deleteById(UUID id);
}
