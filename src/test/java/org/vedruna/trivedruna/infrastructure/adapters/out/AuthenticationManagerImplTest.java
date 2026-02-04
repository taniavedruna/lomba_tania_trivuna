package org.vedruna.trivedruna.infrastructure.adapters.out;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class AuthenticationManagerImplTest {

  @Mock private AuthenticationManager authenticationManager;

  @Test
  void delegaAutenticacionAlAuthenticationManager() {
    var impl = new AuthenticationManagerImpl(authenticationManager);

    impl.authenticate("john", "pwd");

    verify(authenticationManager)
        .authenticate(new UsernamePasswordAuthenticationToken("john", "pwd"));
  }
}
