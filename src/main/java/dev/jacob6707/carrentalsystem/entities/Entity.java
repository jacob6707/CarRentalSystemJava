package dev.jacob6707.carrentalsystem.entities;

import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.io.Serializable;

/*@JsonbTypeInfo({
        @JsonbSubtype(alias = "Car", type=Car.class),
        @JsonbSubtype(alias = "SUV", type=SUV.class),
        @JsonbSubtype(alias = "Employee", type=Employee.class),
        @JsonbSubtype(alias = "Customer", type=Customer.class),
        @JsonbSubtype(alias = "Rental", type=Rental.class),
})*/
public abstract class Entity implements Serializable {
    private Long id;

    public Entity() {}

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
