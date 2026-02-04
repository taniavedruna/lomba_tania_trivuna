package org.vedruna.trivedruna.infrastructure.adapters.out;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.domain.ports.outbound.PasswordEncoderI;

/** Implementacion del puerto PasswordEncoderI usando Spring Security. */
@Component
@AllArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoderI {

  private final PasswordEncoder passwordEncoder;

  @Override
  public String encode(CharSequence rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
