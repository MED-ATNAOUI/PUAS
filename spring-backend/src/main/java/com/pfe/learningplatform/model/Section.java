package com.pfe.learningplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // titre section
    private String title;

    // contenu texte
    @Column(length = 5000)
    private String content;

    // vidéo
    private String videoUrl;

    // résumé
    @Column(length = 3000)
    private String summary;

    // relation avec cours
    @JsonIgnore
    @ManyToOne

    @JoinColumn(name = "course_id")

    private Course course;
}