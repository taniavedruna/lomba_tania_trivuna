package org.vedruna.trivedruna.application.query.getbyid.user;
import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserByIdRequest implements Request<GetUserByIdResponse>{

    Integer userId;

}
