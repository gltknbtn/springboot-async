package com.gltknbtn.repository;

import com.gltknbtn.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called personRepository
// CRUD refers Create, Read, Update, Delete

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByName(String name);
}