package org.vedruna.trivedruna.application.command.refreshtoken;


import org.vedruna.trivedruna.application.cqrs.Request;
import org.vedruna.trivedruna.domain.model.AccessToken;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para refrescar un token de acceso.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso y utilidad.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RefreshTokenRequest implements Request<RefreshTokenResponse> {

  /** El objeto AccessToken que contiene el Refresh Token necesario para la operación. */
  private AccessToken accessToken;
}
