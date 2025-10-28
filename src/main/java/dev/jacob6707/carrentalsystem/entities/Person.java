package dev.jacob6707.carrentalsystem.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String idNumber;
    protected Location location;
    protected LocalDate dateOfBirth;

    public Person(String firstName, String lastName, String email, String phoneNumber, String idNumber, Location location, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract String getRole();

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return getRole() + " " + firstName + " " + lastName + " (email: " + email + " DOB: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")";
    }
}