package dev.henriquebraga.tasks_backend.model.dto;

public record TaskFilter (
    Long numero,
    String titulo,
    Long responsavel,
    String situacao
)
{}
