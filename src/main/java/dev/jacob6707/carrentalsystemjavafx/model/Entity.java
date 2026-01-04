package dev.jacob6707.carrentalsystemjavafx.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an entity to store in the database or file.
 */
public abstract class Entity {
    private UUID id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    protected Entity() {}

    protected Entity(UUID id) {
        this.id = id;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Gets the ID of the entity.
     * @return ID of the entity
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID of the entity.
     * @param id ID of the entity
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the time the entity was created at.
     * @return time the entity was created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the time the entity was created at.
     * @param createdAt time the entity was created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the time the entity was last updated at.
     * @return time the entity was last updated at
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the time the entity was last updated at.
     * @param updatedAt time the entity was last updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
