package dev.henriquebraga.tasks_backend.configuration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
          "message", ex.getMessage()));
    }

    // 400 - validação de dto falhou
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();

      ex.getBindingResult().getFieldErrors().forEach(err -> {
        String field = err.getField();
        String message = err.getDefaultMessage();
        errors.put(field, message);
      });

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "message", "Erro de validação",
          "errors", errors));
    }

    // 400 - validação de parâmetros
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "message", "Parâmetros inválidos",
          "errors", ex.getMessage()));
    }






    // 500 - internal server
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
          "message", "Erro interno no servidor",
          "error", ex.getMessage()));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
      Throwable cause = ex.getCause();

      if (cause instanceof InvalidFormatException invalidFormat) {
        String targetType = invalidFormat.getTargetType().getSimpleName();

        return switch (targetType) {
          case "LocalDate" -> handleInvalidDateFormat(invalidFormat);
          case "TaskPriority" -> handleInvalidEnumFormat(invalidFormat);
          default -> handleGenericInvalidFormat(invalidFormat);
        };
      }

      return ResponseEntity.badRequest().body(Map.of(
              "message", "Erro ao processar a requisição",
              "error", ex.getMessage()
      ));
    }


    private ResponseEntity<Object> handleInvalidDateFormat(InvalidFormatException ex) {
      String field = extractFieldName(ex);
      String value = ex.getValue().toString();

      return ResponseEntity.badRequest().body(Map.of(
              "message", "Data inválida no campo '" + field + "': valor '" + value + "'. Use o formato yyyy-MM-dd"
      ));
    }

    private ResponseEntity<Object> handleInvalidEnumFormat(InvalidFormatException ex) {
      String field = extractFieldName(ex);
      String value = ex.getValue().toString();

      return ResponseEntity.badRequest().body(Map.of(
              "message", "Valor inválido para '" + field + "': '" + value
      ));
    }

    private ResponseEntity<Object> handleGenericInvalidFormat(InvalidFormatException ex) {
      String field = extractFieldName(ex);
      String value = ex.getValue().toString();

      return ResponseEntity.badRequest().body(Map.of(
              "message", "Valor inválido no campo '" + field + "': '" + value + "'"
      ));
    }

    private String extractFieldName(InvalidFormatException ex) {
      return ex.getPath().stream()
              .map(ref -> ref.getFieldName())
              .findFirst()
              .orElse("campo");
    }








}