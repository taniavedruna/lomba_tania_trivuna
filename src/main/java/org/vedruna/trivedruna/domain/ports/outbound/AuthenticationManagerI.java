package org.vedruna.trivedruna.domain.ports.outbound;

/** Puerto de salida para la gestión de la autenticación de usuarios. */
public interface AuthenticationManagerI {
  /**
   * Autentica a un usuario en el sistema utilizando su nombre de usuario y contraseña.
   *
   * @param username El nombre de usuario.
   * @param password La contraseña en texto plano.
   */
  void authenticate(String username, String password);
}
