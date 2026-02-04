package org.vedruna.trivedruna.domain.model;

import lombok.Data;

@Data
public class QuestionModel {

    Integer questionId;
    String questionText;
    String incorrectAnswer1;
    String incorrectAnswer2;
    String incorrectAnswer3;
    String correctAnswer;
    Boolean isApproved;
    Integer categoryId;
    Integer courseId;
    String categoryName;
    Integer userId;
    
}
