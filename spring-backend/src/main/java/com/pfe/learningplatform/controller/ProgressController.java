package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.ProgressResponse;
import com.pfe.learningplatform.dto.QuizSubmissionRequest;
import com.pfe.learningplatform.service.ProgressService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProgressController {

    private final ProgressService progressService;

    /*
     * =========================================
     * SUBMIT QUIZ ANSWERS
     * =========================================
     */

    @PostMapping("/submit-quiz")
    public ResponseEntity<ProgressResponse> submitQuiz(

            @RequestBody QuizSubmissionRequest request
    ) {

        ProgressResponse response =
                progressService.submitQuiz(request);

        return ResponseEntity.ok(response);
    }

    /*
     * =========================================
     * GET STUDENT PROGRESS
     * =========================================
     */

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ProgressResponse>>
    getStudentProgress(

            @PathVariable Long studentId
    ) {

        List<ProgressResponse> progressList =
                progressService.getStudentProgress(
                        studentId
                );

        return ResponseEntity.ok(progressList);
    }
}