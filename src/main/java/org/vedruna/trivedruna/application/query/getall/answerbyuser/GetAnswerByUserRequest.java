package org.vedruna.trivedruna.application.query.getall.answerbyuser;

import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAnswerByUserRequest implements Request<GetAnswerByUserResponse> {

  Integer userId;
  Pageable pageable;
}
