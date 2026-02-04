package org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getbyid.user.GetUserByIdRequest;
import org.vedruna.trivedruna.domain.ports.inbound.GetUserByIdUseCase;
import org.vedruna.trivedruna.application.command.update.user.UpdateUserRequest;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.UserController;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.UpdateUserRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;

/**
 * Implementacion del controlador REST para gestion de usuarios.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class UserControllerImpl implements UserController {

  private final Mediator mediator;
  private final InboundConverter inboundConverter;

  @Override
  public ResponseEntity<UserRegisteredDTO> updateMe(
    UserDTO userLogueado, @Valid UpdateUserRequestDTO request) {
    log.info("Actualizar usuario");
        return ResponseEntity.ok(
        inboundConverter.toUserRegisteredDTO(
            mediator
                .dispatch(
                    new UpdateUserRequest(
                        userLogueado.getUserId(),
                        inboundConverter.toUserModel(request)))
                .getUserModel()));
  }

  @Override
  public ResponseEntity<UserDTO> getUserById(Integer id) {
    log.info("Buscar usuario por id");
    return ResponseEntity.ok().body(
        inboundConverter.toUserDTO(
            mediator.dispatch(new GetUserByIdRequest(id))
        .getUserModel()));
  }

  
}
