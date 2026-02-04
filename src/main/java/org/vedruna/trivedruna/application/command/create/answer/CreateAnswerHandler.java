package org.vedruna.trivedruna.application.command.create.answer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.QuestionNotFoundException;
import org.vedruna.trivedruna.domain.exceptions.DuplicateAnswerException;
import org.vedruna.trivedruna.domain.exceptions.InvalidAnswerOptionException;
import org.vedruna.trivedruna.domain.exceptions.UserNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserAnswerJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class CreateAnswerHandler implements RequestHandler<CreateAnswerRequest, CreateAnswerResponse> {

  private final UserAnswerJpaRepository userAnswerJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final CoursesJpaRepository coursesJpaRepository;
  private final QuestionsJpaRepository questionsJpaRepository;

  /**
   * Inserta la respuesta y, de forma transaccional, suma un punto al usuario y a su curso.
   * Además, valida que la opción elegida exista en la pregunta y solo suma si es correcta.
   */
  @Override
  @Transactional
  public CreateAnswerResponse handle(CreateAnswerRequest request) {
    log.info("Crear respuesta para la pregunta {}", request.getQuestionsId());

    // No permitir responder la misma pregunta más de una vez
    if (userAnswerJpaRepository.existsByUserAndQuestion(request.getUserId(), request.getQuestionsId())) {
      throw new DuplicateAnswerException();
    }

    var question =
        questionsJpaRepository
            .findById(request.getQuestionsId())
            .orElseThrow(QuestionNotFoundException::new);

    // Validar que la respuesta elegida es una de las opciones disponibles
    var selected = request.getSelectedAnswer();
    boolean esOpcionValida =
        matches(question.getCorrectAnswer(), selected)
            || matches(question.getIncorrectAnswer1(), selected)
            || matches(question.getIncorrectAnswer2(), selected)
            || matches(question.getIncorrectAnswer3(), selected);

    if (!esOpcionValida) {
      throw new InvalidAnswerOptionException();
    }

    var saved =
        userAnswerJpaRepository.save(
            request.getSelectedAnswer(), request.getUserId(), request.getQuestionsId());

    var user =
        userJpaRepository
            .getUserById(request.getUserId())
            .orElseThrow(UserNotFoundException::new);

    boolean correcta =
        question.getCorrectAnswer() != null
            && question.getCorrectAnswer().equalsIgnoreCase(request.getSelectedAnswer());

    if (correcta) {
      userJpaRepository.incrementScore(user.getUserId(), 1);
      coursesJpaRepository.incrementScore(user.getCoursesCourseId(), 1);
    }

    return new CreateAnswerResponse(saved);
  }

  private boolean matches(String option, String selected) {
    return option != null && selected != null && option.equalsIgnoreCase(selected);
  }

  @Override
  public Class<CreateAnswerRequest> getRequestType() {
    return CreateAnswerRequest.class;
  }
}

