package org.vedruna.trivedruna.domain.ports.inbound;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.trivedruna.domain.model.UserAnswerQuestionModel;

public interface GetAllAnwersUseCase {

    Page<UserAnswerQuestionModel> findAll(Pageable pageable);

}
