package org.vedruna.trivedruna.application.command.create.answer;


import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAnswerResponse {

    UserAnswerQuestionModel userAnswerQuestionModel;

}
