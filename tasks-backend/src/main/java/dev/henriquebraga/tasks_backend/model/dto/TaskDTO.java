package dev.henriquebraga.tasks_backend.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.henriquebraga.tasks_backend.model.entity.Person;
import dev.henriquebraga.tasks_backend.model.entity.TaskPriority;
import dev.henriquebraga.tasks_backend.model.entity.TaskStatus;

public class TaskDTO {

  private Long id;
  private String title;
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate deadline;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

  //private Long personId;

  private PersonDTO personDto;


  private TaskPriority priority;
  private TaskStatus status;



  public TaskDTO() {
  }

  public TaskDTO(Long id, String title, String description, LocalDate deadline, PersonDTO personDto, LocalDate createdAt, TaskPriority priority, TaskStatus status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.personDto = personDto;
    this.createdAt = createdAt;
    this.priority = priority;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public PersonDTO getPersonDto() {
    return personDto;
  }

  public void setPersonDto(PersonDTO personDto) {
    this.personDto = personDto;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public void setPriority(TaskPriority priority) {
    this.priority = priority;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }
}
