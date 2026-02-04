package org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la respuesta a una autenticación exitosa.
 *
 * <p>{@code @Schema}: Documentación de OpenAPI. {@code @Data}: Métodos boilerplate.
 * {@code @Builder}: Soporte para el patrón Builder. {@code @AllArgsConstructor}: Constructor
 * completo. {@code @NoArgsConstructor}: Constructor vacío.
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

  /** Tipo de token (siempre "Bearer"). */
  
  final String tokenType = "Bearer";

  /** Token de acceso JWT generado. */
  
  String accessToken;

  /** Tiempo de expiración del token en segundos. */
  
  Long expiresIn;

  /** Token de refresco para renovar el acceso. */
  
  String refreshToken;

  /** Permisos o ámbitos otorgados al token. */
  
  String scope;
}
