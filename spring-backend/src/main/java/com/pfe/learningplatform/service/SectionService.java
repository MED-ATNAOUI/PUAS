package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.SectionRequest;
import com.pfe.learningplatform.dto.SectionResponse;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Course;
import com.pfe.learningplatform.model.Section;

import com.pfe.learningplatform.repository.CourseRepository;
import com.pfe.learningplatform.repository.SectionRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    private final CourseRepository courseRepository;

    public SectionService(
            SectionRepository sectionRepository,
            CourseRepository courseRepository
    ) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
    }

    // créer section
    public SectionResponse createSection(
            SectionRequest request
    ) {

        Course course = courseRepository.findById(
                request.getCourseId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cours non trouvé"
                )
        );

        Section section = Section.builder()

                .title(request.getTitle())

                .content(request.getContent())

                .videoUrl(request.getVideoUrl())

                .summary(request.getSummary())

                .course(course)

                .build();

        Section savedSection =
                sectionRepository.save(section);

        return convertToDTO(savedSection);
    }

    // récupérer toutes sections
    public List<SectionResponse> getAllSections() {

        List<Section> sections =
                sectionRepository.findAll();

        List<SectionResponse> responses =
                new ArrayList<>();

        for (Section section : sections) {

            responses.add(convertToDTO(section));
        }

        return responses;
    }

    // récupérer section par id
    public SectionResponse getSectionById(
            Long id
    ) {

        Section section =
                sectionRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section non trouvée"
                                ));

        return convertToDTO(section);
    }

    // récupérer sections par cours
    public List<SectionResponse> getSectionsByCourse(
            Long courseId
    ) {

        List<Section> sections =
                sectionRepository
                        .findByCourse_Id(courseId);

        List<SectionResponse> responses =
                new ArrayList<>();

        for (Section section : sections) {

            responses.add(convertToDTO(section));
        }

        return responses;
    }

    /*
     * =========================================
     * UPDATE SECTION
     * =========================================
     */

    public SectionResponse updateSection(
            Long id,
            SectionRequest request
    ) {

        Section section =
                sectionRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section non trouvée"
                                ));

        if (request.getTitle() != null
                && !request.getTitle().isEmpty()) {

            section.setTitle(request.getTitle());
        }

        if (request.getContent() != null
                && !request.getContent().isEmpty()) {

            section.setContent(request.getContent());
        }

        if (request.getVideoUrl() != null
                && !request.getVideoUrl().isEmpty()) {

            section.setVideoUrl(request.getVideoUrl());
        }

        if (request.getSummary() != null
                && !request.getSummary().isEmpty()) {

            section.setSummary(request.getSummary());
        }

        Section updatedSection =
                sectionRepository.save(section);

        return convertToDTO(updatedSection);
    }

    /*
     * =========================================
     * DELETE SECTION
     * =========================================
     */

    public String deleteSection(Long id) {

        Section section =
                sectionRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section non trouvée"
                                ));

        sectionRepository.delete(section);

        return "Section supprimée avec succès";
    }

    // conversion Entity → DTO
    private SectionResponse convertToDTO(
            Section section
    ) {

        return SectionResponse.builder()

                .id(section.getId())

                .title(section.getTitle())

                .content(section.getContent())

                .videoUrl(section.getVideoUrl())

                .summary(section.getSummary())

                .courseId(
                        section.getCourse().getId()
                )

                .courseTitle(
                        section.getCourse().getTitle()
                )

                .build();
    }
}