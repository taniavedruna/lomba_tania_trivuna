package org.vedruna.trivedruna.infrastructure.adapters.in.dto.output;

import lombok.Data;

/**
 * DTO de salida para cursos.
 */
@Data
public class CourseDTO {
  private Integer courseId;
  private String courseName;
  private Integer courseScore;
}
