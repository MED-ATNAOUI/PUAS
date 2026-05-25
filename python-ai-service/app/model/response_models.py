from pydantic import BaseModel, Field

from typing import List


# =========================================
# SINGLE QUESTION RESPONSE
# =========================================

class QuestionResponse(BaseModel):

    question: str = Field(
        ...,
        description="Quiz question"
    )

    options: List[str] = Field(
        ...,
        min_length=4,
        max_length=4,
        description="List of 4 possible answers"
    )

    correctAnswer: str = Field(
        ...,
        description="Correct answer"
    )


# =========================================
# QUIZ RESPONSE
# =========================================

class QuizResponse(BaseModel):

    questions: List[QuestionResponse]


# =========================================
# CHAT RESPONSE
# =========================================

class ChatResponse(BaseModel):

    response: str = Field(
        ...,
        description="AI response message"
    )