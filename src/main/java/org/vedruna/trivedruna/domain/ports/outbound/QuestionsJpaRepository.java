package org.vedruna.trivedruna.domain.ports.outbound;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.domain.model.QuestionModel;

public interface QuestionsJpaRepository {

    List<QuestionModel> saveAll(List<QuestionModel> questionModels);
    Page<QuestionModel> findAll(Pageable pageable);
    Optional<Page<QuestionModel>> findByCategoryId(Integer categoryId, Pageable pageable);
    Optional<Page<QuestionModel>> findUnansweredByCategory(Integer userId, Integer categoryId, Pageable pageable);
    Optional<QuestionModel> findById(Integer questionId);
}
