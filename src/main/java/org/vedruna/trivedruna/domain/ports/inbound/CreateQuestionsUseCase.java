package org.vedruna.trivedruna.domain.ports.inbound;

import java.util.List;

import org.vedruna.trivedruna.domain.model.QuestionModel;

public interface CreateQuestionsUseCase {
    List<QuestionModel> saveAll(List<QuestionModel> questionModels);

}
