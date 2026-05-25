package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.VideoRequest;
import com.pfe.learningplatform.dto.VideoResponse;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Video;

import com.pfe.learningplatform.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    /*
     * =====================================
     * VIDEO REPOSITORY
     * =====================================
     */

    private final VideoRepository videoRepository;

    /*
     * =====================================
     * CREATE VIDEO
     * =====================================
     */

    public VideoResponse createVideo(

            VideoRequest request
    ) {

        Video video =

                Video.builder()

                        .title(request.getTitle())

                        .description(
                                request.getDescription()
                        )

                        .youtubeUrl(
                                request.getYoutubeUrl()
                        )

                        .thumbnailUrl(
                                request.getThumbnailUrl()
                        )

                        .courseId(
                                request.getCourseId()
                        )

                        .sectionId(
                                request.getSectionId()
                        )

                        .build();

        Video savedVideo =
                videoRepository.save(video);

        return mapToResponse(savedVideo);
    }

    /*
     * =====================================
     * GET ALL VIDEOS
     * =====================================
     */

    public List<VideoResponse> getAllVideos() {

        return videoRepository.findAll()

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * =====================================
     * GET VIDEO BY ID
     * =====================================
     */

    public VideoResponse getVideoById(Long id) {

        Video video =
                videoRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vidéo non trouvée"
                                ));

        return mapToResponse(video);
    }

    /*
     * =====================================
     * GET VIDEOS BY COURSE
     * =====================================
     */

    public List<VideoResponse> getVideosByCourse(

            Long courseId
    ) {

        return videoRepository.findByCourseId(courseId)

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * =====================================
     * GET VIDEOS BY SECTION
     * =====================================
     */

    public List<VideoResponse> getVideosBySection(

            Long sectionId
    ) {

        return videoRepository.findBySectionId(sectionId)

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());
    }

    /*
     * =====================================
     * UPDATE VIDEO
     * =====================================
     */

    public VideoResponse updateVideo(

            Long id,

            VideoRequest request
    ) {

        Video video =
                videoRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vidéo non trouvée"
                                ));

        if (request.getTitle() != null
                && !request.getTitle().isEmpty()) {

            video.setTitle(request.getTitle());
        }

        if (request.getDescription() != null
                && !request.getDescription().isEmpty()) {

            video.setDescription(
                    request.getDescription()
            );
        }

        if (request.getYoutubeUrl() != null
                && !request.getYoutubeUrl().isEmpty()) {

            video.setYoutubeUrl(
                    request.getYoutubeUrl()
            );
        }

        if (request.getThumbnailUrl() != null
                && !request.getThumbnailUrl().isEmpty()) {

            video.setThumbnailUrl(
                    request.getThumbnailUrl()
            );
        }

        Video updatedVideo =
                videoRepository.save(video);

        return mapToResponse(updatedVideo);
    }

    /*
     * =====================================
     * DELETE VIDEO
     * =====================================
     */

    public String deleteVideo(Long id) {

        Video video =
                videoRepository.findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vidéo non trouvée"
                                ));

        videoRepository.delete(video);

        return "Vidéo supprimée avec succès";
    }

    /*
     * =====================================
     * MAP ENTITY TO DTO
     * =====================================
     */

    private VideoResponse mapToResponse(

            Video video
    ) {

        return VideoResponse.builder()

                .id(video.getId())

                .title(video.getTitle())

                .description(video.getDescription())

                .youtubeUrl(video.getYoutubeUrl())

                .thumbnailUrl(video.getThumbnailUrl())

                .courseId(video.getCourseId())

                .sectionId(video.getSectionId())

                .build();
    }
}