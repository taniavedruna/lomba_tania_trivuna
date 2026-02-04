package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.QuestionEntity;
import org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model.UserAnswerQuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    Page<QuestionEntity> findByCategoryCategoryId(Integer categoryId, Pageable pageable);

    @Query("""
        select q from QuestionEntity q
        where q.category.categoryId = :categoryId
          and q.questionId not in (
            select ua.question.questionId from UserAnswerQuestionEntity ua
            where ua.user.userId = :userId
          )
        """)
    Page<QuestionEntity> findUnansweredByCategory(
        @Param("userId") Integer userId,
        @Param("categoryId") Integer categoryId,
        Pageable pageable
    );
}
