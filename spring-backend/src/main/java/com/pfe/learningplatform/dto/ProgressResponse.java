package com.pfe.learningplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressResponse {

    /*
     * =========================================
     * COURSE NAME
     * =========================================
     */

    private String courseName;

    /*
     * =========================================
     * SECTION NAME
     * =========================================
     */

    private String sectionName;

    /*
     * =========================================
     * CURRENT LEVEL
     * =========================================
     */

    private int currentLevel;

    /*
     * =========================================
     * COMPLETED QUIZZES
     * =========================================
     */

    private int completedQuizzes;

    /*
     * =========================================
     * AVERAGE SCORE
     * =========================================
     */

    private double averageScore;

    /*
     * =========================================
     * RESULT MESSAGE
     * =========================================
     */

    private String message;
}