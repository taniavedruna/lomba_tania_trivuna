package org.vedruna.trivedruna.application.command.update.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.PasswordEncoderI;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

class UpdateUserHandlerTest {

  @Mock private UserJpaRepository userJpaRepository;
  @Mock private PasswordEncoderI passwordEncoder;
  @InjectMocks private UpdateUserHandler handler;

  private UserModel current;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    current = new UserModel();
    current.setUserId(1);
    current.setUsername("old");
    current.setPassword("oldpass");
  }

  @Test
  void actualizaCamposYEncriptaPassword() {
    UserModel updates = new UserModel();
    updates.setUsername("new");
    updates.setPassword("plain");
    updates.setAvatarUrl("a");
    when(userJpaRepository.getUserById(1)).thenReturn(Optional.of(current));
    when(passwordEncoder.encode("plain")).thenReturn("hashed");
    when(userJpaRepository.updateUser(any(), any())).thenAnswer(inv -> inv.getArgument(1));

    var resp = handler.handle(new UpdateUserRequest(1, updates));

    assertThat(resp.getUserModel().getUsername()).isEqualTo("new");
    assertThat(resp.getUserModel().getPassword()).isEqualTo("hashed");
    assertThat(resp.getUserModel().getAvatarUrl()).isEqualTo("a");
    verify(userJpaRepository).updateUser(any(), any());
  }

  @Test
  void lanzaSiNoExisteUsuario() {
    when(userJpaRepository.getUserById(1)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> handler.handle(new UpdateUserRequest(1, new UserModel())))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
