package org.vedruna.trivedruna.infrastructure.adapters.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserDetailsService userDetailsService;
  @Mock private OutboundConverter outboundConverter;

  @Test
  void cargaUsuarioYLoConvierteAModelo() {
    var entity = new UserEntity();
    entity.setUsername("john");
    var model = new UserModel();
    model.setUsername("john");

    when(userDetailsService.loadUserByUsername("john")).thenReturn(entity);
    when(outboundConverter.toUserModel(entity)).thenReturn(model);

    var impl = new UserServiceImpl(userDetailsService, outboundConverter);

    var result = impl.loadUserByUsername("john");

    assertThat(result.getUsername()).isEqualTo("john");
  }
}
