package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import java.util.List;
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
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.CreateQuestionRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.QuestionDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RequestMapping("/api/v1/questions")
@Tag(name = "Preguntas", description = "Gestión y consulta de preguntas")
public interface QuestionController {

  /**
   * Crea un lote de preguntas (solo administradores).
   *
   * @param preguntas lista de preguntas a crear.
   * @return preguntas creadas.
   */
  @PostMapping
  @Operation(summary = "Crear preguntas", description = "Crea un lote de preguntas (solo ADMIN)")
  ResponseEntity<List<QuestionDTO>> createQuestions(
      @Valid @RequestBody List<CreateQuestionRequestDTO> preguntas,
      @AuthenticationPrincipal UserDTO user);

  /**
   * Retorna una lista de preguntas con paginación.
   *
   * @param pageable objeto con parámetros de paginación.
   * @return página con las preguntas.
   */
  @GetMapping("/")
  @Operation(summary = "Listar preguntas", description = "Devuelve todas las preguntas paginadas")
  ResponseEntity<Page<QuestionDTO>> getAllQuestions(Pageable pageable);

  /**
   * Obtiene las preguntas de una categoría concreta.
   *
   * @param categoryId identificador de la categoría.
   * @param pageable paginación.
   */
  @GetMapping("/category/{categoryId}")
  @Operation(summary = "Preguntas por categoría", description = "Paginado por categoría")
  ResponseEntity<Page<QuestionDTO>> getQuestionsByCategory(
      @PathVariable Integer categoryId, Pageable pageable);

  /**
   * Obtiene las preguntas de una categoría que el usuario autenticado no ha respondido.
   *
   * @param categoryId identificador de la categoría.
   * @param user usuario autenticado.
   * @param pageable paginación.
   */
  @GetMapping("/category/{categoryId}/unanswered")
  @Operation(summary = "Pendientes por categoría", description = "Preguntas de la categoría no respondidas por el usuario")
  ResponseEntity<Page<QuestionDTO>> getUnansweredByCategory(
      @PathVariable Integer categoryId,
      @AuthenticationPrincipal UserDTO user,
      Pageable pageable);

  /**
   * Devuelve el detalle de una pregunta por su identificador.
   *
   * @param questionId id de la pregunta.
   */
  @GetMapping("/{questionId}")
  @Operation(summary = "Detalle de pregunta", description = "Obtiene una pregunta por su id")
  ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Integer questionId);
}
