package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.trivedruna.application.command.create.answer.CreateAnswerRequest;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.answer.GetAllAnswerRequest;
import org.vedruna.trivedruna.application.query.getall.answerbyuser.GetAnswerByUserRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.AnswerController;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.SubmitAnswerRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.AnswerDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/answers")
public class AnswerControllerImpl implements AnswerController {

  private final Mediator mediator;
  private final InboundConverter inboundConverter;
  private final UserJpaRepository userJpaRepository;
  private final QuestionsJpaRepository questionsJpaRepository;

  /**
   * Inserta una respuesta del usuario autenticado y aplica la lógica de negocio asociada
   * (validación de opción + incremento de puntuaciones si es correcta).
   *
   * @param request datos de la respuesta (id de pregunta y opción elegida)
   * @param user usuario autenticado inyectado por Spring Security
   * @return respuesta almacenada con datos legibles (usuario y texto de la pregunta)
   */
  @Override
  public ResponseEntity<AnswerDTO> submitAnswer(
      SubmitAnswerRequestDTO request, @AuthenticationPrincipal UserDTO user) {
    log.info("Usuario {} responde a la pregunta {}", user.getUserId(), request.getQuestionId());
    var response =
        mediator.dispatch(
            new CreateAnswerRequest(
                request.getSelectedAnswer(), user.getUserId(), request.getQuestionId()));
    return ResponseEntity.ok(mapToAnswerDTO(response.getUserAnswerQuestionModel()));
  }

  /**
   * Recupera todas las respuestas paginadas y las devuelve enriquecidas con el nombre de usuario
   * y el enunciado de la pregunta.
   *
   * @param pageable parámetros de paginación
   * @return página de respuestas
   */
  @Override
  public ResponseEntity<Page<AnswerDTO>> getAllAnswers(Pageable pageable) {
    log.info("Listar respuestas paginadas");
    var response =
        mediator
            .dispatch(new GetAllAnswerRequest(pageable))
            .getUserAnswerQuestionModel()
            .map(this::mapToAnswerDTO);
    return ResponseEntity.ok(response);
  }

  /**
   * Recupera todas las respuestas de un usuario con paginaciÃ³n.
   *
   * @param userId identificador del usuario.
   * @param pageable parÃ¡metros de paginaciÃ³n.
   * @return pÃ¡gina de respuestas del usuario.
   */
  @Override
  public ResponseEntity<Page<AnswerDTO>> getAnswersByUser(
      @PathVariable Integer userId, Pageable pageable) {
    log.info("Listar respuestas paginadas del usuario {}", userId);
    var response =
        mediator
            .dispatch(new GetAnswerByUserRequest(userId, pageable))
            .getUserAnswerQuestionModel()
            .map(this::mapToAnswerDTO);
    return ResponseEntity.ok(response);
  }

  private AnswerDTO mapToAnswerDTO(org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel model) {
    AnswerDTO dto = new AnswerDTO();
    dto.setSelectedAnswer(model.getSelectedAnswer());
    dto.setCreateDate(model.getCreateDate());
    dto.setQuestionId(model.getQuestionsQuestionId());

    var question =
        questionsJpaRepository
            .findById(model.getQuestionsQuestionId())
            .orElse(null);

    dto.setIsCorrect(
        question != null
            && question.getCorrectAnswer() != null
            && question.getCorrectAnswer().equalsIgnoreCase(model.getSelectedAnswer()));

    dto.setUsername(
        userJpaRepository
            .getUserById(model.getUsersUserId())
            .map(org.vedruna.trivedruna.domain.model.UserModel::getUsername)
            .orElse("Usuario desconocido"));
    dto.setQuestionText(
        question != null
            ? question.getQuestionText()
            : "Pregunta no encontrada");
    return dto;
  }
}
