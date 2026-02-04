package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.vedruna.trivedruna.application.command.create.question.CreateQuestionRequest;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.question.GetAllQuestionRequest;
import org.vedruna.trivedruna.application.query.getall.questionbycategory.GetQuestionByCategoryRequest;
import org.vedruna.trivedruna.application.query.getall.questionunanswered.GetUnansweredQuestionRequest;
import org.vedruna.trivedruna.application.query.getbyid.question.GetQuestionByIdRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl.QuestionControllerImpl;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.CreateQuestionRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.QuestionDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.domain.model.QuestionModel;

class QuestionControllerImplTest {

  @Mock private Mediator mediator;
  @Mock private InboundConverter inboundConverter;
  @InjectMocks private QuestionControllerImpl controller;

  private UserDTO admin;
  private CreateQuestionRequestDTO dto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    admin = new UserDTO();
    admin.setUserId(1);
    dto = new CreateQuestionRequestDTO();
    dto.setQuestionText("pregunta");
    dto.setCorrectAnswer("c");
    dto.setIncorrectAnswer1("a");
    dto.setIncorrectAnswer2("b");
    dto.setIncorrectAnswer3("d");
    dto.setCategoryId(1);
    dto.setCourseId(1);
  }

  @Test
  void creaPreguntasDevuelveLista() {
    var modelResponse = new org.vedruna.trivedruna.application.command.create.question.CreateQuestionResponse(
        List.of(new org.vedruna.trivedruna.domain.model.QuestionModel()));
    when(inboundConverter.toQuestionModel(dto)).thenReturn(new org.vedruna.trivedruna.domain.model.QuestionModel());
    when(inboundConverter.toQuestionDTO(any())).thenReturn(new QuestionDTO());
    when(mediator.dispatch(any(CreateQuestionRequest.class))).thenReturn(modelResponse);

    var response = controller.createQuestions(List.of(dto), admin);

    assertThat(response.getBody()).hasSize(1);
  }

  @Test
  void getAllQuestionsDevuelvePagina() {
    Page<QuestionModel> page = new PageImpl<>(List.of(new QuestionModel()));
    when(mediator.dispatch(any(GetAllQuestionRequest.class)))
        .thenReturn(new org.vedruna.trivedruna.application.query.getall.question.GetAllQuestionResponse(page));
    when(inboundConverter.toQuestionDTO(any(QuestionModel.class))).thenReturn(new QuestionDTO());

    var res = controller.getAllQuestions(PageRequest.of(0, 10));
    assertThat(res.getBody().getContent()).hasSize(1);
  }

  @Test
  void getQuestionsByCategoryDevuelvePagina() {
    Page<QuestionModel> page = new PageImpl<>(List.of(new QuestionModel()));
    when(mediator.dispatch(any(GetQuestionByCategoryRequest.class)))
        .thenReturn(new org.vedruna.trivedruna.application.query.getall.questionbycategory.GetQuestionByCategoryResponse(page));
    when(inboundConverter.toQuestionDTO(any(QuestionModel.class))).thenReturn(new QuestionDTO());

    var res = controller.getQuestionsByCategory(1, PageRequest.of(0, 10));
    assertThat(res.getBody().getContent()).hasSize(1);
  }

  @Test
  void getUnansweredByCategoryDevuelvePagina() {
    Page<QuestionModel> page = new PageImpl<>(List.of(new QuestionModel()));
    when(mediator.dispatch(any(GetUnansweredQuestionRequest.class)))
        .thenReturn(new org.vedruna.trivedruna.application.query.getall.questionunanswered.GetUnansweredQuestionResponse(page));
    when(inboundConverter.toQuestionDTO(any(QuestionModel.class))).thenReturn(new QuestionDTO());

    var res = controller.getUnansweredByCategory(1, admin, PageRequest.of(0, 10));
    assertThat(res.getBody().getContent()).hasSize(1);
  }

  @Test
  void getQuestionByIdDevuelveDetalle() {
    var model = new org.vedruna.trivedruna.domain.model.QuestionModel();
    when(mediator.dispatch(any(GetQuestionByIdRequest.class)))
        .thenReturn(new org.vedruna.trivedruna.application.query.getbyid.question.GetQuestionByIdResponse(model));
    var dtoMapped = new QuestionDTO();
    dtoMapped.setQuestionText("p");
    when(inboundConverter.toQuestionDTO(model)).thenReturn(dtoMapped);

    var res = controller.getQuestionById(5);
    assertThat(res.getBody().getQuestionText()).isEqualTo("p");
  }
}
