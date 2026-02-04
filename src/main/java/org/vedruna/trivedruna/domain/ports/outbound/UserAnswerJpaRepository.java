package org.vedruna.trivedruna.domain.ports.outbound;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

public interface UserAnswerJpaRepository {

    UserAnswerQuestionModel save(String selectedAnswer, Integer usersUserId, Integer questionsQuestionId);
    Page<UserAnswerQuestionModel> findAll(Pageable pageable);
    Page<UserAnswerQuestionModel> findByUserId(Integer userId, Pageable pageable);
    boolean existsByUserAndQuestion(Integer userId, Integer questionId);
}
