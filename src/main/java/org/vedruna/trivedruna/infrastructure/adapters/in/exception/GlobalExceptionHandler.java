package org.vedruna.trivedruna.infrastructure.adapters.in.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vedruna.trivedruna.domain.exceptions.InvalidAnswerOptionException;
import org.vedruna.trivedruna.domain.exceptions.QuestionNotFoundException;

/**
 * Manejador global de excepciones para devolver respuestas limpias y sin trazas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidAnswerOptionException.class)
  public ResponseEntity<ApiError> handleInvalidAnswer(
      InvalidAnswerOptionException ex, HttpServletRequest request) {
    return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(QuestionNotFoundException.class)
  public ResponseEntity<ApiError> handleQuestionNotFound(
      QuestionNotFoundException ex, HttpServletRequest request) {
    return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .findFirst()
            .orElse("Parámetros inválidos");
    return buildError(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", request.getRequestURI());
  }

  private ResponseEntity<ApiError> buildError(HttpStatus status, String message, String path) {
    ApiError body =
        new ApiError(Instant.now().toString(), status.value(), status.getReasonPhrase(), message, path);
    return ResponseEntity.status(status).body(body);
  }

  /**
   * Estructura de error expuesta en las respuestas de la API.
   */
  public static record ApiError(
      String timestamp, int status, String error, String message, String path) {}
}
