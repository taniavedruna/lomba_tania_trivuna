package org.vedruna.trivedruna.domain.ports.outbound;

import java.util.List;
import java.util.Optional;

import org.vedruna.trivedruna.domain.model.CategoryModel;

public interface CategoryJpaRepository {

    List<CategoryModel> findAll();

    Optional<CategoryModel> findById(Integer categoryId);

}
