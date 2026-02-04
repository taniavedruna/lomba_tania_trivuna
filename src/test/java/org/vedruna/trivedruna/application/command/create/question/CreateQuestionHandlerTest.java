package org.vedruna.trivedruna.application.command.create.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

@ExtendWith(MockitoExtension.class)
class CreateQuestionHandlerTest {

  @Mock private QuestionsJpaRepository questionsJpaRepository;
  @InjectMocks private CreateQuestionHandler handler;

  @Test
  void guardaListaDePreguntas() {
    var question = new QuestionModel();
    var request = new CreateQuestionRequest(List.of(question));
    when(questionsJpaRepository.saveAll(request.getQuestionModels()))
        .thenReturn(List.of(question));

    var response = handler.handle(request);

    verify(questionsJpaRepository).saveAll(request.getQuestionModels());
    assertThat(response.getQuestionModel()).containsExactly(question);
  }
}
