package org.vedruna.trivedruna.application.query.getall.questionunanswered;

import org.springframework.data.domain.Page;
import org.vedruna.trivedruna.domain.model.QuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUnansweredQuestionResponse {

    Page<QuestionModel> questionModel;

}
