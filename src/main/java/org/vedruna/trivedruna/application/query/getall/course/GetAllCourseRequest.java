package org.vedruna.trivedruna.application.query.getall.course;

import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllCourseRequest implements Request<GetAllCourseResponse> {

}
