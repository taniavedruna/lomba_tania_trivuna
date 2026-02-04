package org.vedruna.trivedruna.application.query.getall.questionunanswered;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.CategoryNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetUnansweredQuestionHandler
        implements RequestHandler<GetUnansweredQuestionRequest, GetUnansweredQuestionResponse> {

    QuestionsJpaRepository questionsJpaRepository;

    @Override
    public GetUnansweredQuestionResponse handle(GetUnansweredQuestionRequest inputRequest) {
        log.info("Obtener preguntas sin responder por categoria");
        return new GetUnansweredQuestionResponse(questionsJpaRepository.findUnansweredByCategory(
            inputRequest.getUserId(),
            inputRequest.getCategoryId(),
            inputRequest.getPageable()
        ).orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public Class<GetUnansweredQuestionRequest> getRequestType() {
        return GetUnansweredQuestionRequest.class;
    }
}
