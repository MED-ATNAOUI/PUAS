package com.pfe.learningplatform.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse {

    private String title;

    private String statement;

    private String solution;

    private String difficulty;

    private String sectionTitle;

    private String courseTitle;
}