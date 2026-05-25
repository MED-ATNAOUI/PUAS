package com.pfe.learningplatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =========================================
     * QUIZ DIFFICULTY
     * beginner / intermediate / advanced
     * =========================================
     */

    private String difficulty;

    /*
     * =========================================
     * QUIZ CREATION DATE
     * =========================================
     */

    private LocalDateTime createdAt;

    /*
     * =========================================
     * SECTION RELATION
     * =========================================
     */

    @ManyToOne
    private Section section;

    /*
     * =========================================
     * QUESTIONS RELATION
     * =========================================
     */

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    private List<Question> questions;
}