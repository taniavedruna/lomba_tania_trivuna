package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.SubmitAnswerRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.AnswerDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RequestMapping("/api/v1/answers")
@Tag(name = "Respuestas", description = "Endpoints para registrar y consultar respuestas")
public interface AnswerController {

  /**
   * Registra la respuesta de un usuario a una pregunta.
   *
   * @param request datos de la respuesta.
   * @param user usuario autenticado.
   * @return respuesta guardada.
   */
  @PostMapping
  @Operation(summary = "Responder pregunta", description = "Inserta una respuesta para una pregunta existente")
  ResponseEntity<AnswerDTO> submitAnswer(
      @Valid @RequestBody SubmitAnswerRequestDTO request, @AuthenticationPrincipal UserDTO user);

  /**
   * Devuelve todas las respuestas paginadas.
   *
   * @param pageable parámetros de paginación.
   * @return página con respuestas.
   */
  @GetMapping
  @Operation(summary = "Listar respuestas", description = "Devuelve todas las respuestas paginadas")
  ResponseEntity<Page<AnswerDTO>> getAllAnswers(Pageable pageable);

  /**
   * Devuelve las respuestas de un usuario concreto.
   *
   * @param userId identificador del usuario.
   * @param pageable parÃ¡metros de paginaciÃ³n.
   * @return pÃ¡gina con las respuestas del usuario.
   */
  @GetMapping("/user/{userId}")
  @Operation(summary = "Respuestas por usuario", description = "Devuelve las respuestas de un usuario")
  ResponseEntity<Page<AnswerDTO>> getAnswersByUser(@PathVariable Integer userId, Pageable pageable);
}
