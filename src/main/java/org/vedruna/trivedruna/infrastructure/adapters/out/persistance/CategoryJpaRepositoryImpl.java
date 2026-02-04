package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.CategoryModel;
import org.vedruna.trivedruna.domain.ports.outbound.CategoryJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryJpaRepositoryImpl implements CategoryJpaRepository {

  private final CategoryRepository categoryRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public List<CategoryModel> findAll() {
    return categoryRepository.findAll().stream().map(outboundConverter::toCategoryModel).toList();
  }

  @Override
  public Optional<CategoryModel> findById(Integer categoryId) {
    return categoryRepository.findById(categoryId).map(outboundConverter::toCategoryModel);
  }
}
