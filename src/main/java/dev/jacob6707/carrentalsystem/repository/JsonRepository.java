package dev.jacob6707.carrentalsystem.repository;

import dev.jacob6707.carrentalsystem.entities.Entity;
import dev.jacob6707.carrentalsystem.exception.JsonParseException;
import dev.jacob6707.carrentalsystem.util.ParameterizedTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.JsonbException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonRepository<T extends Entity> implements Repository<T> {
    private final Path file;
    private final Class<T> type;
    private final Jsonb jsonb;

    public JsonRepository(Path file, Class<T> type) {
        this.file = file;
        this.type = type;
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true);
        this.jsonb = JsonbBuilder.create(config);
    }

    @Override
    public List<T> findAll() {
        if (!Files.exists(file)) return new ArrayList<>();
        try {
            String json = Files.readString(file);
            if (json.isBlank()) return new ArrayList<>();

            // Create proper parameterized type for List<T>
            Type listType = ParameterizedTypes.listOf(type);

            return jsonb.fromJson(json, listType);
        } catch (Exception e) {
            throw new JsonParseException("Could not parse JSON from: " + file.toAbsolutePath(), e);
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        return findAll().stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(T entity) {
        List<T> entities = findAll();
        if (entity.getId() == null) entity.setId((long) entities.size() + 1);
        entities.removeIf(e -> e.getId().equals(entity.getId()));
        entities.add(entity);
        saveAll(entities);
    }

    @Override
    public void saveAll(List<T> entities) {
        try {
            String json = jsonb.toJson(entities);
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new JsonParseException("Failed to write JSON to file: " + file.toAbsolutePath(), e);
        } catch (JsonbException e) {
            throw new JsonParseException("Failed to serialize JSON: " + file.toAbsolutePath(), e);
        };
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId().toString());
    }

    @Override
    public void deleteById(String id) {
        List<T> entities = findAll();
        entities.removeIf(e -> e.getId().toString().equals(id));
        saveAll(entities);
    }

    @Override
    public void clear() {
        try {
            Files.writeString(file, "[]");
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear file: " + file.toAbsolutePath(), e);
        }
    }
}
