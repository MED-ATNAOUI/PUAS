package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.EnrollmentResponse;

import com.pfe.learningplatform.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /*
     * =========================================
     * ENROLL STUDENT TO COURSE
     * =========================================
     */

    @PostMapping(
            "/student/{studentId}/course/{courseId}"
    )
    public ResponseEntity<EnrollmentResponse>
    enrollStudent(

            @PathVariable Long studentId,

            @PathVariable Long courseId
    ) {

        EnrollmentResponse response =
                enrollmentService.enrollStudent(
                        studentId,
                        courseId
                );

        return ResponseEntity.ok(response);
    }

    /*
     * =========================================
     * GET STUDENT ENROLLMENTS
     * =========================================
     */

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponse>>
    getStudentEnrollments(

            @PathVariable Long studentId
    ) {

        List<EnrollmentResponse> enrollments =
                enrollmentService
                        .getStudentEnrollments(studentId);

        return ResponseEntity.ok(enrollments);
    }

    /*
     * =========================================
     * GET COURSE STUDENTS
     * =========================================
     */

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponse>>
    getCourseStudents(

            @PathVariable Long courseId
    ) {

        List<EnrollmentResponse> students =
                enrollmentService
                        .getCourseStudents(courseId);

        return ResponseEntity.ok(students);
    }

    /*
     * =========================================
     * UNENROLL STUDENT
     * =========================================
     */

    @DeleteMapping(
            "/student/{studentId}/course/{courseId}"
    )
    public ResponseEntity<String> unenrollStudent(

            @PathVariable Long studentId,

            @PathVariable Long courseId
    ) {

        String result =
                enrollmentService.unenrollStudent(
                        studentId,
                        courseId
                );

        return ResponseEntity.ok(result);
    }
}
