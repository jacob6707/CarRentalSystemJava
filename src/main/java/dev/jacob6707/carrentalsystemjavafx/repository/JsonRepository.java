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

    protected JsonRepository(Path file, Type type) {
        this.file = file;
        this.type = type;
        this.lastUpdated = LocalDateTime.now();
        this.entities = loadEntities();
    }

    @Override
    public List<T> findAll() {
        if (LocalDateTime.now().minusMinutes(5).isAfter(lastUpdated)) {
            entities = loadEntities();
        }
        return entities;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return findAll().stream().filter(entity -> entity.getId().equals(id)).findFirst();
    }

    @Override
    public void save(T entity) {
        entities = findAll();
        entities.add(entity);
        lastUpdated = LocalDateTime.now();
        writeToFile();
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(UUID id) {
        entities = findAll();
        entities.removeIf(entity -> entity.getId().equals(id));
        lastUpdated = LocalDateTime.now();
        writeToFile();
    }

    private List<T> loadEntities() {
        if (!Files.exists(file)) {
            log.warn("File {} does not exist", file);
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(file);
            T[] objects = jsonb.fromJson(json, type);
            lastUpdated = LocalDateTime.now();
            return new ArrayList<>(List.of(objects));
        } catch (IOException e) {
            throw new JsonParseException("Failed to read from JSON file: " + file, e);
        }
    }

    private void writeToFile() {
        try {
            Files.writeString(file, jsonb.toJson(entities.toArray()));
        } catch (IOException e) {
            throw new JsonParseException("Failed to write to JSON file: " + file, e);
        }
    }
}
