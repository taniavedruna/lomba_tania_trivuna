package org.vedruna.trivedruna.domain.ports.outbound;

/** Puerto de salida para la codificación de contraseñas. */
public interface PasswordEncoderI {

  /**
   * Codifica una contraseña en texto plano.
   *
   * @param rawPassword La contraseña original.
   * @return La contraseña codificada de forma segura.
   */
  String encode(CharSequence rawPassword);
}
