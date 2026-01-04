package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

/**
 * Represents a vehicle that can be serviced.
 * @see Vehicle
 */
public sealed interface Serviceable permits Vehicle {
    /**
     * Services the vehicle.
     */
    void service();
}
