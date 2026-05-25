package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.ExerciseResponse;
import com.pfe.learningplatform.service.IAService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExerciseController {

    private final IAService iaService;

    /*
     * =========================================
     * GENERATE EXERCISE BY SECTION + DIFFICULTY
     * =========================================
     */

    @GetMapping("/section/{sectionId}/difficulty/{difficulty}")
    public ResponseEntity<ExerciseResponse> generateExercise(

            @PathVariable Long sectionId,

            @PathVariable String difficulty) {

        ExerciseResponse exercise = iaService.generateExercise(
                sectionId,
                difficulty);

        return ResponseEntity.ok(exercise);
    }
}