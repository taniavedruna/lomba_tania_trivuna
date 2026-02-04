package org.vedruna.trivedruna.domain.ports.inbound;

import org.vedruna.trivedruna.domain.model.UserModel;

public interface UpdateUserUseCase {
    UserModel updateUser(Integer userId, UserModel userModel);

}
