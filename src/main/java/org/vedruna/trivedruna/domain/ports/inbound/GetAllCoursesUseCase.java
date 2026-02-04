package org.vedruna.trivedruna.domain.ports.inbound;

import org.vedruna.trivedruna.domain.model.CourseModel;

import java.util.List;

public interface GetAllCoursesUseCase {

    List<CourseModel> findAll();

}
