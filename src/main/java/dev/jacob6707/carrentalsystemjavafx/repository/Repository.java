package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A generic repository interface for managing entities. This interface defines
 * the standard operations that can be performed on a repository, including
 * saving, retrieving, and deleting entities.
 *
 * @param <T> the type of entities managed by this repository, which must
 *            extend {@link Entity}
 */
public interface Repository<T extends Entity> {
    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.
     * @param entity entity to save
     */
    void save(T entity);

    /**
     * Retrieves an entity by its ID.
     * @param id must not be {@literal null}.
     * @return the entity with the given ID or {@literal Optional#empty()} if none found
     */
    Optional<T> findById(UUID id);

    /**
     * Retrieves all entities managed by the repository.
     * @return all entities
     */
    List<T> findAll();

    /**
     * Deletes the given entity.
     * @param entity entity to delete
     */
    void delete(T entity);

    /**
     * Deletes an entity by its ID.
     * @param id must not be {@literal null}.
     */
    void deleteById(UUID id);
}
