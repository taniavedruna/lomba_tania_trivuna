package org.vedruna.trivedruna.application.command.create.question;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CreateQuestionHandler implements RequestHandler<CreateQuestionRequest, CreateQuestionResponse> {

    QuestionsJpaRepository questionsJpaRepository;

    @Override
    public CreateQuestionResponse handle(CreateQuestionRequest request) {
        log.info("Crear preguntas");
        return new CreateQuestionResponse(questionsJpaRepository.saveAll(request.getQuestionModels()));
    }

    @Override
    public Class<CreateQuestionRequest> getRequestType() {
        return CreateQuestionRequest.class;
    }
}
