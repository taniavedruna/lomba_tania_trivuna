package org.vedruna.trivedruna.application.command.registeruser;


import org.vedruna.trivedruna.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta tras el registro exitoso de un usuario.
 *
 * <p>{@code @Data}: Anotación de Lombok para getters y setters. {@code @AllArgsConstructor}: Genera
 * un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RegisterUserResponse {

  /** El modelo del usuario que ha sido registrado y persistido. */
  private UserModel user;
}
