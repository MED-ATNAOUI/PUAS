from fastapi import APIRouter

from app.model.request_models import ExerciseRequest
from app.model.response_models import ExerciseResponse
from app.service.exercise_generator import ExerciseGenerator


# =========================================
# CREATE ROUTER
# =========================================

router = APIRouter()


# =========================================
# GENERATE EXERCISE ENDPOINT
# =========================================

@router.post(
    "/generate-exercise",
    response_model=ExerciseResponse
)
async def generate_exercise(
        request: ExerciseRequest
):

    generator = ExerciseGenerator()

    exercise = await generator.generate_exercise(request)

    return exercise