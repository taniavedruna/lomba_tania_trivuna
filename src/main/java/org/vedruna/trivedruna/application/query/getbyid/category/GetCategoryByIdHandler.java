package org.vedruna.trivedruna.application.query.getbyid.category;

import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.CategoryNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.CategoryJpaRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetCategoryByIdHandler implements RequestHandler<GetCategoryByIdRequest, GetCategoryByIdResponse> {

    CategoryJpaRepository categoryJpaRepository;

   @Override
    public GetCategoryByIdResponse handle(GetCategoryByIdRequest inputRequest) {
     log.info("Buscar categoria por id");
     return new GetCategoryByIdResponse(categoryJpaRepository.findById(inputRequest.getCategoryId()).orElseThrow(
        CategoryNotFoundException::new
     ));
    }

    @Override
    public Class<GetCategoryByIdRequest> getRequestType() {
        return GetCategoryByIdRequest.class;
    }



    
}
