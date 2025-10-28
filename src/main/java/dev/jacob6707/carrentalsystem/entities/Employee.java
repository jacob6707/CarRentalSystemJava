package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee extends Person {
    private BigDecimal salary;

    private Employee(EmployeeBuilder builder) {
        super(builder.firstName, builder.lastName, builder.email, builder.phoneNumber, builder.idNumber, builder.location, builder.dateOfBirth);
        this.salary = builder.salary;
    }

    public static class EmployeeBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String idNumber;
        private Location location;
        private LocalDate dateOfBirth;
        private BigDecimal salary;

        public EmployeeBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public EmployeeBuilder idNumber(String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public EmployeeBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public EmployeeBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public EmployeeBuilder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    @Override
    public String getRole() {
        return "Employee";
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
