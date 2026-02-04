package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserAnswerQuestionEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserAnswerQuestionId;

public interface UserAnswerQuestionRepository
        extends JpaRepository<UserAnswerQuestionEntity, UserAnswerQuestionId> {

    Optional<UserAnswerQuestionEntity> findByIdUsersUserIdAndIdQuestionsQuestionId(
        Integer usersUserId,
        Integer questionsQuestionId
    );

    Page<UserAnswerQuestionEntity> findByIdUsersUserIdOrderByCreateDateDesc(Integer usersUserId, Pageable pageable);

    boolean existsByIdUsersUserIdAndIdQuestionsQuestionId(Integer usersUserId, Integer questionsQuestionId);
}
