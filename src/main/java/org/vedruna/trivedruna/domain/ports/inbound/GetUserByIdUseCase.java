package org.vedruna.trivedruna.domain.ports.inbound;


import org.vedruna.trivedruna.domain.model.UserModel;

public interface GetUserByIdUseCase {

    UserModel getUserById(Integer userId);

}
