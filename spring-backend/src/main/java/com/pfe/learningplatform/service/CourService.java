package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.CourseRequest;
import com.pfe.learningplatform.dto.CourseResponse;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Course;

import com.pfe.learningplatform.repository.CourseRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourService {

    private final CourseRepository courseRepository;

    public CourService(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }

    // 🔹 créer cours
    public CourseResponse createCourse(
            CourseRequest request
    ) {

        Course course = Course.builder()

                .title(request.getTitle())

                .description(request.getDescription())

                .build();

        Course savedCourse =
                courseRepository.save(course);

        return new CourseResponse(

                savedCourse.getId(),

                savedCourse.getTitle(),

                savedCourse.getDescription()
        );
    }

    // 🔹 récupérer tous les cours
    public List<CourseResponse> getAllCourses() {

        List<Course> courses =
                courseRepository.findAll();

        List<CourseResponse> responses =
                new ArrayList<>();

        for (Course course : courses) {

            responses.add(

                    new CourseResponse(

                            course.getId(),

                            course.getTitle(),

                            course.getDescription()
                    )
            );
        }

        return responses;
    }

    // 🔹 récupérer cours par id
    public CourseResponse getCourseById(Long id) {

        Course course =
                courseRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        return new CourseResponse(

                course.getId(),

                course.getTitle(),

                course.getDescription()
        );
    }

    // 🔹 mettre à jour un cours
    public CourseResponse updateCourse(
            Long id,
            CourseRequest request
    ) {

        Course course =
                courseRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        if (request.getTitle() != null
                && !request.getTitle().isEmpty()) {

            course.setTitle(request.getTitle());
        }

        if (request.getDescription() != null
                && !request.getDescription().isEmpty()) {

            course.setDescription(
                    request.getDescription()
            );
        }

        Course updatedCourse =
                courseRepository.save(course);

        return new CourseResponse(

                updatedCourse.getId(),

                updatedCourse.getTitle(),

                updatedCourse.getDescription()
        );
    }

    // 🔹 supprimer un cours
    public String deleteCourse(Long id) {

        Course course =
                courseRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        courseRepository.delete(course);

        return "Cours supprimé avec succès";
    }
}