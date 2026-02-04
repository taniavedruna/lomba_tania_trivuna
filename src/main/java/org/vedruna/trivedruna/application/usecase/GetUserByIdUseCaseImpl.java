package org.vedruna.trivedruna.application.usecase;


import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.domain.exceptions.UserNotFoundException;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.inbound.GetUserByIdUseCase;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    UserJpaRepository  userJpaRepository;

    @Override
    public UserModel getUserById(Integer userId) {
    log.info("Buscanr usuario por id");
     return userJpaRepository.getUserById(userId).orElseThrow(
        UserNotFoundException::new
     );
       
    }

  

}
