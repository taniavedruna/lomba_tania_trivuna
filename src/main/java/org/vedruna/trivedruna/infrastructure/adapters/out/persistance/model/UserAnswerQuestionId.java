package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class UserAnswerQuestionId implements Serializable {

    private static final long serialVersionUID=1L;

    @Column(name = "users_user_id")
     Integer usersUserId;

    @Column(name = "questions_question_id")
     Integer questionsQuestionId;
}

