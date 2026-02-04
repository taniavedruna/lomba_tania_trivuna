package org.vedruna.trivedruna.infrastructure.adapters.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderImplTest {

  @Mock private PasswordEncoder passwordEncoder;

  @Test
  void usaPasswordEncoderParaEncriptar() {
    when(passwordEncoder.encode("plain")).thenReturn("hashed");
    var impl = new PasswordEncoderImpl(passwordEncoder);

    var encoded = impl.encode("plain");

    assertThat(encoded).isEqualTo("hashed");
  }
}
