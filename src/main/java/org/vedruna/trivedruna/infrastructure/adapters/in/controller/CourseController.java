package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CourseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RequestMapping("/api/v1/courses")
@Tag(name = "Cursos", description = "Consulta de cursos disponibles")
public interface CourseController {

  /**
   * Obtiene todos los cursos sin paginación.
   */
  @GetMapping
  @Operation(summary = "Listar cursos", description = "Devuelve todos los cursos sin paginar")
  ResponseEntity<List<CourseDTO>> getAllCourses();
}
