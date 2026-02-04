package org.vedruna.trivedruna.application.query.getbyid.category;


import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCategoryByIdRequest implements Request<GetCategoryByIdResponse> {

    Integer categoryId;

}
