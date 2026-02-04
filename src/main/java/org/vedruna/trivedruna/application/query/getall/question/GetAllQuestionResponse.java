package org.vedruna.trivedruna.application.query.getall.question;

import org.springframework.data.domain.Page;
import org.vedruna.trivedruna.domain.model.QuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllQuestionResponse {

    Page<QuestionModel> questionModel;

}
