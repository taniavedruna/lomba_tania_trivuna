package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.CourseModel;
import org.vedruna.trivedruna.domain.ports.outbound.CoursesJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.CourseRepository;

@Service
@AllArgsConstructor
public class CoursesJpaRepositoryImpl implements CoursesJpaRepository {

  private final CourseRepository courseRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public List<CourseModel> findAll() {
    return courseRepository.findAll().stream().map(outboundConverter::toCourseModel).toList();
  }

  @Override
  public Optional<CourseModel> findByCourseName(String courseName) {
    return courseRepository.findByCourseName(courseName).map(outboundConverter::toCourseModel);
  }

  @Override
  public void incrementScore(Integer courseId, Integer score) {
    courseRepository.incrementScore(courseId, score);
  }
}
