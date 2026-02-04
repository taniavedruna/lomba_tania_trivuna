package org.vedruna.trivedruna.application.command.loginuser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.trivedruna.domain.exceptions.UserNotFoundException;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.AuthenticationManagerI;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class LoginUserHandlerTest {

  @Mock private UserJpaRepository userJpaRepository;
  @Mock private AuthenticationManagerI authenticationManager;
  @Mock private JWTProvider jwtProvider;

  @InjectMocks private LoginUserHandler handler;

  @Test
  void devuelveTokensCuandoElUsuarioExiste() {
    var user = new UserModel();
    user.setUsername("john");
    user.setPassword("pwd");
    var role = new org.vedruna.trivedruna.domain.model.RolModel();
    role.setScopes("scope");
    user.setRole(role);

    when(userJpaRepository.findByUserName("john")).thenReturn(Optional.of(user));
    when(jwtProvider.generateAccessToken(user)).thenReturn("access");
    when(jwtProvider.generateRefreshToken(user)).thenReturn("refresh");
    when(jwtProvider.getAccessTokenExpiresIn()).thenReturn(3600L);

    var response = handler.handle(new LoginUserRequest(user));

    verify(authenticationManager).authenticate("john", "pwd");
    assertThat(response.getAccessToken().getAccessToken()).isEqualTo("access");
    assertThat(response.getAccessToken().getRefreshToken()).isEqualTo("refresh");
    assertThat(response.getAccessToken().getExpiresIn()).isEqualTo(3600L);
  }

  @Test
  void lanzaExcepcionCuandoElUsuarioNoExiste() {
    var user = new UserModel();
    user.setUsername("ghost");
    when(userJpaRepository.findByUserName("ghost")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> handler.handle(new LoginUserRequest(user)))
        .isInstanceOf(UserNotFoundException.class);
  }
}
