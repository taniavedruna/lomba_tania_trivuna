package org.vedruna.trivedruna.application.query.getall.answer;

import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllAnswerRequest implements Request<GetAllAnswerResponse> {

    Pageable pageable;

}
