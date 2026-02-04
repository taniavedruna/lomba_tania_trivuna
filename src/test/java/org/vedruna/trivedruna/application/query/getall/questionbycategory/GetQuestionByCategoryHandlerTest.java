package org.vedruna.trivedruna.application.query.getall.questionbycategory;

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

class GetQuestionByCategoryHandlerTest {

  @Mock private QuestionsJpaRepository repo;
  @InjectMocks private GetQuestionByCategoryHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void devuelvePaginaPorCategoria() {
    var page = new PageImpl<>(java.util.List.of(new QuestionModel()));
    when(repo.findByCategoryId(1, PageRequest.of(0, 5))).thenReturn(java.util.Optional.of(page));

    var resp = handler.handle(new GetQuestionByCategoryRequest(1, PageRequest.of(0, 5)));

    assertThat(resp.getQuestionModel().getContent()).hasSize(1);
  }
}
