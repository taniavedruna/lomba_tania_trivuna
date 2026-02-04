package org.vedruna.trivedruna.application.query.getall.category;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.ports.outbound.CategoryJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllCategoryHandler
    implements RequestHandler<GetAllCategoryRequest, GetAllCategoryResponse> {

  private final CategoryJpaRepository categoryJpaRepository;

  @Override
  public GetAllCategoryResponse handle(GetAllCategoryRequest inputRequest) {
    log.info("Listar todas las categorias");
    return new GetAllCategoryResponse(categoryJpaRepository.findAll());
  }

  @Override
  public Class<GetAllCategoryRequest> getRequestType() {
    return GetAllCategoryRequest.class;
  }
}
