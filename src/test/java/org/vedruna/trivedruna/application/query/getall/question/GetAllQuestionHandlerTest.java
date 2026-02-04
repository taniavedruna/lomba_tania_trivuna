package org.vedruna.trivedruna.application.query.getall.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

class GetAllQuestionHandlerTest {

  @Mock private QuestionsJpaRepository repo;
  @InjectMocks private GetAllQuestionHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void devuelvePaginaDePreguntas() {
    var page = new PageImpl<>(java.util.List.of(new QuestionModel()));
    when(repo.findAll(PageRequest.of(0, 5))).thenReturn(page);

    var resp = handler.handle(new GetAllQuestionRequest(PageRequest.of(0, 5)));

    assertThat(resp.getQuestionModel().getContent()).hasSize(1);
  }
}
