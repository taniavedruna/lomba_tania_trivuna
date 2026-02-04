package org.vedruna.trivedruna.application.query.getbyid.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.exceptions.QuestionNotFoundException;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

class GetQuestionByIdHandlerTest {

  @Mock private QuestionsJpaRepository repo;
  @InjectMocks private GetQuestionByIdHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void devuelvePreguntaCuandoExiste() {
    QuestionModel model = new QuestionModel();
    when(repo.findById(5)).thenReturn(Optional.of(model));

    var resp = handler.handle(new GetQuestionByIdRequest(5));

    assertThat(resp.getQuestionModel()).isSameAs(model);
  }

  @Test
  void lanzaCuandoNoExiste() {
    when(repo.findById(5)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> handler.handle(new GetQuestionByIdRequest(5)))
        .isInstanceOf(QuestionNotFoundException.class);
  }
}
