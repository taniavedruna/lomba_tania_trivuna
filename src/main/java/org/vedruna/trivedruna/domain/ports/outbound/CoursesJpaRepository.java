package org.vedruna.trivedruna.domain.ports.outbound;
import org.vedruna.trivedruna.domain.model.CourseModel;

import java.util.List;
import java.util.Optional;

public interface CoursesJpaRepository {

    List<CourseModel> findAll();

    Optional<CourseModel> findByCourseName(String courseName);

    void incrementScore(Integer courseId, Integer score);

}
