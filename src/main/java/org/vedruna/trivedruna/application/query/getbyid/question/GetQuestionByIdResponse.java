package org.vedruna.trivedruna.application.query.getbyid.question;

import org.vedruna.trivedruna.domain.model.QuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetQuestionByIdResponse {

    QuestionModel questionModel;

}
