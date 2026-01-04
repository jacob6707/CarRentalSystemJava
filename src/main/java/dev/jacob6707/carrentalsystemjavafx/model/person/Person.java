package dev.jacob6707.carrentalsystemjavafx.model.person;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.model.Location;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents an abstract person.
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

    /**
     * Gets the first name of the person.
     * @return Person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * @param firstName Person's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the person.
     * @return Person's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName Person's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the person.
     * @return Person's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the person.
     * @param email Person's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the person.
     * @return Person's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     * @param phoneNumber Person's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the location of the person.
     * @return Person's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the person.
     * @param location Person's location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the ID number of the person.
     * @return Person's ID number
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the ID number of the person.
     * @param idNumber Person's ID number
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Gets the date of birth of the person.
     * @return Person's date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the person.
     * @param dateOfBirth Person's date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns a string representation of the person.
     * @return FirstName LastName (email: email DOB: dateOfBirth)
     */
    @Override
    public String toString() {
        return getRole().getName() + " " + getFullName() + " (email: " + email + " DOB: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")";
    }

    /**
     * Gets the full name of the person.
     * @return FirstName LastName
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}