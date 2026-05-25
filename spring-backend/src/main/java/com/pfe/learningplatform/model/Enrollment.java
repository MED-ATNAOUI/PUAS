package com.pfe.learningplatform.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "course_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =========================================
     * STUDENT RELATION
     * =========================================
     */

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    /*
     * =========================================
     * COURSE RELATION
     * =========================================
     */

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    /*
     * =========================================
     * ENROLLMENT DATE
     * =========================================
     */

    private LocalDateTime enrolledAt;
}
