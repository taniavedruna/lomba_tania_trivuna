package org.vedruna.trivedruna.application.command.refreshtoken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.domain.model.AccessToken;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.JWTProvider;
import org.vedruna.trivedruna.domain.ports.outbound.UserServiceI;

class RefreshTokenHandlerTest {

  @Mock private JWTProvider jwtProvider;
  @Mock private UserServiceI userService;
  @InjectMocks private RefreshTokenHandler handler;

  private AccessToken token;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    token = new AccessToken("access", 1L, "refresh", "scope");
  }

  @Test
  void renuevaTokenCuandoEsValido() {
    UserModel user = new UserModel();
    var rol = new org.vedruna.trivedruna.domain.model.RolModel();
    rol.setScopes("USER");
    user.setRole(rol);
    when(jwtProvider.getUsernameFromRefreshToken("refresh")).thenReturn("u");
    when(userService.loadUserByUsername("u")).thenReturn(user);
    when(jwtProvider.isRefreshTokenValid("refresh", user)).thenReturn(true);
    when(jwtProvider.generateAccessToken(user)).thenReturn("newAccess");
    when(jwtProvider.getAccessTokenExpiresIn()).thenReturn(123L);
    when(jwtProvider.generateRefreshToken(user)).thenReturn("newRefresh");

    var resp = handler.handle(new RefreshTokenRequest(token));

    assertThat(resp.getAccessToken().getAccessToken()).isEqualTo("newAccess");
    assertThat(resp.getAccessToken().getRefreshToken()).isEqualTo("newRefresh");
  }

  @Test
  void lanzaCuandoRefreshEsInvalido() {
    when(jwtProvider.getUsernameFromRefreshToken("refresh"))
        .thenThrow(new io.jsonwebtoken.JwtException("bad"));
    assertThatThrownBy(() -> handler.handle(new RefreshTokenRequest(token)))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
