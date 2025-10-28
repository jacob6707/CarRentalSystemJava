package dev.jacob6707.carrentalsystem.entities;

public record Location(String address, String city, String state, String postalCode, String country) {
    public String getFullAddress() {
        return address + ", " + postalCode + " " + country + ", " + state + ", " + country;
    }
}
