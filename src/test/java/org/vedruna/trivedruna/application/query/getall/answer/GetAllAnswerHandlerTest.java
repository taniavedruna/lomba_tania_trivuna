package org.vedruna.trivedruna.application.query.getall.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.UserAnswerJpaRepository;

class GetAllAnswerHandlerTest {

  @Mock private UserAnswerJpaRepository repo;
  @InjectMocks private GetAllAnswerHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void devuelvePaginaDeRespuestas() {
    var page = new PageImpl<>(java.util.List.of(new UserAnswerQuestionModel()));
    when(repo.findAll(any())).thenReturn(page);

    var resp = handler.handle(new GetAllAnswerRequest(PageRequest.of(0, 10)));

    assertThat(resp.getUserAnswerQuestionModel().getContent()).hasSize(1);

    var captor = ArgumentCaptor.forClass(org.springframework.data.domain.Pageable.class);
    verify(repo).findAll(captor.capture());
    assertThat(captor.getValue().getSort().isSorted()).isTrue();
  }
}
