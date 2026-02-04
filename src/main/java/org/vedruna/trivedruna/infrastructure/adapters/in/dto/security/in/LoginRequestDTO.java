package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de inicio de sesión. */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

  /** El nombre de usuario para la autenticación. */

  @NotBlank(message = "username is required and must not be blank")
  @Size(min = 3, max = 45, message = "username must be between 3 and 45 characters long")
  String username;

  /** La contraseña del usuario. */

  @NotBlank(message = "password is required and must not be blank")
  String password;
}
