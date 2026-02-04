package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.command.loginuser.LoginUserRequest;
import org.vedruna.trivedruna.application.command.registeruser.RegisterUserRequest;
import org.vedruna.trivedruna.application.command.refreshtoken.RefreshTokenRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.AuthController;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.LoginRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RefreshRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.in.RegisterRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.AuthResponseDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;

/**
 * Implementación del controlador REST para la autenticación de usuarios.
 *
 * <p>{@code @Slf4j}: Habilita logs. {@code @AllArgsConstructor}: Genera constructor para inyección
 * de dependencias. {@code @CrossOrigin}: Permite peticiones CORS. {@code @RestController}: Define
 * esta clase como controlador REST de Spring.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class AuthControllerImpl implements AuthController {

  /** Conversor para transformar entre DTOs de entrada/salida y modelos de dominio. */
  private final InboundConverter inboundConverter;

  /** Mediador para despachar las solicitudes a sus manejadores correspondientes. */
  private final Mediator mediator;

  /**
   * {@inheritDoc}
   *
   * <p>Procesa el registro convirtiendo el DTO a modelo y despachando la solicitud al mediador.
   */
  @Override
  public ResponseEntity<UserRegisteredDTO> register(@Valid RegisterRequestDTO request) {
    UserRegisteredDTO response =
        inboundConverter.toUserRegisteredDTO(
            mediator
                .dispatch(
                    new RegisterUserRequest(
                        inboundConverter.registerToUserModel(request),
                        request.getCourseName()))
                .getUser());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }




  /**
   * {@inheritDoc}
   *
   * <p>Gestiona el inicio de sesión despachando la solicitud de login al mediador.
   */
  @Override
  public ResponseEntity<AuthResponseDTO> login(@Valid LoginRequestDTO request) {
    return ResponseEntity.ok(
        inboundConverter.toAuthResponseDTO(
            mediator
                .dispatch(new LoginUserRequest(inboundConverter.loginToUserModel(request)))
                .getAccessToken()));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Retorna la información del usuario principal autenticado.
   */
  @Override
  public ResponseEntity<UserDTO> me(UserDTO userLogueado) {
    return ResponseEntity.ok(userLogueado);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Solicita la renovación del token de acceso a través del mediador.
   */
  @Override
  public ResponseEntity<AuthResponseDTO> refreshToken(@Valid RefreshRequestDTO request) {
    return ResponseEntity.ok(
        inboundConverter.toAuthResponseDTO(
            mediator
                .dispatch(new RefreshTokenRequest(inboundConverter.toAccessToken(request)))
                .getAccessToken()));
  }
}
