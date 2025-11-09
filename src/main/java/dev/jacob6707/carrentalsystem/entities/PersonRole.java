package dev.jacob6707.carrentalsystem.entities;

/**
 * Enum that represents the roles of a person.
 */
public enum PersonRole {
    CUSTOMER(1, "Customer"),
    EMPLOYEE(2, "Employee");

    private final int id;
    private final String name;

    PersonRole(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
