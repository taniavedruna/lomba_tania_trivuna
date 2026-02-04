package org.vedruna.trivedruna.application.command.refreshtoken;


import org.vedruna.trivedruna.domain.model.AccessToken;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta a una solicitud de refresco de token exitosa.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso. {@code @AllArgsConstructor}: Genera
 * un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RefreshTokenResponse {

  /** El nuevo token de acceso generado tras el refresco. */
  private AccessToken accessToken;
}
