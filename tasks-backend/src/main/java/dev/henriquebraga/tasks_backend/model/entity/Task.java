package dev.henriquebraga.tasks_backend.model.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_task_tsk")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_task_tsk")
  private Long id;

  @Column(name = "title_task_tsk")
  private String title;

  @Column(name = "desc_task_tsk")
  private String description;

  
  @Column(name = "dta_deadline_task_tsk")
  private LocalDate deadline;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_person_tsk", nullable = true)
  private Person person;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority_task_tsk", nullable = false)
  private TaskPriority priority;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;


  @Enumerated(EnumType.STRING)
  @Column(name = "status_task_tsk", nullable = false)
  private TaskStatus status;

  public Task() {
  }

  public Task(Long id, String title, String description, LocalDate deadline, TaskPriority priority, Person person, LocalDate createdAt, TaskStatus status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.priority = priority;
    this.person = person;
    this.createdAt = createdAt;
    this.status = status;

  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDeadline() {
    return this.deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public Person getPerson() {
    return this.person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public void setPriority(TaskPriority priority) {
    this.priority = priority;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Task)) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(id, task.id) && Objects.equals(title, task.title)
        && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, deadline);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", title='" + getTitle() + "'" +
        ", description='" + getDescription() + "'" +
        ", deadline='" + getDeadline() + "'" +
        ", person='" + getPerson() + "'" +
        "}";
  }

}
