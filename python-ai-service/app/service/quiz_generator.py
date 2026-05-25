import json
import re

from app.service.llm_service import generate_with_llm

from app.model.request_models import QuizRequest

from app.model.response_models import (
    QuizResponse,
    QuestionResponse
)


class QuizGenerator:

    async def generate_quiz(

            self,

            request: QuizRequest

    ) -> QuizResponse:

        """
        =========================================
        GENERATE QUIZ USING AI
        =========================================
        """

        # -------------------------------------
        # CREATE PROMPT
        # -------------------------------------

        prompt = f"""
        Generate a quiz about:

        Course: {request.course}
        Section: {request.section}
        Difficulty: {request.difficulty}
        Number of questions: {request.numberOfQuestions}

        You MUST return ONLY valid JSON with this exact format,
        no extra text before or after:

        {{
            "questions": [
                {{
                    "question": "Question text here?",
                    "options": ["Option A", "Option B", "Option C", "Option D"],
                    "correctAnswer": "Option A"
                }}
            ]
        }}

        Rules:
        - Return exactly {request.numberOfQuestions} questions
        - Each question must have exactly 4 options
        - correctAnswer must be one of the options
        - Return ONLY the JSON, no markdown, no explanation
        """

        # -------------------------------------
        # CALL LLM SERVICE
        # -------------------------------------

        llm_response = generate_with_llm(
            prompt
        )

        # -------------------------------------
        # PARSE JSON FROM LLM RESPONSE
        # -------------------------------------

        ai_text = llm_response.get("response", "")

        try:

            # Try to extract JSON from the response
            # The LLM might wrap it in markdown code blocks
            json_match = re.search(
                r'\{[\s\S]*\}',
                ai_text
            )

            if json_match:
                parsed = json.loads(
                    json_match.group()
                )
            else:
                raise ValueError(
                    "No JSON found in LLM response"
                )

        except (json.JSONDecodeError, ValueError) as e:

            print(f"\n========== QUIZ PARSE ERROR ==========\n")
            print(f"Error: {str(e)}")
            print(f"Raw response: {ai_text[:500]}")
            print(f"\n=======================================\n")

            # Return a fallback quiz
            return QuizResponse(
                questions=[
                    QuestionResponse(
                        question="Erreur: impossible de générer le quiz. Veuillez réessayer.",
                        options=[
                            "Réessayer",
                            "Changer de section",
                            "Changer de difficulté",
                            "Contacter le support"
                        ],
                        correctAnswer="Réessayer"
                    )
                ]
            )

        # -------------------------------------
        # CONVERT RESPONSE
        # -------------------------------------

        questions = []

        for q in parsed.get("questions", []):

            question = QuestionResponse(

                question=q.get("question", ""),

                options=q.get("options", [
                    "A", "B", "C", "D"
                ]),

                correctAnswer=q.get(
                    "correctAnswer", ""
                )
            )

            questions.append(question)

        # -------------------------------------
        # RETURN QUIZ RESPONSE
        # -------------------------------------

        return QuizResponse(
            questions=questions
        )