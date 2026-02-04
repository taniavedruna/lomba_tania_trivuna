package org.vedruna.trivedruna.application.query.getall.questionunanswered;

import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUnansweredQuestionRequest implements Request<GetUnansweredQuestionResponse> {

    Integer userId;
    Integer categoryId;
    Pageable pageable;

}
