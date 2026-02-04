package org.vedruna.trivedruna.infrastructure.adapters.out;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.domain.ports.outbound.AuthenticationManagerI;

/** Implementacion del puerto AuthenticationManagerI usando Spring Security. */
@Component
@AllArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManagerI {

  private final AuthenticationManager authenticationManager;

  @Override
  public void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
