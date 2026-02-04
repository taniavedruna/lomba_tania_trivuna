package org.vedruna.trivedruna.infrastructure.adapters.in.dto.output;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de salida para respuestas de usuario.
 * Incluye el id de la pregunta, el nombre del usuario y el enunciado para facilitar la lectura.
 */
@Data
public class AnswerDTO {
  private Integer questionId;
  private String username;
  private String questionText;
  private LocalDateTime createDate;
  private String selectedAnswer;
  private Boolean isCorrect;
}
