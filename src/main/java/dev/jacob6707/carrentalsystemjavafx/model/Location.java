package dev.jacob6707.carrentalsystemjavafx.model;

import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseColumn;
import jakarta.json.bind.annotation.JsonbTransient;

/**
 * Represents a location.
 * @param address Address line
 * @param city City
 * @param state State
 * @param postalCode Postal code
 * @param country Country
 */
public record Location(String address, String city, String state, @DatabaseColumn("postal_code") String postalCode, String country) {
    /**
     * Gets the full address of the location.
     * @return The full address (address, postal code, city, state, country)
     */
    @JsonbTransient
    public String getFullAddress() {
        return address + ", " + postalCode + " " + city + ", " + state + ", " + country;
    }

    /**
     * Returns the full address of the location.
     * @return The full address (address, postal code, city, state, country)
     */
    @Override
    public String toString() {
        return getFullAddress();
    }
}
