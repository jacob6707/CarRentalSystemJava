package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.exception.JsonParseException;
import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * An abstract implementation of the {@code Repository} interface that persists entities
 * to a JSON file. This class provides basic CRUD operations and automatically handles
 * serialization and deserialization of entities.
 *
 * @param <T> the type of entities managed by this repository, which must extend {@link Entity}
 */
public abstract class JsonRepository<T extends Entity> implements Repository<T> {
    private static final Logger log = LoggerFactory.getLogger(JsonRepository.class);
    private final Path file;
    private final Type type;
    private final Jsonb jsonb = JsonbBuilder.create(
            new JsonbConfig().withFormatting(true).withNullValues(true)
    );

    private LocalDateTime lastUpdated;
    private List<T> entities;

    protected JsonRepository() {
        file = null;
        type = null;
    }

    /**
     * Constructor for JsonRepository.
     * @param file The path to the JSON file.
     * @param type The type of entities managed by this repository.
     */
    protected JsonRepository(Path file, Type type) {
        this.file = file;
        this.type = type;
        this.lastUpdated = LocalDateTime.now();
        this.entities = loadEntities();
    }

    /**
     * Finds all cached entities.
     * If the list wasn't updated in the last 5 minutes, it will be reloaded from the JSON file.
     *
     * @return A list of entities.
     */
    @Override
    public List<T> findAll() {
        if (LocalDateTime.now().minusMinutes(5).isAfter(lastUpdated)) {
            entities = loadEntities();
            lastUpdated = LocalDateTime.now();
        }
        return entities;
    }

    /**
     * Finds an entity by ID.
     * @param id Entity UUID
     * @return An optional containing the entity if found, or an empty optional otherwise.
     */
    @Override
    public Optional<T> findById(UUID id) {
        return findAll().stream().filter(entity -> entity.getId().equals(id)).findFirst();
    }

    /**
     * Saves an entity to the JSON file.
     * @param entity The entity to save.
     */
    @Override
    public void save(T entity) {
        entities = findAll();
        entities.add(entity);
        lastUpdated = LocalDateTime.now();
        writeToFile();
    }

    /**
     * Deletes an entity from the JSON file.
     * @param entity The entity to delete.
     */
    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    /**
     * Deletes an entity from the JSON file by ID.
     * @param id Entity UUID
     */
    @Override
    public void deleteById(UUID id) {
        entities = findAll();
        entities.removeIf(entity -> entity.getId().equals(id));
        lastUpdated = LocalDateTime.now();
        writeToFile();
    }

    /**
     * Loads entities from the JSON file.
     * @return A list of entities.
     */
    private List<T> loadEntities() {
        if (!Files.exists(file)) {
            log.warn("File {} does not exist", file);
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(file);
            T[] objects = jsonb.fromJson(json, type);
            lastUpdated = LocalDateTime.now();
            log.debug("Loaded {} entities from {}", objects.length, file);
            return new ArrayList<>(List.of(objects));
        } catch (IOException e) {
            throw new JsonParseException("Failed to read from JSON file: " + file, e);
        }
    }

    /**
     * Writes entities to the JSON file.
     */
    private void writeToFile() {
        try {
            log.debug("Writing {} entities to {}", entities.size(), file);
            Files.writeString(file, jsonb.toJson(entities.toArray()));
        } catch (IOException e) {
            throw new JsonParseException("Failed to write to JSON file: " + file, e);
        }
    }
}
