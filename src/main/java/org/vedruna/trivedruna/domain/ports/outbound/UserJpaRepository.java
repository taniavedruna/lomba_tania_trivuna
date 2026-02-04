package org.vedruna.trivedruna.domain.ports.outbound;

import java.util.Optional;

import org.vedruna.trivedruna.domain.model.UserModel;

public interface UserJpaRepository {

    UserModel updateUser(Integer userId, UserModel userModel);
    Optional<UserModel> findByUserName(String username);
    Optional<UserModel> getUserById(Integer userId);
    void incrementScore(Integer userId, Integer score);
    UserModel saveUser(UserModel userModel);

}
