package dev.henriquebraga.tasks_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.henriquebraga.tasks_backend.model.entity.Person;

public class UserDTO {

  private Long id;
  private String username;
  private String password; // Em produção, você pode omitir este campo da resposta.
  private Person person;

  public UserDTO() {
  }

  public UserDTO(Long id, String username, String password, Person person) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.person = person;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
