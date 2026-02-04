package org.vedruna.trivedruna.infrastructure.adapters.out.persistance;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vedruna.trivedruna.domain.model.QuestionModel;
import org.vedruna.trivedruna.domain.ports.outbound.QuestionsJpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.converter.OutboundConverter;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.QuestionEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository.QuestionRepository;

@Service
@AllArgsConstructor
public class QuestionsJpaRepositoryImpl implements QuestionsJpaRepository {

  private final QuestionRepository questionRepository;
  private final OutboundConverter outboundConverter;

  @Override
  public List<QuestionModel> saveAll(List<QuestionModel> questionModels) {
    List<QuestionEntity> entities =
        questionModels.stream().map(outboundConverter::toQuestionEntity).toList();
    return questionRepository.saveAll(entities).stream()
        .map(outboundConverter::toQuestionModel)
        .toList();
  }

  @Override
  public Page<QuestionModel> findAll(Pageable pageable) {
    return questionRepository.findAll(pageable).map(outboundConverter::toQuestionModel);
  }

  @Override
  public Optional<Page<QuestionModel>> findByCategoryId(Integer categoryId, Pageable pageable) {
    return Optional.of(
        questionRepository
            .findByCategoryCategoryId(categoryId, pageable)
            .map(outboundConverter::toQuestionModel));
  }

  @Override
  public Optional<Page<QuestionModel>> findUnansweredByCategory(
      Integer userId, Integer categoryId, Pageable pageable) {
    return Optional.of(
        questionRepository
            .findUnansweredByCategory(userId, categoryId, pageable)
            .map(outboundConverter::toQuestionModel));
  }

  @Override
  public Optional<QuestionModel> findById(Integer questionId) {
    return questionRepository.findById(questionId).map(outboundConverter::toQuestionModel);
  }
}
