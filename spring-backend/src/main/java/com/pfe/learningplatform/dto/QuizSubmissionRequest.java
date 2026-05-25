package com.pfe.learningplatform.dto;

import lombok.Data;

import java.util.Map;

@Data
public class QuizSubmissionRequest {

    /*
     * =========================================
     * QUIZ ID
     * =========================================
     */

    private Long quizId;

    /*
     * =========================================
     * STUDENT ID
     * =========================================
     */

    private Long studentId;

    /*
     * =========================================
     * STUDENT ANSWERS
     *
     * questionId -> selected answer
     * =========================================
     */

    private Map<Long, String> answers;
}