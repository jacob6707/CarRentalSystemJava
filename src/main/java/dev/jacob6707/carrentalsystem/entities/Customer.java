package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Customer extends Person {

    private BigDecimal discountRate;

    @Override
    public String getRole() {
        return "Customer";
    }

    private Customer(CustomerBuilder builder) {
        super(builder.firstName, builder.lastName, builder.email, builder.phoneNumber, builder.idNumber, builder.location, builder.dateOfBirth);
        this.discountRate = builder.discountRate;
    }

    public static class CustomerBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String idNumber;
        private Location location;
        private LocalDate dateOfBirth;
        private BigDecimal discountRate;

        public CustomerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder idNumber(String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public CustomerBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public CustomerBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CustomerBuilder discountRate(BigDecimal discountRate) {
            this.discountRate = discountRate;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}
