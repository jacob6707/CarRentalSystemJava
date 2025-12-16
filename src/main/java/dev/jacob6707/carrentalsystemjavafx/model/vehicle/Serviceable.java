package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

public sealed interface Serviceable permits Vehicle {
    void service();
}
