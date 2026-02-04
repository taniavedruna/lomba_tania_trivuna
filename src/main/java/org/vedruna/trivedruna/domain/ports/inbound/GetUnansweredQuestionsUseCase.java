package org.vedruna.trivedruna.domain.ports.inbound;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.domain.model.QuestionModel;

public interface GetUnansweredQuestionsUseCase {

    Page<QuestionModel> findUnansweredByCategory(Integer userId, Integer categoryId, Pageable pageable);

}
