package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
