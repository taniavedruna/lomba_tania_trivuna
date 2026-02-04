package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.vedruna.trivedruna.application.command.create.question.CreateQuestionRequest;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.question.GetAllQuestionRequest;
import org.vedruna.trivedruna.application.query.getall.questionbycategory.GetQuestionByCategoryRequest;
import org.vedruna.trivedruna.application.query.getall.questionunanswered.GetUnansweredQuestionRequest;
import org.vedruna.trivedruna.application.query.getbyid.question.GetQuestionByIdRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.QuestionController;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.CreateQuestionRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.QuestionDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionControllerImpl implements QuestionController {

  private final Mediator mediator;
  private final InboundConverter inboundConverter;

  /**
   * Crea un lote de preguntas. Solo accesible para administradores.
   *
   * @param preguntas lista de preguntas a crear.
   * @param user      usuario administrador autenticado.
   * @return preguntas creadas con sus identificadores.
   */
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<List<QuestionDTO>> createQuestions(
      @Valid @RequestBody List<CreateQuestionRequestDTO> preguntas,
      @AuthenticationPrincipal UserDTO user) {
    log.info("Crear preguntas (lote: {}) por usuario {}", preguntas != null ? preguntas.size() : 0, user.getUserId());
    if (preguntas == null || preguntas.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var questionModels = preguntas.stream()
        .map(inboundConverter::toQuestionModel)
        .peek(q -> {
          q.setUserId(user.getUserId());
          // Marcamos aprobada por defecto al ser creada por admin para evitar null en DB.
          if (q.getIsApproved() == null) {
            q.setIsApproved(Boolean.TRUE);
          }
        })
        .toList();
    var created = mediator
        .dispatch(new CreateQuestionRequest(questionModels))
        .getQuestionModel()
        .stream()
        .map(inboundConverter::toQuestionDTO)
        .toList();
    return ResponseEntity.ok(created);
  }

  /**
   * Retorna una lista de preguntas con paginación.
   *
   * @param pageable parámetros de paginación.
   * @return página de preguntas.
   */
  @Override
  public ResponseEntity<Page<QuestionDTO>> getAllQuestions(Pageable pageable) {
    log.info("Obtener todas las preguntas paginadas");
    Page<QuestionDTO> response = mediator
        .dispatch(new GetAllQuestionRequest(pageable))
        .getQuestionModel()
        .map(inboundConverter::toQuestionDTO);
    return ResponseEntity.ok(response);
  }

  /**
   * Devuelve las preguntas de una categoría concreta.
   *
   * @param categoryId identificador de la categoría.
   * @param pageable   parámetros de paginación.
   * @return página de preguntas de la categoría.
   */
  @Override
  public ResponseEntity<Page<QuestionDTO>> getQuestionsByCategory(
      Integer categoryId, Pageable pageable) {
    log.info("Obtener preguntas por categoría {}", categoryId);
    var response = mediator
        .dispatch(new GetQuestionByCategoryRequest(categoryId, pageable))
        .getQuestionModel()
        .map(inboundConverter::toQuestionDTO);
    return ResponseEntity.ok(response);
  }

  /**
   * Devuelve preguntas no respondidas por el usuario autenticado.
   *
   * @param categoryId identificador de la categoría.
   * @param user       usuario autenticado.
   * @param pageable   parámetros de paginación.
   * @return página de preguntas pendientes.
   */
  @Override
  public ResponseEntity<Page<QuestionDTO>> getUnansweredByCategory(
      Integer categoryId, UserDTO user, Pageable pageable) {
    log.info(
        "Obtener preguntas sin responder por categoría {} para usuario {}",
        categoryId,
        user.getUserId());
    var response = mediator
        .dispatch(new GetUnansweredQuestionRequest(user.getUserId(), categoryId, pageable))
        .getQuestionModel()
        .map(inboundConverter::toQuestionDTO);
    return ResponseEntity.ok(response);
  }

  /**
   * Detalle de pregunta por id.
   *
   * @param questionId identificador de la pregunta.
   * @return pregunta encontrada.
   */
  @Override
  public ResponseEntity<QuestionDTO> getQuestionById(Integer questionId) {
    log.info("Buscar pregunta {}", questionId);
    var question = mediator.dispatch(new GetQuestionByIdRequest(questionId)).getQuestionModel();
    return ResponseEntity.ok(inboundConverter.toQuestionDTO(question));
  }
}
