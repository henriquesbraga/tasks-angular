package dev.henriquebraga.tasks_backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

  @NotBlank(message = "Username é obrigatório")
  @Size(min = 6, max = 20, message = "Username deve ter entre 6 e 20 caracteres")
  String username,

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
  String password

){}
