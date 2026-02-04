package org.vedruna.trivedruna.infrastructure.adapters.out;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.UserServiceI;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserEntity;

/** Implementacion del servicio de usuario para integrarse con Spring Security. */
@Component
@AllArgsConstructor
public class UserServiceImpl implements UserServiceI {

  private final UserDetailsService userDetailsService;
  private final OutboundConverter outboundConverter;

  @Override
  public UserModel loadUserByUsername(String username) {
    return outboundConverter.toUserModel(
        (UserEntity) userDetailsService.loadUserByUsername(username));
  }
}
