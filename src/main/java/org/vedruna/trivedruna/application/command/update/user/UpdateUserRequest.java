package org.vedruna.trivedruna.application.command.update.user;
import org.vedruna.trivedruna.application.cqrs.Request;
import org.vedruna.trivedruna.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest implements Request<UpdateUserResponse>{

    Integer userId;
    UserModel userModel;

}
