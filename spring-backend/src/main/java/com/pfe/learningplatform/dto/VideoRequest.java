package com.pfe.learningplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    private String description;

    @NotBlank(message = "L'URL YouTube est obligatoire")
    private String youtubeUrl;

    private String thumbnailUrl;

    @NotNull(message = "L'ID du cours est obligatoire")
    private Long courseId;

    @NotNull(message = "L'ID de la section est obligatoire")
    private Long sectionId;
}