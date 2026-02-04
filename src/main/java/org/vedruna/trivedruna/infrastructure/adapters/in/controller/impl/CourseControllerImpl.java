package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.course.GetAllCourseRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.CourseController;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/courses")
public class CourseControllerImpl implements CourseController {

  private final Mediator mediator;
  private final InboundConverter inboundConverter;

  /**
   * Recupera todos los cursos sin paginación.
   *
   * @return lista completa de cursos con su puntuación.
   */
  @Override
  public ResponseEntity<List<CourseDTO>> getAllCourses() {
    log.info("Listar todos los cursos");
    var courses =
        mediator
            .dispatch(new GetAllCourseRequest())
            .getCourseModel()
            .stream()
            .map(inboundConverter::toCourseDTO)
            .toList();
    return ResponseEntity.ok(courses);
  }
}
