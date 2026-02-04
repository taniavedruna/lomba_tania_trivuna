package org.vedruna.trivedruna.application.command.create.question;

import java.util.List;

import org.vedruna.trivedruna.domain.model.QuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuestionResponse {

    List<QuestionModel> questionModel;

}
