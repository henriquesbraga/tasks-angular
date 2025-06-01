package dev.henriquebraga.tasks_backend.service;

import dev.henriquebraga.tasks_backend.model.dto.UserDTO;
import dev.henriquebraga.tasks_backend.model.dto.UserRegisterDTO;
import dev.henriquebraga.tasks_backend.model.dto.UserUpdateDTO;
import dev.henriquebraga.tasks_backend.model.entity.Person;
import dev.henriquebraga.tasks_backend.model.entity.User;
import dev.henriquebraga.tasks_backend.repository.PersonRepository;
import dev.henriquebraga.tasks_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PersonRepository personRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.personRepository = personRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<UserDTO> findAll() {
    return userRepository.findAll()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }





  public UserDTO findByUsername(String username) {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Usuario nao encontrado para o username:  " + username));
    return toDTO(user);
  }

  public UserDTO findById(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    return toDTO(user);
  }



  @Transactional
  public UserDTO create(UserRegisterDTO dto) {

    // Registar o person primeiro
    Person p = personRepository.save(new Person(null, dto.name()));
    if (userRepository.existsByUsername(dto.username())) {
      throw new RuntimeException("Username já existe");
    }
    // criptografar a senha
    String encodedPassword = passwordEncoder.encode(dto.password());

    User user = userRepository.save(new User(null, dto.username(), encodedPassword, p));
    return toDTO(user);
  }

  @Transactional
  public UserDTO update(Long id, UserUpdateDTO dto) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário " + id + " não encontrado!"));


    // username immutable
    if (!user.getUsername().equals(dto.username()) &&
        userRepository.existsByUsername(dto.username())) {
      throw new RuntimeException("Username already exists");
    }

    updateEntityFromDTO(user, dto);

    return toDTO(userRepository.save(user));
  }

  @Transactional
  public void delete(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User não encontrado: " + id));
    userRepository.delete(user);
  }

  private void updateEntityFromDTO(User user, UserUpdateDTO dto) {
    user.setUsername(dto.username());

    String encodedPassord = passwordEncoder.encode(dto.password());

    user.setPassword(encodedPassord);

  }

  private UserDTO toDTO(User user) {
    
    return new UserDTO(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getPerson()
    );
  }
}
