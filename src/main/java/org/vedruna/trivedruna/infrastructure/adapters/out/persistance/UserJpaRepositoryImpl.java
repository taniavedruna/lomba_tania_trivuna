package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.UserModel;
import org.vedruna.trivedruna.domain.ports.outbound.UserJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserJpaRepositoryImpl implements UserJpaRepository {

  private final UserRepository userRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public UserModel updateUser(Integer userId, UserModel userModel) {
    userModel.setUserId(userId);
    return outboundConverter.toUserModel(userRepository.save(outboundConverter.toUserEntity(userModel)));
  }

  @Override
  public Optional<UserModel> findByUserName(String username) {
    return userRepository.findByUsername(username).map(outboundConverter::toUserModel);
  }

  @Override
  public Optional<UserModel> getUserById(Integer userId) {
    return userRepository.findById(userId).map(outboundConverter::toUserModel);
  }

  @Override
  public void incrementScore(Integer userId, Integer score) {
    userRepository.incrementScore(userId, score);
  }

  @Override
  public UserModel saveUser(UserModel userModel) {
    return outboundConverter.toUserModel(userRepository.save(outboundConverter.toUserEntity(userModel)));
  }
}
