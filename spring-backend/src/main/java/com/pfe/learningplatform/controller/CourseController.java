package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.CourseRequest;
import com.pfe.learningplatform.dto.CourseResponse;

import com.pfe.learningplatform.service.CourService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourService courseService;

    public CourseController(
            CourService courseService
    ) {

        this.courseService = courseService;
    }

    /*
     * =========================================
     * CREATE COURSE
     * ADMIN ONLY
     * =========================================
     */

    @PostMapping

    @PreAuthorize("hasRole('ADMIN')")

    public CourseResponse createCourse(

            @Valid
            @RequestBody
            CourseRequest request
    ) {

        return courseService.createCourse(
                request
        );
    }

    /*
     * =========================================
     * GET ALL COURSES
     * USER + ADMIN
     * =========================================
     */

    @GetMapping
    public List<CourseResponse> getAllCourses() {

        return courseService.getAllCourses();
    }

    /*
     * =========================================
     * GET COURSE BY ID
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/{id}")
    public CourseResponse getCourseById(

            @PathVariable Long id
    ) {

        return courseService.getCourseById(id);
    }

    /*
     * =========================================
     * UPDATE COURSE
     * ADMIN ONLY
     * =========================================
     */

    @PutMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public CourseResponse updateCourse(

            @PathVariable Long id,

            @Valid
            @RequestBody
            CourseRequest request
    ) {

        return courseService.updateCourse(
                id,
                request
        );
    }

    /*
     * =========================================
     * DELETE COURSE
     * ADMIN ONLY
     * =========================================
     */

    @DeleteMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public String deleteCourse(

            @PathVariable Long id
    ) {

        return courseService.deleteCourse(id);
    }
}