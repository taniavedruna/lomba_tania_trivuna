package org.vedruna.trivedruna.application.command.update.user;
import org.vedruna.trivedruna.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponse {

    UserModel userModel;

}
