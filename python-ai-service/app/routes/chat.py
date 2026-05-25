from fastapi import APIRouter

from app.model.request_models import ChatRequest

from app.model.response_models import ChatResponse

from app.service.chat import Chat


router = APIRouter()


@router.post(
    "/chat",
    response_model=ChatResponse
)
async def chat_ai(
        request: ChatRequest
):

    chat = Chat()

    response = await chat.generate_response(
        request
    )

    return response