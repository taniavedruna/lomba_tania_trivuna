package org.vedruna.trivedruna.application.query.getbyid.user;

import org.vedruna.trivedruna.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserByIdResponse {

    UserModel userModel;

}
