package org.vedruna.trivedruna.infrastructure.adapters.out.persistance.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    Integer questionId;

    @Lob
    @Column(name = "question_text", nullable = false)
    String questionText;

    @Column(name = "incorrect_answer1", nullable = false)
    String incorrectAnswer1;

    @Column(name = "incorrect_answer2", nullable = false)
    String incorrectAnswer2;

    @Column(name = "incorrect_answer3", nullable = false)
    String incorrectAnswer3;

    @Column(name = "respuesta_correcta", nullable = false)
    String respuestaCorrecta;

    @Column(name = "is_approved", nullable = false)
    Boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_category_id", nullable = false)
    CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_user_id", nullable = false)
    UserEntity user;
}

