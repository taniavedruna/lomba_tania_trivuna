package org.vedruna.trivedruna.application.query.getall.questionbycategory;

import org.springframework.data.domain.Page;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Respuesta con la página de preguntas filtradas por categoría.
 */
@Data
@AllArgsConstructor
public class GetQuestionByCategoryResponse {

  private Page<QuestionModel> questionModel;
}
