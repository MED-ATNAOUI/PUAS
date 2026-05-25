package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.AdminResponse;
import com.pfe.learningplatform.dto.DashboardStatsResponse;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.User;

import com.pfe.learningplatform.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final SectionRepository sectionRepository;

    private final VideoRepository videoRepository;

    private final QuizRepository quizRepository;

    private final EnrollmentRepository enrollmentRepository;

    /*
     * =========================================
     * GET ALL USERS
     * =========================================
     */

    public List<AdminResponse> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<AdminResponse> responses =
                new ArrayList<>();

        for (User user : users) {

            responses.add(convertToDTO(user));
        }

        return responses;
    }

    /*
     * =========================================
     * GET USER BY ID
     * =========================================
     */

    public AdminResponse getUserById(Long id) {

        User user =
                userRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Utilisateur non trouvé"
                                ));

        return convertToDTO(user);
    }

    /*
     * =========================================
     * GET DASHBOARD STATS
     * =========================================
     */

    public DashboardStatsResponse getDashboardStats() {

        return DashboardStatsResponse.builder()

                .totalUsers(
                        userRepository.count()
                )

                .totalCourses(
                        courseRepository.count()
                )

                .totalSections(
                        sectionRepository.count()
                )

                .totalVideos(
                        videoRepository.count()
                )

                .totalQuizzes(
                        quizRepository.count()
                )

                .totalEnrollments(
                        enrollmentRepository.count()
                )

                .build();
    }

    /*
     * =========================================
     * CONVERT USER TO DTO
     * =========================================
     */

    private AdminResponse convertToDTO(User user) {

        return AdminResponse.builder()

                .id(user.getId())

                .nom(user.getNom())

                .prenom(user.getPrenom())

                .email(user.getEmail())

                .level(user.getLevel())

                .role(user.getRole().name())

                .build();
    }
}
