package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.EnrollmentResponse;

import com.pfe.learningplatform.exception.DuplicateResourceException;
import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Course;
import com.pfe.learningplatform.model.Enrollment;
import com.pfe.learningplatform.model.User;

import com.pfe.learningplatform.repository.CourseRepository;
import com.pfe.learningplatform.repository.EnrollmentRepository;
import com.pfe.learningplatform.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    /*
     * =========================================
     * ENROLL STUDENT
     * =========================================
     */

    public EnrollmentResponse enrollStudent(
            Long studentId,
            Long courseId
    ) {

        User student =
                userRepository.findById(studentId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Étudiant non trouvé"
                                ));

        Course course =
                courseRepository.findById(courseId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        // vérifier si déjà inscrit
        if (enrollmentRepository
                .findByStudentAndCourse(student, course)
                .isPresent()) {

            throw new DuplicateResourceException(
                    "L'étudiant est déjà inscrit à ce cours"
            );
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .build();

        Enrollment saved =
                enrollmentRepository.save(enrollment);

        return convertToDTO(saved);
    }

    /*
     * =========================================
     * GET STUDENT ENROLLMENTS
     * =========================================
     */

    public List<EnrollmentResponse> getStudentEnrollments(
            Long studentId
    ) {

        User student =
                userRepository.findById(studentId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Étudiant non trouvé"
                                ));

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudent(student);

        List<EnrollmentResponse> responses =
                new ArrayList<>();

        for (Enrollment enrollment : enrollments) {

            responses.add(convertToDTO(enrollment));
        }

        return responses;
    }

    /*
     * =========================================
     * GET COURSE STUDENTS
     * =========================================
     */

    public List<EnrollmentResponse> getCourseStudents(
            Long courseId
    ) {

        Course course =
                courseRepository.findById(courseId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        List<Enrollment> enrollments =
                enrollmentRepository.findByCourse(course);

        List<EnrollmentResponse> responses =
                new ArrayList<>();

        for (Enrollment enrollment : enrollments) {

            responses.add(convertToDTO(enrollment));
        }

        return responses;
    }

    /*
     * =========================================
     * UNENROLL STUDENT
     * =========================================
     */

    public String unenrollStudent(
            Long studentId,
            Long courseId
    ) {

        User student =
                userRepository.findById(studentId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Étudiant non trouvé"
                                ));

        Course course =
                courseRepository.findById(courseId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cours non trouvé"
                                ));

        Enrollment enrollment =
                enrollmentRepository
                        .findByStudentAndCourse(student, course)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inscription non trouvée"
                                ));

        enrollmentRepository.delete(enrollment);

        return "Désinscription effectuée avec succès";
    }

    /*
     * =========================================
     * CONVERT TO DTO
     * =========================================
     */

    private EnrollmentResponse convertToDTO(
            Enrollment enrollment
    ) {

        return EnrollmentResponse.builder()

                .id(enrollment.getId())

                .courseId(
                        enrollment.getCourse().getId()
                )

                .courseTitle(
                        enrollment.getCourse().getTitle()
                )

                .courseDescription(
                        enrollment.getCourse()
                                .getDescription()
                )

                .studentId(
                        enrollment.getStudent().getId()
                )

                .studentName(
                        enrollment.getStudent().getPrenom()
                                + " "
                                + enrollment.getStudent().getNom()
                )

                .enrolledAt(
                        enrollment.getEnrolledAt()
                )

                .build();
    }
}
