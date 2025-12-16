package dev.jacob6707.carrentalsystemjavafx.model.person;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.model.Location;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Abstract class that represents a person.
 */
@JsonbTypeInfo({
        @JsonbSubtype(alias = "Customer", type=Customer.class),
        @JsonbSubtype(alias = "Employee", type=Employee.class)
})
public abstract class Person extends Entity {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String idNumber;
    protected Location location;
    protected LocalDate dateOfBirth;

    protected Person() {}

    protected Person(String firstName, String lastName, String email, String phoneNumber, String idNumber, Location location, LocalDate dateOfBirth) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the role of the person.
     * @return the role of the person
     * @see PersonRole
     */
    public abstract PersonRole getRole();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return getRole().getName() + " " + getFullName() + " (email: " + email + " DOB: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")";
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}