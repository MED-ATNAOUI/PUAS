package com.pfe.learningplatform.dto;

import lombok.Data;

@Data
public class ExerciseRequest {

    private Long sectionId;

    private String difficulty;

    private String language = "français";
}