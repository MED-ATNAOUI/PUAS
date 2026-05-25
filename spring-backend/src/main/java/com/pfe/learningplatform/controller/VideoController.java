package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.VideoRequest;
import com.pfe.learningplatform.dto.VideoResponse;

import com.pfe.learningplatform.service.VideoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideoController {

    /*
     * =========================================
     * VIDEO SERVICE
     * =========================================
     */

    private final VideoService videoService;

    /*
     * =========================================
     * CREATE VIDEO
     * ADMIN ONLY
     * =========================================
     */

    @PostMapping

    @PreAuthorize("hasRole('ADMIN')")

    public VideoResponse createVideo(

            @Valid
            @RequestBody
            VideoRequest request
    ) {

        return videoService.createVideo(
                request
        );
    }

    /*
     * =========================================
     * GET ALL VIDEOS
     * USER + ADMIN
     * =========================================
     */

    @GetMapping
    public List<VideoResponse> getAllVideos() {

        return videoService.getAllVideos();
    }

    /*
     * =========================================
     * GET VIDEO BY ID
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/{id}")
    public VideoResponse getVideoById(

            @PathVariable Long id
    ) {

        return videoService.getVideoById(id);
    }

    /*
     * =========================================
     * GET VIDEOS BY COURSE
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/course/{courseId}")
    public List<VideoResponse> getVideosByCourse(

            @PathVariable
            Long courseId
    ) {

        return videoService.getVideosByCourse(
                courseId
        );
    }

    /*
     * =========================================
     * GET VIDEOS BY SECTION
     * USER + ADMIN
     * =========================================
     */

    @GetMapping("/section/{sectionId}")
    public List<VideoResponse> getVideosBySection(

            @PathVariable
            Long sectionId
    ) {

        return videoService.getVideosBySection(
                sectionId
        );
    }

    /*
     * =========================================
     * UPDATE VIDEO
     * ADMIN ONLY
     * =========================================
     */

    @PutMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public VideoResponse updateVideo(

            @PathVariable Long id,

            @Valid
            @RequestBody
            VideoRequest request
    ) {

        return videoService.updateVideo(
                id,
                request
        );
    }

    /*
     * =========================================
     * DELETE VIDEO
     * ADMIN ONLY
     * =========================================
     */

    @DeleteMapping("/{id}")

    @PreAuthorize("hasRole('ADMIN')")

    public String deleteVideo(

            @PathVariable Long id
    ) {

        return videoService.deleteVideo(id);
    }
}