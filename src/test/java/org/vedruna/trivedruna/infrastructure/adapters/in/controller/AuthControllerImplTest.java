package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.vedruna.trivedruna.application.command.loginuser.LoginUserRequest;
import org.vedruna.trivedruna.application.command.loginuser.LoginUserResponse;
import org.vedruna.trivedruna.application.command.refreshtoken.RefreshTokenRequest;
import org.vedruna.trivedruna.application.command.refreshtoken.RefreshTokenResponse;
import org.vedruna.trivedruna.application.command.registeruser.RegisterUserRequest;
import org.vedruna.trivedruna.application.command.registeruser.RegisterUserResponse;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.domain.model.AccessToken;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl.AuthControllerImpl;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.LoginRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RefreshRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RegisterRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.AuthResponseDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;

class AuthControllerImplTest {

  @Mock private Mediator mediator;
  @Mock private InboundConverter inboundConverter;
  @InjectMocks private AuthControllerImpl controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void registerDevuelve201() {
    RegisterRequestDTO req = new RegisterRequestDTO();
    UserModel model = new UserModel();
    when(inboundConverter.registerToUserModel(req)).thenReturn(model);
    when(mediator.dispatch(any(RegisterUserRequest.class)))
        .thenReturn(new RegisterUserResponse(model));
    UserRegisteredDTO dto = new UserRegisteredDTO();
    when(inboundConverter.toUserRegisteredDTO(model)).thenReturn(dto);

    var response = controller.register(req);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isSameAs(dto);
  }

  @Test
  void loginDevuelveToken() {
    LoginRequestDTO req = new LoginRequestDTO();
    UserModel model = new UserModel();
    AccessToken token = new AccessToken("a", 1L, "r", "scope");
    when(inboundConverter.loginToUserModel(req)).thenReturn(model);
    when(mediator.dispatch(any(LoginUserRequest.class))).thenReturn(new LoginUserResponse(token));
    AuthResponseDTO dto = new AuthResponseDTO();
    when(inboundConverter.toAuthResponseDTO(token)).thenReturn(dto);

    var response = controller.login(req);
    assertThat(response.getBody()).isSameAs(dto);
  }

  @Test
  void meDevuelveUsuario() {
    UserDTO user = new UserDTO();
    var response = controller.me(user);
    assertThat(response.getBody()).isSameAs(user);
  }

  @Test
  void refreshDevuelveNuevoToken() {
    RefreshRequestDTO req = new RefreshRequestDTO();
    AccessToken token = new AccessToken("a", 1L, "r", "scope");
    when(inboundConverter.toAccessToken(req)).thenReturn(token);
    when(mediator.dispatch(any(RefreshTokenRequest.class)))
        .thenReturn(new RefreshTokenResponse(token));
    AuthResponseDTO dto = new AuthResponseDTO();
    when(inboundConverter.toAuthResponseDTO(token)).thenReturn(dto);

    var response = controller.refreshToken(req);
    assertThat(response.getBody()).isSameAs(dto);
  }
}
