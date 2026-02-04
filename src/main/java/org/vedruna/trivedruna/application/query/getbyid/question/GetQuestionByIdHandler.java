package org.vedruna.trivedruna.application.query.getbyid.question;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.QuestionNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetQuestionByIdHandler implements RequestHandler<GetQuestionByIdRequest, GetQuestionByIdResponse> {

    QuestionsJpaRepository questionsJpaRepository;

    @Override
    public GetQuestionByIdResponse handle(GetQuestionByIdRequest inputRequest) {
        log.info("Buscar pregunta por id");
        return new GetQuestionByIdResponse(questionsJpaRepository.findById(inputRequest.getQuestionId()).orElseThrow(
            QuestionNotFoundException::new
        ));
    }

    @Override
    public Class<GetQuestionByIdRequest> getRequestType() {
        return GetQuestionByIdRequest.class;
    }
}
