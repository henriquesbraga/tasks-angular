package dev.henriquebraga.tasks_backend.service;

import dev.henriquebraga.tasks_backend.model.dto.PersonDTO;
import dev.henriquebraga.tasks_backend.model.entity.Person;
import dev.henriquebraga.tasks_backend.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

  private final PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public List<PersonDTO> findAll() {
    return personRepository.findAll()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public PersonDTO findById(Long id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Person not found with id " + id));
    return toDTO(person);
  }

  @Transactional
  public PersonDTO create(PersonDTO dto) {
    Person person = new Person();
    updateEntityFromDTO(person, dto);
    return toDTO(personRepository.save(person));
  }

  @Transactional
  public PersonDTO update(Long id, PersonDTO dto) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Person not found with id " + id));
    updateEntityFromDTO(person, dto);
    return toDTO(personRepository.save(person));
  }

  @Transactional
  public void delete(Long id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Person not found with id " + id));
    personRepository.delete(person);
  }

  private void updateEntityFromDTO(Person person, PersonDTO dto) {
    person.setName(dto.getName());
  }

  private PersonDTO toDTO(Person person) {
    return new PersonDTO(
        person.getId(),
        person.getName()
    );
  }
}
