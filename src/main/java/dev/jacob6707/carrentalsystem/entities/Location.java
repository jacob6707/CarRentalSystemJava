package dev.jacob6707.carrentalsystem.entities;

/**
 * Class that represents a location.
 * @param address Address line
 * @param city City
 * @param state State
 * @param postalCode Postal code
 * @param country Country
 */
public record Location(String address, String city, String state, String postalCode, String country) {
    /**
     * Returns the full address of the location.
     * @return The full address (address, postal code, city, state, country)
     */
    public String getFullAddress() {
        return address + ", " + postalCode + " " + city + ", " + state + ", " + country;
    }
}
