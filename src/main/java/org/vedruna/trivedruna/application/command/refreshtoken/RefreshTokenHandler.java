package org.vedruna.trivedruna.application.command.refreshtoken;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.model.AccessToken;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.domain.ports.outbound.UserServiceI;

import io.jsonwebtoken.JwtException;

/**
 * Manejador encargado de renovar tokens de acceso utilizando un token de refresco (Refresh Token).
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * RefreshTokenRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Indica que esta clase es un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para inyectar
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RefreshTokenHandler
    implements RequestHandler<RefreshTokenRequest, RefreshTokenResponse> {

  /** Proveedor para la gestión y validación de tokens JWT. */
  private final JWTProvider jwtProvider;

  /** Servicio para acceder a la lógica de negocio de usuarios. */
  private final UserServiceI userService;

  /**
   * Procesa la solicitud de renovación de token.
   *
   * <p>1. Extrae y valida el nombre de usuario desde el Refresh Token proporcionado. 2. Recupera la
   * información del usuario desde el servicio. 3. Verifica si el Refresh Token es válido y no ha
   * expirado para dicho usuario. 4. Genera un nuevo conjunto de tokens (Access y Refresh). 5.
   * Devuelve la nueva respuesta con los tokens actualizados.
   *
   * @param inputRequest El objeto de solicitud que contiene el Refresh Token actual.
   * @return {@link RefreshTokenResponse} con el nuevo token de acceso.
   * @throws IllegalArgumentException Si el Refresh Token es inválido o ha expirado.
   */
  @Override
  public RefreshTokenResponse handle(RefreshTokenRequest inputRequest) {
    log.info("Handling refresh token request");
    // 1. Validar y obtener el username del Refresh Token
    final String username;
    try {
      username =
          jwtProvider.getUsernameFromRefreshToken(inputRequest.getAccessToken().getRefreshToken());
    } catch (JwtException e) {
      // Captura si el token es inválido (firma, formato, etc.)
      throw new IllegalArgumentException("Refresh Token inválido: " + e.getMessage());
    }

    UserModel user = userService.loadUserByUsername(username);

    if (!jwtProvider.isRefreshTokenValid(inputRequest.getAccessToken().getRefreshToken(), user)) {
      // Captura si el token ha expirado o no pertenece al usuario cargado
      throw new IllegalArgumentException("Refresh Token expirado o no válido para el usuario.");
    }

    return new RefreshTokenResponse(
        new AccessToken(
            jwtProvider.generateAccessToken(user),
            jwtProvider.getAccessTokenExpiresIn(),
            jwtProvider.generateRefreshToken(user),
            user.getScope()));
  }

  /**
   * Define el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link RefreshTokenRequest}.
   */
  @Override
  public Class<RefreshTokenRequest> getRequestType() {
    return RefreshTokenRequest.class;
  }
}
