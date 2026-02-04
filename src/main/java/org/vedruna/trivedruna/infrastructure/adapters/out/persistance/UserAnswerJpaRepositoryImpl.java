package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.UserAnswerJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.UserAnswerQuestionRepository;

@Service
@AllArgsConstructor
public class UserAnswerJpaRepositoryImpl implements UserAnswerJpaRepository {

  private final UserAnswerQuestionRepository userAnswerQuestionRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public UserAnswerQuestionModel save(
      String selectedAnswer, Integer usersUserId, Integer questionsQuestionId) {
    UserAnswerQuestionModel model = new UserAnswerQuestionModel();
    model.setUsersUserId(usersUserId);
    model.setQuestionsQuestionId(questionsQuestionId);
    model.setSelectedAnswer(selectedAnswer);
    model.setCreateDate(LocalDateTime.now());

    return outboundConverter.toUserAnswerQuestionModel(
        userAnswerQuestionRepository.save(outboundConverter.toUserAnswerQuestionEntity(model)));
  }

  @Override
  public Page<UserAnswerQuestionModel> findAll(Pageable pageable) {
    return userAnswerQuestionRepository.findAll(pageable)
        .map(outboundConverter::toUserAnswerQuestionModel);
  }

  @Override
  public Page<UserAnswerQuestionModel> findByUserId(Integer userId, Pageable pageable) {
    return userAnswerQuestionRepository
        .findByIdUsersUserIdOrderByCreateDateDesc(userId, pageable)
        .map(outboundConverter::toUserAnswerQuestionModel);
  }

  @Override
  public boolean existsByUserAndQuestion(Integer userId, Integer questionId) {
    return userAnswerQuestionRepository.existsByIdUsersUserIdAndIdQuestionsQuestionId(userId, questionId);
  }
}
