from fastapi import APIRouter

from app.model.request_models import QuizRequest

from app.model.response_models import QuizResponse

from app.service.quiz_generator import QuizGenerator


# =========================================
# CREATE ROUTER
# =========================================

router = APIRouter()


# =========================================
# GENERATE QUIZ ENDPOINT
# =========================================

@router.post(
    "/generate-quiz",
    response_model=QuizResponse
)
async def generate_quiz(
        request: QuizRequest
):

    generator = QuizGenerator()

    quiz = await generator.generate_quiz(
        request
    )

    return quiz