package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_answers_questions")
public class UserAnswerQuestionEntity {

    @EmbeddedId
    UserAnswerQuestionId id;


    @Column(name = "create_date", nullable = false)
    LocalDateTime createDate;

    @Column(name = "selected_answer", length = 45)
    String selectedAnswer;

    @ManyToOne
    @MapsId("usersUserId")
    @JoinColumn(name = "users_user_id", nullable = false)
    UserEntity user;

    @ManyToOne
    @MapsId("questionsQuestionId")
    @JoinColumn(name = "questions_question_id", nullable = false)
    QuestionEntity question;
}

