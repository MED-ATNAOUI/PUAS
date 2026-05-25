package com.pfe.learningplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SectionRequest {

    // titre section
    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    // contenu texte
    private String content;

    // lien vidéo
    private String videoUrl;

    // résumé
    private String summary;

    // id du cours parent
    @NotNull(message = "L'ID du cours est obligatoire")
    private Long courseId;
}