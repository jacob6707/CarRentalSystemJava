package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;

public class Employee extends Person {
    private BigDecimal salary;

    public Employee(String firstName, String lastName, String email, BigDecimal salary) {
        super(firstName, lastName, email);
        this.salary = salary;
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
