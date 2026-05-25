package com.pfe.learningplatform.repository;

import com.pfe.learningplatform.model.Video;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository
        extends JpaRepository<Video, Long> {

    /*
     * =====================================
     * FIND VIDEOS BY COURSE
     * =====================================
     */

    List<Video> findByCourseId(
            Long courseId
    );

    /*
     * =====================================
     * FIND VIDEOS BY SECTION
     * =====================================
     */

    List<Video> findBySectionId(
            Long sectionId
    );
}