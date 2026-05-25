package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.QuestionResponse;
import com.pfe.learningplatform.dto.QuizResponse;
import com.pfe.learningplatform.dto.QuizSubmissionRequest;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Question;
import com.pfe.learningplatform.model.Quiz;
import com.pfe.learningplatform.model.Section;

import com.pfe.learningplatform.repository.QuizRepository;
import com.pfe.learningplatform.repository.SectionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final SectionRepository sectionRepository;

    private final IAService aiService;

    // injection
    public QuizService(

            QuizRepository quizRepository,

            SectionRepository sectionRepository,

            IAService aiService
    ) {

        this.quizRepository = quizRepository;

        this.sectionRepository = sectionRepository;

        this.aiService = aiService;
    }

    /*
     * =========================================
     * GET OR GENERATE QUIZ
     * =========================================
     */

    public QuizResponse generateQuiz(

            Long sectionId,

            String difficulty
    ) {

        // récupérer section
        Section section =
                sectionRepository.findById(sectionId)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Section non trouvée"
                                )
                        );

        // quiz existants
        List<Quiz> quizzes =

                quizRepository.findBySectionAndDifficulty(
                        section,
                        difficulty
                );

        Quiz quiz;

        // quiz trouvé
        if (!quizzes.isEmpty()) {

            quiz = quizzes.get(

                    new Random().nextInt(quizzes.size())
            );

        } else {

            // génération IA
            quiz = aiService.generateQuiz(
                    sectionId,
                    difficulty
            );

            // sauvegarde
            quizRepository.save(quiz);
        }

        return convertToDTO(quiz);
    }

    /*
     * =========================================
     * CHECK ANSWERS
     * =========================================
     */

    public int checkAnswers(
            QuizSubmissionRequest request
    ) {

        Quiz quiz =
                quizRepository.findById(
                        request.getQuizId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Quiz non trouvé"
                        )
                );

        int correctAnswers = 0;

        int totalQuestions =
                quiz.getQuestions().size();

        /*
         * =========================================
         * LOOP QUESTIONS
         * =========================================
         */

        for (Question question : quiz.getQuestions()) {

            String studentAnswer =

                    request.getAnswers()
                            .get(question.getId());

            /*
             * =====================================
             * CHECK ANSWER
             * =====================================
             */

            if (

                    question.getCorrectAnswer()

                            .equals(studentAnswer)
            ) {

                correctAnswers++;
            }
        }

        /*
         * =========================================
         * RETURN SCORE %
         * =========================================
         */

        return (correctAnswers * 100)
                / totalQuestions;
    }

    /*
     * =========================================
     * ENTITY -> DTO
     * =========================================
     */

    private QuizResponse convertToDTO(
            Quiz quiz
    ) {

        List<QuestionResponse> questions =

                quiz.getQuestions()

                        .stream()

                        .map(question ->

                                QuestionResponse.builder()

                                        .id(question.getId())

                                        .question(
                                                question.getQuestion()
                                        )

                                        .optionA(
                                                question.getOptionA()
                                        )

                                        .optionB(
                                                question.getOptionB()
                                        )

                                        .optionC(
                                                question.getOptionC()
                                        )

                                        .optionD(
                                                question.getOptionD()
                                        )

                                        .build()

                        )

                        .toList();

        return QuizResponse.builder()

                .id(quiz.getId())

                .difficulty(quiz.getDifficulty())

                .questions(questions)

                .build();
    }
}