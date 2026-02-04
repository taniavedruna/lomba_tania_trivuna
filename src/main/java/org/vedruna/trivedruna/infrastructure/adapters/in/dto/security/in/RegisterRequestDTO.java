package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de registro de un nuevo usuario. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

  @NotBlank(message = "username is required and must not be blank")
  @Size(min = 3, max = 45, message = "username must be between 3 and 45 characters long")
  private String username;

  /** El email del usuario para el registro. */
  @NotBlank(message = "email is required and must not be blank")
  @Email(message = "email must be valid")
  private String email;

  /** Curso asignado al usuario (obligatorio). */
  @NotBlank(message = "courseName is required and must not be blank")
  private String courseName;

  /** La contrasena elegida, debe cumplir los criterios de complejidad. */
  @NotBlank(message = "password is required and must not be blank")
  private String password;

  private String avatarUrl;

  @NotBlank(message = "name is required and must not be blank")
  private String name;
  
  @NotBlank(message = "surname1 is required and must not be blank")
  private String surname1;

  private String surname2;
}
