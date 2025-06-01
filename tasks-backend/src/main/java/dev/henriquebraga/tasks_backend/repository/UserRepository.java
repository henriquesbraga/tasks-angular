package dev.henriquebraga.tasks_backend.repository;

import dev.henriquebraga.tasks_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuário por username (útil para autenticação futura)
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}