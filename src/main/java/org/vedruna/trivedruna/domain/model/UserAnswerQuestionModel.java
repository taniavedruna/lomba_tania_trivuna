package org.vedruna.trivedruna.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserAnswerQuestionModel {

    Integer usersUserId;
    Integer questionsQuestionId;
    LocalDateTime createDate;
    String selectedAnswer;
    
}
