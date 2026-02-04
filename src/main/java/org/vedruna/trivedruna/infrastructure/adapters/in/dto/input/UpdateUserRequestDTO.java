package org.vedruna.trivedruna.infrastructure.adapters.in.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar los datos del usuario autenticado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {

  @Size(min = 3, max = 45, message = "username must be between 3 and 45 characters long")
  private String username;

  @Email(message = "email must be valid")
  private String email;

  private String password;
  private String avatarUrl;
  private String name;
  private String surname1;
  private String surname2;
}
