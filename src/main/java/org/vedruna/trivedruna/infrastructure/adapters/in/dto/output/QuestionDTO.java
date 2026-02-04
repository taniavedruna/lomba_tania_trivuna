package org.vedruna.trivedruna.infrastructure.adapters.in.dto.output;

import lombok.Data;

/**
 * DTO de salida para exponer la información de una pregunta.
 */
@Data
public class QuestionDTO {

  private Integer questionId;
  private String questionText;
  private String incorrectAnswer1;
  private String incorrectAnswer2;
  private String incorrectAnswer3;
  private String correctAnswer;
  private Integer categoryId;
  private String categoryName;
}
