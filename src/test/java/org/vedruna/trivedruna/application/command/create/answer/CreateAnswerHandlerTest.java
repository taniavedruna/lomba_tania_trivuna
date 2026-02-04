package org.vedruna.trivedruna.application.command.create.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.exceptions.InvalidAnswerOptionException;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserAnswerJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

class CreateAnswerHandlerTest {

  @Mock private UserAnswerJpaRepository userAnswerJpaRepository;
  @Mock private UserJpaRepository userJpaRepository;
  @Mock private CoursesJpaRepository coursesJpaRepository;
  @Mock private QuestionsJpaRepository questionsJpaRepository;

  @InjectMocks private CreateAnswerHandler handler;

  private QuestionModel question;
  private UserModel user;
  private UserAnswerQuestionModel saved;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    question = new QuestionModel();
    question.setQuestionId(1);
    question.setCorrectAnswer("Correcta");
    question.setIncorrectAnswer1("A");
    question.setIncorrectAnswer2("B");
    question.setIncorrectAnswer3("C");

    user = new UserModel();
    user.setUserId(10);
    user.setCoursesCourseId(20);

    saved = new UserAnswerQuestionModel();
    saved.setUsersUserId(user.getUserId());
    saved.setQuestionsQuestionId(question.getQuestionId());
    saved.setSelectedAnswer("Correcta");
  }

  @Test
  void cuandoRespuestaCorrecta_incrementaPuntos() {
    when(questionsJpaRepository.findById(1)).thenReturn(java.util.Optional.of(question));
    when(userJpaRepository.getUserById(10)).thenReturn(java.util.Optional.of(user));
    when(userAnswerJpaRepository.save("Correcta", 10, 1)).thenReturn(saved);

    var response = handler.handle(new CreateAnswerRequest("Correcta", 10, 1));

    assertThat(response.getUserAnswerQuestionModel().getSelectedAnswer()).isEqualTo("Correcta");
    verify(userJpaRepository).incrementScore(10, 1);
    verify(coursesJpaRepository).incrementScore(20, 1);
  }

  @Test
  void cuandoRespuestaIncorrecta_noIncrementaPuntos() {
    when(questionsJpaRepository.findById(1)).thenReturn(java.util.Optional.of(question));
    when(userJpaRepository.getUserById(10)).thenReturn(java.util.Optional.of(user));
    when(userAnswerJpaRepository.save("B", 10, 1)).thenReturn(saved);

    handler.handle(new CreateAnswerRequest("B", 10, 1));

    verify(userJpaRepository, never()).incrementScore(anyInt(), anyInt());
    verify(coursesJpaRepository, never()).incrementScore(anyInt(), anyInt());
  }

  @Test
  void cuandoOpcionNoExiste_lanzaInvalidAnswerOptionException() {
    when(questionsJpaRepository.findById(1)).thenReturn(java.util.Optional.of(question));

    assertThatThrownBy(() -> handler.handle(new CreateAnswerRequest("Desconocida", 10, 1)))
        .isInstanceOf(InvalidAnswerOptionException.class);

    verify(userAnswerJpaRepository, never()).save(eq("Desconocida"), anyInt(), anyInt());
  }
}
