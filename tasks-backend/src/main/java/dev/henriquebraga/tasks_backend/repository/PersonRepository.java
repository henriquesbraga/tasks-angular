package dev.henriquebraga.tasks_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.henriquebraga.tasks_backend.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Person findByName(String name);

}