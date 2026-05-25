package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.QuizResponse;
import com.pfe.learningplatform.dto.QuizSubmissionRequest;

import com.pfe.learningplatform.service.QuizService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    // injection
    public QuizController(
            QuizService quizService
    ) {
        this.quizService = quizService;
    }

    /*
     * =========================================
     * GET QUIZ
     * =========================================
     */

    @GetMapping(
            "/section/{sectionId}/difficulty/{difficulty}"
    )
    public QuizResponse getQuiz(

            @PathVariable Long sectionId,

            @PathVariable String difficulty
    ) {

        return quizService.generateQuiz(
                sectionId,
                difficulty
        );
    }

    /*
     * =========================================
     * SUBMIT QUIZ
     * =========================================
     */

    @PostMapping("/submit")
    public String submitQuiz(

            @RequestBody
            QuizSubmissionRequest request
    ) {

        int score =
                quizService.checkAnswers(request);

        return "Score : " + score + "%";
    }
}