package org.vedruna.trivedruna.domain.ports.inbound;

import org.vedruna.trivedruna.domain.model.QuestionModel;

public interface GetAQuestionByIdUseCase {

    QuestionModel findById(Integer questionId);

}
