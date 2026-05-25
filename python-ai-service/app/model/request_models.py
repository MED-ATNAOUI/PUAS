from pydantic import BaseModel, Field

from typing import List


# =========================================
# QUIZ REQUEST
# =========================================

class QuizRequest(BaseModel):

    course: str = Field(
        ...,
        description="Course title"
    )

    section: str = Field(
        ...,
        description="Section title"
    )

    difficulty: str = Field(
        ...,
        description="Quiz difficulty"
    )

    numberOfQuestions: int = Field(
        ...,
        gt=0,
        description="Number of quiz questions"
    )


# =========================================
# CHAT REQUEST
# =========================================

class ChatRequest(BaseModel):

    message: str = Field(
        ...,
        min_length=1,
        description="User message"
    )


# =========================================
# EXERCISE RESPONSE
# =========================================

class ExerciseResponse(BaseModel):

    title: str = Field(
        ...,
        description="Exercise title"
    )

    statement: str = Field(
        ...,
        description="Exercise statement (énoncé)"
    )

    solution: str = Field(
        ...,
        description="Exercise solution with explanations"
    )

    difficulty: str = Field(
        ...,
        description="Difficulty level"
    )