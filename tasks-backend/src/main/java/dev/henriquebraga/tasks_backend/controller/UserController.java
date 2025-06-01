package dev.henriquebraga.tasks_backend.controller;

import dev.henriquebraga.tasks_backend.model.dto.UserDTO;
import dev.henriquebraga.tasks_backend.model.dto.UserRegisterDTO;
import dev.henriquebraga.tasks_backend.model.dto.UserUpdateDTO;
import dev.henriquebraga.tasks_backend.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // GET /api/users
  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  // GET /api/users/{id}
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  // POST /api/users
  @PostMapping
  public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegisterDTO dto) {

    userService.create(dto);
    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Usuário criado com sucesso!");

    return ResponseEntity.status(201).body(response);
  }

  // PUT /api/users/{id}
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {

    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Usuário atualizado com sucesso!");

    return ResponseEntity.status(200).body(response);
  }

  // DELETE /api/users/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    userService.delete(id);

    Map<String, String> response = new HashMap<String, String>();
    response.put("status", "Usuário deletado com sucesso!");

    return ResponseEntity.status(200).body(response);
  }
}