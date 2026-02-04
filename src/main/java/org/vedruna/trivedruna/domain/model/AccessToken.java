package org.vedruna.trivedruna.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que representa el token de acceso JWT y su información relacionada.
 *
 * <p>{@code @Data}: Genera los métodos de acceso mediante Lombok. {@code @NoArgsConstructor}:
 * Genera constructor sin argumentos. {@code @AllArgsConstructor}: Genera constructor con todos los
 * argumentos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

  /** Tipo de token, por defecto "Bearer". */
  private final String tokenType = "Bearer";

  /** El token JWT propiamente dicho. */
  private String accessToken;

  /** El tiempo en segundos que el token permanece activo. */
  private Long expiresIn;

  /** Token de larga duración utilizado para renovar el token de acceso expirado. */
  private String refreshToken;

  /** Ámbito o permisos asociados a este token. */
  private String scope;
}
