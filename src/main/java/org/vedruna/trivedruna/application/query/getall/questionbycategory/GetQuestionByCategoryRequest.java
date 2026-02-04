package org.vedruna.trivedruna.application.query.getall.questionbycategory;

import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.application.cqrs.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Solicitud para obtener preguntas de una categoría con paginación.
 */
@Data
@AllArgsConstructor
public class GetQuestionByCategoryRequest implements Request<GetQuestionByCategoryResponse> {

  private Integer categoryId;
  private Pageable pageable;
}
