package org.vedruna.trivedruna.application.query.getall.course;

import java.util.List;

import org.vedruna.trivedruna.domain.model.CourseModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllCourseResponse {

    List<CourseModel> courseModel;

}
