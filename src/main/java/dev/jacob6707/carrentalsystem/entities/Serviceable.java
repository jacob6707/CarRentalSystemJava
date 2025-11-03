package dev.jacob6707.carrentalsystem.entities;

/**
 * Interface that represents a serviceable vehicle.
 * Serviceable vehicles can be serviced.
 */
public sealed interface Serviceable permits Car, SUV {
    void service();
}
