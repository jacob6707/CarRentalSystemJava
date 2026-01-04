package dev.jacob6707.carrentalsystemjavafx.model.person;

/**
 * Represents the roles of a person.
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

    /**
     * Gets the ID of the role.
     * @return ID representing the role
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the role.
     * @return Role name
     */
    public String getName() {
        return name;
    }
}
