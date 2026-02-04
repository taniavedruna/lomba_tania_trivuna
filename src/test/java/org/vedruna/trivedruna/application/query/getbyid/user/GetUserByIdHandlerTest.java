package org.vedruna.trivedruna.application.query.getbyid.user;

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
class GetUserByIdHandlerTest {

  @Mock private UserJpaRepository userJpaRepository;
  @InjectMocks private GetUserByIdHandler handler;

  @Test
  void retornaUsuarioSiExiste() {
    var user = new UserModel();
    user.setUserId(7);
    when(userJpaRepository.getUserById(7)).thenReturn(Optional.of(user));

    var response = handler.handle(new GetUserByIdRequest(7));

    assertThat(response.getUserModel().getUserId()).isEqualTo(7);
  }

  @Test
  void lanzaUserNotFoundSiNoExiste() {
    when(userJpaRepository.getUserById(8)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> handler.handle(new GetUserByIdRequest(8)))
        .isInstanceOf(UserNotFoundException.class);
  }
}
