from app.model.request_models import ChatRequest

from app.model.response_models import ChatResponse

from app.service.llm_service import generate_with_llm


class Chat:

    async def generate_response(

            self,

            request: ChatRequest

    ) -> ChatResponse:

        """
        =========================================
        GENERATE AI CHAT RESPONSE
        =========================================
        """

        # -------------------------------------
        # CREATE CHAT PROMPT
        # -------------------------------------

        prompt = f"""

        You are an intelligent AI tutor
        for an educational platform.

        Answer clearly and simply.

        User message:

        {request.message}

        """

        # -------------------------------------
        # CALL LLM
        # -------------------------------------

        llm_response = generate_with_llm(
            prompt
        )

        # -------------------------------------
        # GET RESPONSE TEXT
        # -------------------------------------

        ai_message = llm_response.get(

            "response",

            "No response generated."
        )

        # -------------------------------------
        # RETURN CHAT RESPONSE
        # -------------------------------------

        return ChatResponse(
            response=ai_message
        )