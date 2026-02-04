package org.vedruna.trivedruna.application.query.getbyid.user;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.exceptions.UserNotFoundException;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class GetUserByIdHandler implements RequestHandler<GetUserByIdRequest, GetUserByIdResponse> {
    
    UserJpaRepository userJpaRepository;
    
    @Override
    public GetUserByIdResponse handle(GetUserByIdRequest inputRequest) {
        log.info("Buscar usuario por id");
        return new GetUserByIdResponse(userJpaRepository.getUserById(inputRequest.getUserId()).orElseThrow(
            UserNotFoundException::new
        ));
    }

    @Override
    public Class<GetUserByIdRequest> getRequestType() {
       return GetUserByIdRequest.class;
    }

}
