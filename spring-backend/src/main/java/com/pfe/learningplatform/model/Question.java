package com.pfe.learningplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =========================================
     * QUESTION TEXT
     * =========================================
     */

    @Column(length = 1000)
    private String question;

    /*
     * =========================================
     * QCM OPTIONS
     * =========================================
     */

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    /*
     * =========================================
     * CORRECT ANSWER
     * =========================================
     */

    private String correctAnswer;

    /*
     * =========================================
     * QUIZ RELATION
     * =========================================
     */

    @JsonIgnore
    @ManyToOne

    @JoinColumn(name = "quiz_id")

    private Quiz quiz;
}