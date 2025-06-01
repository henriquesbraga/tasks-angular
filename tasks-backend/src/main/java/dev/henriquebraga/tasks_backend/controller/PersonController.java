package dev.henriquebraga.tasks_backend.controller;

import dev.henriquebraga.tasks_backend.model.dto.PersonDTO;
import dev.henriquebraga.tasks_backend.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  // GET /api/persons
  @GetMapping
  public ResponseEntity<List<PersonDTO>> getAllPersons() {
    return ResponseEntity.ok(personService.findAll());
  }

  // GET /api/persons/{id}
  @GetMapping("/{id}")
  public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
    return ResponseEntity.ok(personService.findById(id));
  }

  // POST /api/persons
  @PostMapping
  public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO dto) {
    return ResponseEntity.ok(personService.create(dto));
  }

  // PUT /api/persons/{id}
  @PutMapping("/{id}")
  public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO dto) {
    return ResponseEntity.ok(personService.update(id, dto));
  }

  // DELETE /api/persons/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
