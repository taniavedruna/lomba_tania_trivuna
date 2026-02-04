package org.vedruna.trivedruna.domain.ports.inbound;

import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

public interface RespondQuestionUseCase {

    UserAnswerQuestionModel respond(String selectedAnswer,Integer usersUserId, Integer questionId);

}
