package org.vedruna.trivedruna.application.query.getall.answer;
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
public class GetAllAnswerHandler implements RequestHandler<GetAllAnswerRequest, GetAllAnswerResponse> {
    
    UserAnswerJpaRepository userAnswerJpaRepository;
    @Override
    public GetAllAnswerResponse handle(GetAllAnswerRequest inputRequest) {
      log.info("Obtener todas las respuestas");
      var pageable = inputRequest.getPageable();
      if (pageable == null || pageable.isUnpaged()) {
        pageable = PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "createDate"));
      } else if (pageable.getSort().isUnsorted()) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createDate"));
      }
      return new GetAllAnswerResponse(userAnswerJpaRepository.findAll(pageable));
    }

    @Override
    public Class<GetAllAnswerRequest> getRequestType() {
       return GetAllAnswerRequest.class;
    }

}
