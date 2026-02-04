package org.vedruna.trivedruna.application.query.getall.question;

import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllQuestionRequest implements Request<GetAllQuestionResponse> {

    Pageable pageable;

}
