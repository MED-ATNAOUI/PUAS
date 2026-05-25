package com.pfe.learningplatform.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CourseRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    private String description;
}