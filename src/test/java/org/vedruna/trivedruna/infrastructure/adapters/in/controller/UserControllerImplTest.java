package org.vedruna.trivedruna.infrastructure.adapters.in.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vedruna.trivedruna.application.command.update.user.UpdateUserRequest;
import org.vedruna.trivedruna.application.command.update.user.UpdateUserResponse;
import org.vedruna.trivedruna.application.cqrs.Mediator;
import org.vedruna.trivedruna.application.query.getbyid.user.GetUserByIdRequest;
import org.vedruna.trivedruna.application.query.getbyid.user.GetUserByIdResponse;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.infrastructure.adapters.in.controller.impl.UserControllerImpl;
import org.vedruna.trivedruna.infrastructure.adapters.in.converters.InboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.input.UpdateUserRequestDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.UserDTO;
import org.vedruna.trivedruna.infrastructure.adapters.in.dto.security.out.UserRegisteredDTO;

class UserControllerImplTest {

  @Mock private Mediator mediator;
  @Mock private InboundConverter inboundConverter;
  @InjectMocks private UserControllerImpl controller;

  private UserDTO user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new UserDTO();
    user.setUserId(1);
  }

  @Test
  void updateMeDevuelveUsuarioActualizado() {
    UpdateUserRequestDTO reqDto = new UpdateUserRequestDTO();
    UserModel model = new UserModel();
    when(inboundConverter.toUserModel(reqDto)).thenReturn(model);
    when(mediator.dispatch(any(UpdateUserRequest.class)))
        .thenReturn(new UpdateUserResponse(model));
    UserRegisteredDTO dto = new UserRegisteredDTO();
    when(inboundConverter.toUserRegisteredDTO(model)).thenReturn(dto);

    var response = controller.updateMe(user, reqDto);
    assertThat(response.getBody()).isSameAs(dto);
  }

  @Test
  void getUserByIdDevuelveUsuario() {
    UserModel model = new UserModel();
    when(mediator.dispatch(any(GetUserByIdRequest.class)))
        .thenReturn(new GetUserByIdResponse(model));
    UserDTO dto = new UserDTO();
    when(inboundConverter.toUserDTO(model)).thenReturn(dto);

    var response = controller.getUserById(2);
    assertThat(response.getBody()).isSameAs(dto);
  }
}
