package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.CourseEntity;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

    Optional<CourseEntity> findByCourseName(String courseName);

    @Modifying
    @Query("update CourseEntity c set c.courseScore = c.courseScore + :score where c.courseId = :courseId")
    int incrementScore(@Param("courseId") Integer courseId, @Param("score") Integer score);
}
