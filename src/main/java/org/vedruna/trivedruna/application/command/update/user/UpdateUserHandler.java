package org.vedruna.trivedruna.application.command.update.user;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.application.cqrs.RequestHandler;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.PasswordEncoderI;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class UpdateUserHandler implements RequestHandler<UpdateUserRequest, UpdateUserResponse> {
    
    UserJpaRepository userJpaRepository;
    PasswordEncoderI passwordEncoder;
    @Override
    public UpdateUserResponse handle(UpdateUserRequest inputRequest) {
       log.info("Actualizar usuario");
       Integer userId = inputRequest.getUserId();
       UserModel current =
           userJpaRepository
               .getUserById(userId)
               .orElseThrow(() -> new IllegalArgumentException("User not found"));
       merge(current, inputRequest.getUserModel());
       return new UpdateUserResponse(userJpaRepository.updateUser(userId, current));
    }

    @Override
    public Class<UpdateUserRequest> getRequestType() {
        return UpdateUserRequest.class;
    }

    private void merge(UserModel current, UserModel updates) {
        if (updates.getUsername() != null) {
            current.setUsername(updates.getUsername());
        }
        if (updates.getEmail() != null) {
            current.setEmail(updates.getEmail());
        }
        if (updates.getPassword() != null) {
            current.setPassword(passwordEncoder.encode(updates.getPassword()));
        }
        if (updates.getAvatarUrl() != null) {
            current.setAvatarUrl(updates.getAvatarUrl());
        }
        if (updates.getName() != null) {
            current.setName(updates.getName());
        }
        if (updates.getSurname1() != null) {
            current.setSurname1(updates.getSurname1());
        }
        if (updates.getSurname2() != null) {
            current.setSurname2(updates.getSurname2());
        }
    }
}
