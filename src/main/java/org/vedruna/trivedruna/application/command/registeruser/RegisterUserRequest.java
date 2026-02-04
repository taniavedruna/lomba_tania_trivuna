package org.vedruna.trivedruna.application.command.registeruser;




import org.vedruna.trivedruna.application.cqrs.Request;
import org.vedruna.trivedruna.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para registrar un nuevo usuario en el sistema.
 *
 * <p>{@code @Data}: Anotación de Lombok para generar métodos boilerplate.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RegisterUserRequest implements Request<RegisterUserResponse> {

  /** Modelo que contiene la información del usuario a registrar. */
  private UserModel user;

  /** Nombre del curso para resolver el identificador. */
  private String courseName;
}
