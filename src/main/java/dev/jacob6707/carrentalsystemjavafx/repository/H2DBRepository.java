package dev.jacob6707.carrentalsystemjavafx.repository;

import dev.jacob6707.carrentalsystemjavafx.exception.DatabaseException;
import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseUtils;
import dev.jacob6707.carrentalsystemjavafx.util.database.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

public abstract class H2DBRepository<T extends Entity> implements Repository<T> {
    private static final Logger log = LoggerFactory.getLogger(H2DBRepository.class);
    protected final String tableName;
    private final Class<T> clazz;

    /**
     * Constructs an instance of {@link H2DBRepository}.
     *
     * @param tableName the name of the table in the database that this repository manages
     */
    protected H2DBRepository(String tableName, Class<T> clazz, Path schemaFile) {
        this.tableName = tableName;
        this.clazz = clazz;
        if (Files.exists(schemaFile) && !DatabaseUtils.tableExists(tableName)) {
            DatabaseUtils.createTableFromSql(schemaFile);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            List<Map<String, Object>> result = DatabaseUtils.runQuery("SELECT * FROM " + tableName);
            return new ObjectMapper<>(clazz).map(result);
        } catch (Exception e) {
            log.error("Failed to query database", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<T> findById(UUID id) {
        try {
            List<Map<String, Object>> result = DatabaseUtils.runQuery("SELECT * FROM " + tableName + " WHERE id=?", id);
            if (result.isEmpty()) return Optional.empty();
            return Optional.of(new ObjectMapper<>(clazz).map(result.getFirst()));
        } catch (DatabaseException | IOException | SQLException e) {
            log.error("Failed to query database", e);
        }
        return Optional.empty();
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(UUID id) {
        try {
            DatabaseUtils.runUpdate("DELETE FROM " + tableName + " WHERE id=?", id);
        } catch (DatabaseException | IOException e) {
            log.error("Failed to delete entity from database", e);
        }
    }
}
