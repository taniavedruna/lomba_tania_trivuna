package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.vedruna.trivedruna.application.command.create.answer.CreateAnswerRequest;
import org.vedruna.trivedruna.application.command.create.answer.CreateAnswerResponse;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.answer.GetAllAnswerRequest;
import org.vedruna.trivedruna.application.query.getall.answer.GetAllAnswerResponse;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl.AnswerControllerImpl;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.SubmitAnswerRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.AnswerDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;

class AnswerControllerImplTest {

  @Mock private Mediator mediator;
  @Mock private InboundConverter inboundConverter;
  @Mock private UserJpaRepository userJpaRepository;
  @Mock private QuestionsJpaRepository questionsJpaRepository;
  @InjectMocks private AnswerControllerImpl controller;

  private UserDTO user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new UserDTO();
    user.setUserId(1);
  }

  @Test
  void submitAnswerDevuelveDto() {
    SubmitAnswerRequestDTO req = new SubmitAnswerRequestDTO();
    req.setQuestionId(2);
    req.setSelectedAnswer("a");

    UserAnswerQuestionModel model = new UserAnswerQuestionModel();
    model.setUsersUserId(1);
    model.setQuestionsQuestionId(2);
    var userModel = new org.vedruna.trivedruna.domain.model.UserModel();
    userModel.setUsername("tester");
    var questionModel = new org.vedruna.trivedruna.domain.model.QuestionModel();
    questionModel.setQuestionText("texto");
    when(mediator.dispatch(any(CreateAnswerRequest.class)))
        .thenReturn(new CreateAnswerResponse(model));
    when(userJpaRepository.getUserById(1)).thenReturn(java.util.Optional.of(userModel));
    when(questionsJpaRepository.findById(2)).thenReturn(java.util.Optional.of(questionModel));
    // mapToAnswerDTO usa repos, no converter, así que seteamos valores en test mediante respuesta del controller

    var response = controller.submitAnswer(req, user);
    assertThat(response.getBody().getQuestionId()).isEqualTo(2);
    assertThat(response.getBody().getQuestionText()).isEqualTo("texto");
    assertThat(response.getBody().getUsername()).isEqualTo("tester");
  }

  @Test
  void getAllAnswersDevuelvePagina() {
    Page<UserAnswerQuestionModel> page = new PageImpl<>(java.util.List.of(new UserAnswerQuestionModel()));
    when(mediator.dispatch(any(GetAllAnswerRequest.class)))
        .thenReturn(new GetAllAnswerResponse(page));
    var userModel = new org.vedruna.trivedruna.domain.model.UserModel();
    userModel.setUsername("u");
    var questionModel = new org.vedruna.trivedruna.domain.model.QuestionModel();
    questionModel.setQuestionText("q");
    when(userJpaRepository.getUserById(any())).thenReturn(java.util.Optional.of(userModel));
    when(questionsJpaRepository.findById(any())).thenReturn(java.util.Optional.of(questionModel));

    var response = controller.getAllAnswers(PageRequest.of(0, 10));
    assertThat(response.getBody().getContent()).hasSize(1);
  }
}
