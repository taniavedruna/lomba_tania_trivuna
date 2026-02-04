package org.vedruna.trivedruna.application.query.getall.question;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllQuestionHandler implements RequestHandler<GetAllQuestionRequest, GetAllQuestionResponse> {

    QuestionsJpaRepository questionsJpaRepository;

    @Override
    public GetAllQuestionResponse handle(GetAllQuestionRequest inputRequest) {
        log.info("Obtener todas las preguntas");
        return new GetAllQuestionResponse(questionsJpaRepository.findAll(inputRequest.getPageable()));
    }

    @Override
    public Class<GetAllQuestionRequest> getRequestType() {
        return GetAllQuestionRequest.class;
    }
}
