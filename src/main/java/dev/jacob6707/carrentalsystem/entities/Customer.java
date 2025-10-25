package dev.jacob6707.carrentalsystem.entities;

public class Customer extends Person {
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;

    @Override
    public String getRole() {
        return "Customer";
    }

    private Customer(CustomerBuilder builder) {
        super(builder.firstName, builder.lastName, builder.email);
        this.address = builder.address;
        this.city = builder.city;
        this.state = builder.state;
        this.postalCode = builder.postalCode;
        this.phoneNumber = builder.phoneNumber;
    }

    public static class CustomerBuilder {
        private final String firstName;
        private final String lastName;
        private final String email;

        private String address;
        private String city;
        private String state;
        private String postalCode;
        private String phoneNumber;

        public CustomerBuilder(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public CustomerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerBuilder city(String city) {
            this.city = city;
            return this;
        }

        public CustomerBuilder state(String state) {
            this.state = state;
            return this;
        }

        public CustomerBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CustomerBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
