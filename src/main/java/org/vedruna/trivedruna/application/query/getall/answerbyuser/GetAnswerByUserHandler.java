package org.vedruna.trivedruna.application.query.getall.answerbyuser;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.ports.outbound.UserAnswerJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetAnswerByUserHandler
    implements RequestHandler<GetAnswerByUserRequest, GetAnswerByUserResponse> {

  private final UserAnswerJpaRepository userAnswerJpaRepository;

  @Override
  public GetAnswerByUserResponse handle(GetAnswerByUserRequest inputRequest) {
    log.info("Obtener respuestas del usuario {}", inputRequest.getUserId());
    var pageable = inputRequest.getPageable();
    if (pageable == null || pageable.isUnpaged()) {
      pageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "createDate"));
    }
    return new GetAnswerByUserResponse(
        userAnswerJpaRepository.findByUserId(inputRequest.getUserId(), pageable));
  }

  @Override
  public Class<GetAnswerByUserRequest> getRequestType() {
    return GetAnswerByUserRequest.class;
  }
}
