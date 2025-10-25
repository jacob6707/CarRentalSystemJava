package dev.jacob6707.carrentalsystem.entities;

public sealed interface Serviceable permits Car {
    void service();
}
