package dev.henriquebraga.tasks_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.henriquebraga.tasks_backend.model.entity.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRegisterDTO (

        @NotNull(message = "Informe a quem pertence a task")
        Long personId,

        @NotBlank(message = "Titulo é obrigatório")
        @Size(min = 6, max = 20, message = "Username deve ter entre 6 e 20 caracteres")
        String title,


        @NotBlank(message = "Descrição é obrigatória")
        @Size(min = 10, max = 254, message = "Descrição deve ter entre 6 e 254 caracteres")
        String description,

        @NotNull(message = "A prioridade da task é obrigatória")
        TaskPriority priority,


        @NotNull(message = "O prazo é obrigatório")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline


){}
