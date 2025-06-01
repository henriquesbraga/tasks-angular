package dev.henriquebraga.tasks_backend.service;

import dev.henriquebraga.tasks_backend.model.dto.*;
import dev.henriquebraga.tasks_backend.model.entity.Person;
import dev.henriquebraga.tasks_backend.model.entity.Task;
import dev.henriquebraga.tasks_backend.model.entity.TaskStatus;
import dev.henriquebraga.tasks_backend.model.entity.User;
import dev.henriquebraga.tasks_backend.repository.PersonRepository;
import dev.henriquebraga.tasks_backend.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final PersonRepository personRepository;

  public TaskService(TaskRepository taskRepository, PersonRepository personRepository) {
    this.taskRepository = taskRepository;
    this.personRepository = personRepository;
  }

  public List<TaskDTO> findAll(TaskFilter filter) {


    // Recupera o usuário autenticado
    //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //    User authenticatedUser = (User) authentication.getPrincipal();


    return taskRepository.findByFilters(filter.numero(), filter.titulo(), filter.responsavel(), filter.situacao())
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());



//    return taskRepository.findAll()
//        .stream()
//        .map(this::toDTO)
//        .collect(Collectors.toList());
  }

  public TaskDTO findById(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    return toDTO(task);
  }

  @Transactional
  public void create(TaskRegisterDTO dto) {

    Person person = new Person();
    person.setId(dto.personId());

    Task task = new Task(
            null,
            dto.title(),
            dto.description(),
            dto.deadline(), //deadline
            dto.priority(),
            person,
            LocalDate.now(),
            TaskStatus.EM_ANDAMENTO
    );

    toDTO(taskRepository.save(task));
  }

  @Transactional
  public void update(Long id, TaskUpdateDTO dto) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

    updateEntityFromDTO(task, dto);

    toDTO(taskRepository.save(task));
  }

  @Transactional
  public void delete(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    taskRepository.delete(task);
  }

  private void updateEntityFromDTO(Task task, TaskUpdateDTO dto) {
    task.setTitle(dto.title());
    task.setDescription(dto.description());
    task.setDeadline(dto.deadline());
    task.setPriority(dto.priority());
    task.setStatus(dto.status());

    Person p = new Person();
    p.setId(dto.personId());

    task.setPerson(p);
  }

  private PersonDTO personToDTO(Person person) {
    return new PersonDTO(
            person.getId(),
            person.getName()
    );
  }


  public TaskDTO toDTO(Task task) {
    return new TaskDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getDeadline(),
        personToDTO(task.getPerson()),
        task.getCreatedAt(),
        task.getPriority(),
        task.getStatus()
    );
  }
}
