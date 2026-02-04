package org.vedruna.trivedruna.application.query.getall.questionbycategory;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.CategoryNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler para recuperar preguntas paginadas de una categoría concreta.
 */
@Service
@AllArgsConstructor
@Slf4j
public class GetQuestionByCategoryHandler
    implements RequestHandler<GetQuestionByCategoryRequest, GetQuestionByCategoryResponse> {

  private final QuestionsJpaRepository questionsJpaRepository;

  @Override
  public GetQuestionByCategoryResponse handle(GetQuestionByCategoryRequest inputRequest) {
    log.info("Obtener preguntas por categoría {}", inputRequest.getCategoryId());
    return new GetQuestionByCategoryResponse(
        questionsJpaRepository
            .findByCategoryId(inputRequest.getCategoryId(), inputRequest.getPageable())
            .orElseThrow(CategoryNotFoundException::new));
  }

  @Override
  public Class<GetQuestionByCategoryRequest> getRequestType() {
    return GetQuestionByCategoryRequest.class;
  }
}
