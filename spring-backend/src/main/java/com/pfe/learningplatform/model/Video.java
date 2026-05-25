package com.pfe.learningplatform.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "videos")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    /*
     * =====================================
     * VIDEO ID
     * =====================================
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =====================================
     * VIDEO TITLE
     * =====================================
     */

    private String title;

    /*
     * =====================================
     * VIDEO DESCRIPTION
     * =====================================
     */

    @Column(columnDefinition = "TEXT")
    private String description;

    /*
     * =====================================
     * YOUTUBE URL
     * =====================================
     */

    private String youtubeUrl;

    /*
     * =====================================
     * THUMBNAIL URL
     * =====================================
     */

    private String thumbnailUrl;

    /*
     * =====================================
     * COURSE ID
     * =====================================
     */

    private Long courseId;

    /*
     * =====================================
     * SECTION ID
     * =====================================
     */

    private Long sectionId;
}