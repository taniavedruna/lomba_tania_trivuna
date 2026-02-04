package org.vedruna.trivedruna.infrastructure.adapters.in.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de entrada para responder a una pregunta.
 */
@Data
public class SubmitAnswerRequestDTO {

  @NotNull
  private Integer questionId;

  @NotBlank
  private String selectedAnswer;
}
