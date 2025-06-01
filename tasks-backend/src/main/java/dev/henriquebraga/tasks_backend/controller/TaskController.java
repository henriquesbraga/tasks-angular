package dev.henriquebraga.tasks_backend.controller;

import dev.henriquebraga.tasks_backend.model.dto.TaskDTO;
import dev.henriquebraga.tasks_backend.model.dto.TaskFilter;
import dev.henriquebraga.tasks_backend.model.dto.TaskRegisterDTO;
import dev.henriquebraga.tasks_backend.model.dto.TaskUpdateDTO;
import dev.henriquebraga.tasks_backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  // GET /api/tasks
  @GetMapping
  public ResponseEntity<List<TaskDTO>> getAllTasks(
    TaskFilter filter
  ) {
    System.out.println(filter);
    return ResponseEntity.ok(taskService.findAll(filter));
  }

  // GET /api/tasks/{id}
  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.findById(id));
  }

  // POST /api/tasks
  @PostMapping
  public ResponseEntity<Object> createTask(@Valid @RequestBody TaskRegisterDTO dto) {
    taskService.create(dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Tarefa criada com sucesso!");
    return ResponseEntity.status(201).body(response);
  }

  // PUT /api/tasks/{id}
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO dto) {
    taskService.update(id, dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Tarefa atualizada com sucesso!");
    return ResponseEntity.status(200).body(response);
  }

  // DELETE /api/tasks/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
    taskService.delete(id);

    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Tarefa deletada com sucesso!");
    return ResponseEntity.status(200).body(response);
  }
}
