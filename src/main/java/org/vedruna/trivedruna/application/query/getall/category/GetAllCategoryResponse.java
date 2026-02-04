package org.vedruna.trivedruna.application.query.getall.category;

import java.util.List;
import org.vedruna.trivedruna.domain.model.CategoryModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllCategoryResponse {

  List<CategoryModel> categoryModels;
}
