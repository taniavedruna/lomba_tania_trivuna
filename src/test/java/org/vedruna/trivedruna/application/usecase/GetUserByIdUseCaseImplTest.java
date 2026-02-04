package org.vedruna.trivedruna.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.trivedruna.domain.exceptions.UserNotFoundException;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseImplTest {

  @Mock private UserJpaRepository userJpaRepository;
  @InjectMocks private GetUserByIdUseCaseImpl useCase;

  @Test
  void devuelveUsuarioCuandoExiste() {
    var user = new UserModel();
    user.setUserId(1);
    when(userJpaRepository.getUserById(1)).thenReturn(Optional.of(user));

    var result = useCase.getUserById(1);

    assertThat(result.getUserId()).isEqualTo(1);
  }

  @Test
  void lanzaExcepcionSiNoExiste() {
    when(userJpaRepository.getUserById(99)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> useCase.getUserById(99))
        .isInstanceOf(UserNotFoundException.class);
  }
}
