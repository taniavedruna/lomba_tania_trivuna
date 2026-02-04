package org.vedruna.trivedruna.application.query.getbyid.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.exceptions.CategoryNotFoundException;
import org.vedruna.trivedruna.domain.model.CategoryModel;
import org.vedruna.trivedruna.domain.ports.outbound.CategoryJpaRepository;

class GetCategoryByIdHandlerTest {

  @Mock private CategoryJpaRepository repo;
  @InjectMocks private GetCategoryByIdHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void devuelveCategoriaCuandoExiste() {
    CategoryModel model = new CategoryModel();
    when(repo.findById(3)).thenReturn(Optional.of(model));

    var resp = handler.handle(new GetCategoryByIdRequest(3));

    assertThat(resp.getCategoryModel()).isSameAs(model);
  }

  @Test
  void lanzaCuandoNoExiste() {
    when(repo.findById(3)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> handler.handle(new GetCategoryByIdRequest(3)))
        .isInstanceOf(CategoryNotFoundException.class);
  }
}
