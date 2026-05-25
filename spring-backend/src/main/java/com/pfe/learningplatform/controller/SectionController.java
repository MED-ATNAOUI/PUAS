package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.SectionRequest;
import com.pfe.learningplatform.dto.SectionResponse;

import com.pfe.learningplatform.service.SectionService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
@CrossOrigin(origins = "*")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(
            SectionService sectionService
    ) {

        this.sectionService = sectionService;
    }

    /*
     * =========================================
     * CREATE SECTION
     * ADMIN ONLY
     * =========================================
     */

    @PostMapping

    @PreAuthorize("hasRole('ADMIN')")

    public SectionResponse createSection(

            @Valid
            @RequestBody
            SectionRequest request
    ) {

        return sectionService.createSection(
                request
        );
    }

    /*
     * =========================================
     * GET ALL SECTIONS
     * USER + ADMIN
     * =========================================
     */

    @GetMapping
    public List<SectionResponse> getAllSections() {

        return sectionService.getAllSections();
    }

    /*
     * =========================================
     * GET SECTION BY ID
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/{id}")
    public SectionResponse getSectionById(

            @PathVariable Long id
    ) {

        return sectionService.getSectionById(id);
    }

    /*
     * =========================================
     * GET SECTIONS BY COURSE
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/course/{courseId}")
    public List<SectionResponse> getSectionsByCourse(

            @PathVariable Long courseId
    ) {

        return sectionService
                .getSectionsByCourse(courseId);
    }

    /*
     * =========================================
     * UPDATE SECTION
     * ADMIN ONLY
     * =========================================
     */

    @PutMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public SectionResponse updateSection(

            @PathVariable Long id,

            @Valid
            @RequestBody
            SectionRequest request
    ) {

        return sectionService.updateSection(
                id,
                request
        );
    }

    /*
     * =========================================
     * DELETE SECTION
     * ADMIN ONLY
     * =========================================
     */

    @DeleteMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public String deleteSection(

            @PathVariable Long id
    ) {

        return sectionService.deleteSection(id);
    }
}