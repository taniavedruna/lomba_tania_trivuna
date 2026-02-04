package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RequestMapping("/api/v1/categories")
@Tag(name = "CategorÃ­as", description = "Consulta de categorÃ­as")
public interface CategoryController {

  @GetMapping
  @Operation(summary = "Listar categorÃ­as", description = "Devuelve todas las categorÃ­as disponibles")
  ResponseEntity<List<CategoryDTO>> getAllCategories();
}
