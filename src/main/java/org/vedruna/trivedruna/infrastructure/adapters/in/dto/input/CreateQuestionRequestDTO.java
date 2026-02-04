package org.vedruna.trivedruna.infrastructure.adapters.in.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de entrada para crear una pregunta.
 */
@Data
public class CreateQuestionRequestDTO {

  @NotBlank
  private String questionText;

  @NotBlank
  private String incorrectAnswer1;

  @NotBlank
  private String incorrectAnswer2;

  @NotBlank
  private String incorrectAnswer3;

  @NotBlank
  private String correctAnswer;

  @NotNull
  private Integer categoryId;

  @NotNull
  private Integer courseId;
}
