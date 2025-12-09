package dev.jacob6707.carrentalsystem.repository;

import dev.jacob6707.carrentalsystem.entities.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {
    List<T> findAll();
    Optional<T> findById(Long id);
    void save(T entity);
    void saveAll(List<T> entities);
    void delete(T entity);
    void deleteById(String id);
    void clear();
}