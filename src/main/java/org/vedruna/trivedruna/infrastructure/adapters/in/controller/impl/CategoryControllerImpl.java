package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getall.category.GetAllCategoryRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.CategoryController;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.output.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryControllerImpl implements CategoryController {

  private final Mediator mediator;

  @Override
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    log.info("Listar categorÃ­as");
    var categories =
        mediator.dispatch(new GetAllCategoryRequest()).getCategoryModels().stream()
            .map(model -> {
              CategoryDTO dto = new CategoryDTO();
              dto.setCategoryId(model.getCategoryId());
              dto.setCategoryName(model.getCategoryName());
              return dto;
            })
            .toList();
    return ResponseEntity.ok(categories);
  }
}
