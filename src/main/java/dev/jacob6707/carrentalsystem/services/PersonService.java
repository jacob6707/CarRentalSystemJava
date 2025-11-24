package dev.jacob6707.carrentalsystem.services;

import dev.jacob6707.carrentalsystem.entities.Person;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PersonService {
    public static Optional<? extends Person> findYoungestPerson(List<? extends Person> people){
        return people.stream()
                .max(Comparator.comparing(Person::getDateOfBirth));
    }


    public static Optional<? extends Person> findOldestPerson(List<? extends Person> people){
        return people.stream()
                .min(Comparator.comparing(Person::getDateOfBirth));
    }
}
