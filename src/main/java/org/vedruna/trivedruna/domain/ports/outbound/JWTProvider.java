package org.vedruna.trivedruna.domain.ports.outbound;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.vedruna.trivedruna.domain.model.UserModel;

/** Puerto de salida que define las operaciones para la gestión de tokens JWT. */
public interface JWTProvider {
  /**
   * Obtiene la clave de firma a partir de una cadena secreta.
   *
   * @param secretKey La cadena secreta.
   * @return La clave en formato de arreglo de bytes.
   */
  byte[] getKey(String secretKey);

  /**
   * Construye un token JWT con propiedades personalizadas.
   *
   * @param extraClaims Reclamaciones adicionales a incluir.
   * @param user El usuario para el que se genera el token.
   * @param expirationTime Tiempo de expiración.
   * @param secretKey Clave secreta para la firma.
   * @return El token generado.
   */
  String buildToken(
      Map<String, Object> extraClaims, UserModel user, Long expirationTime, String secretKey);

  /**
   * Genera un token de acceso para un usuario.
   *
   * @param user El modelo de usuario.
   * @return El token de acceso generado.
   */
  String generateAccessToken(UserModel user);

  /**
   * Genera un token de refresco para un usuario.
   *
   * @param user El modelo de usuario.
   * @return El token de refresco generado.
   */
  String generateRefreshToken(UserModel user);

  /**
   * Obtiene todas las reclamaciones (claims) de un token.
   *
   * @param token El token JWT.
   * @param secretKey La clave secreta de validación.
   * @return Mapa con todas las reclamaciones.
   */
  Map<String, Object> getAllClaims(String token, String secretKey);

  /**
   * Extrae una reclamación específica del token.
   *
   * @param <T> Tipo del valor devuelto.
   * @param token El token JWT.
   * @param claimsResolver Función para resolver la reclamación.
   * @param secretKey La clave secreta.
   * @return El valor de la reclamación.
   */
  <T> T getClaim(String token, Function<Map<String, Object>, T> claimsResolver, String secretKey);

  /**
   * Obtiene el nombre de usuario desde un token de acceso.
   *
   * @param token El token de acceso.
   * @return El nombre de usuario.
   */
  String getUsernameFromAccessToken(String token);

  /**
   * Obtiene el tiempo de expiración configurado para los tokens de acceso.
   *
   * @return Tiempo en segundos.
   */
  Long getAccessTokenExpiresIn();

  /**
   * Obtiene la fecha de expiración de un token de acceso concreto.
   *
   * @param token El token de acceso.
   * @return La fecha de expiración.
   */
  Date getAccessTokenExpiration(String token);

  /**
   * Comprueba si un token de acceso ha caducado.
   *
   * @param token El token a comprobar.
   * @return true si ha caducado.
   */
  boolean isAccessTokenExpired(String token);

  /**
   * Valida si un token de acceso es correcto para un usuario dado.
   *
   * @param token El token a validar.
   * @param user El modelo de usuario.
   * @return true si el token es válido.
   */
  boolean isAccessTokenValid(String token, UserModel user);

  /**
   * Obtiene el nombre de usuario desde un token de refresco.
   *
   * @param token El token de refresco.
   * @return El nombre de usuario.
   */
  String getUsernameFromRefreshToken(String token);

  /**
   * Obtiene el tiempo de expiración configurado para los tokens de refresco.
   *
   * @return Tiempo en segundos.
   */
  Long getRefreshTokenExpiresIn();

  /**
   * Obtiene la fecha de expiración de un token de refresco concreto.
   *
   * @param token El token de refresco.
   * @return La fecha de expiración.
   */
  Date getRefreshTokenExpiration(String token);

  /**
   * Comprueba si un token de refresco ha caducado.
   *
   * @param token El token a comprobar.
   * @return true si ha caducado.
   */
  boolean isRefreshTokenExpired(String token);

  /**
   * Valida si un token de refresco es correcto para un usuario dado.
   *
   * @param token El token a validar.
   * @param user El modelo de usuario.
   * @return true si el token es válido.
   */
  boolean isRefreshTokenValid(String token, UserModel user);
}
