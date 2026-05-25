package com.pfe.learningplatform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =========================================
     * STUDENT RELATION
     * =========================================
     */

    @ManyToOne
    private User student;

    /*
     * =========================================
     * SECTION RELATION
     * =========================================
     */

    @ManyToOne
    private Section section;

    /*
     * =========================================
     * CURRENT LEVEL
     * =========================================
     */

    private int currentLevel;

    /*
     * =========================================
     * NUMBER OF SUCCESSFUL QUIZZES
     * =========================================
     */

    private int completedQuizzes;

    /*
     * =========================================
     * STUDENT AVERAGE SCORE
     * =========================================
     */

    private double averageScore;
}