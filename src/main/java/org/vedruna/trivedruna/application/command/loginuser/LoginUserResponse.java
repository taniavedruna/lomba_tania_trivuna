package org.vedruna.trivedruna.application.command.loginuser;


import org.vedruna.trivedruna.domain.model.AccessToken;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta a una solicitud de inicio de sesión exitosa.
 *
 * <p>{@code @Data}: Anotación de Lombok que genera automáticamente getters, setters, toString,
 * equals y hashCode. {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class LoginUserResponse {

  /** El token de acceso generado para el usuario autenticado. */
  private AccessToken accessToken;
}
