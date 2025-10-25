package dev.jacob6707.carrentalsystem.entities;

public interface Rentable {
    boolean isAvailable();
    void rent();
    void returnBack();
}
