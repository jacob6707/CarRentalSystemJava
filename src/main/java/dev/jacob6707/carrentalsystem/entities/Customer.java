package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class that represents a customer.
 */
public class Customer extends Person {

    private BigDecimal discountRate;

    /**
     * Gets the role of the customer.
     * @return PersonRole.CUSTOMER
     * @see PersonRole
     */
    @Override
    public PersonRole getRole() {
        return PersonRole.CUSTOMER;
    }

    public Customer() {}

    private Customer(CustomerBuilder builder) {
        super(builder.id, builder.firstName, builder.lastName, builder.email, builder.phoneNumber, builder.idNumber, builder.location, builder.dateOfBirth);
        this.discountRate = builder.discountRate;
    }

    /**
     * Builder for the Customer class.
     */
    public static class CustomerBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String idNumber;
        private Location location;
        private LocalDate dateOfBirth;
        private BigDecimal discountRate = BigDecimal.ZERO;

        public CustomerBuilder id(Long id) {
            this.id = id;
            return this;
        }

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

        /**
         * Builds the Customer object.
         * @return The Customer object
         */
        public Customer build() {
            return new Customer(this);
        }
    }

    /**
     * Gets the discount rate of the customer.
     * @return The discount rate of the customer
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * Sets the discount rate of the customer.
     * @param discountRate The discount rate of the customer
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}
