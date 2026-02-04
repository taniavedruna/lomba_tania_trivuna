package org.vedruna.trivedruna.application.query.getbyid.question;

import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetQuestionByIdRequest implements Request<GetQuestionByIdResponse> {

    Integer questionId;

}
