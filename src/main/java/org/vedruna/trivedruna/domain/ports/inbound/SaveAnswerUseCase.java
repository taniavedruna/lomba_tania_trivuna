package org.vedruna.trivedruna.domain.ports.inbound;

import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

public interface SaveAnswerUseCase {

     UserAnswerQuestionModel save(String selectedAnswer, Integer usersUserId, Integer questionsQuestionId);

}
