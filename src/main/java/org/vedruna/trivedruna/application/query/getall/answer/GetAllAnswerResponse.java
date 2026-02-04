package org.vedruna.trivedruna.application.query.getall.answer;
import org.springframework.data.domain.Page;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllAnswerResponse {

    Page<UserAnswerQuestionModel> userAnswerQuestionModel;

}
