package dev.henriquebraga.tasks_backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO (

  @NotBlank(message = "Username é obrigatório")
  @Size(min = 4, max = 20, message = "Username deve ter entre 4 e 20 caracteres")
  String username,

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
  String password,

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 4, max = 20, message = "Nome deve ter entre 6 e 20 caracteres")
  String name

){}
