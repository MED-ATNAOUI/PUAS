import os

import requests

from dotenv import load_dotenv


# =========================================
# LOAD ENV VARIABLES
# =========================================

load_dotenv()


# =========================================
# OPENROUTER CONFIG
# =========================================

OPENROUTER_API_KEY = os.getenv(
    "OPENROUTER_API_KEY"
)

OPENROUTER_URL = (
    "https://openrouter.ai/api/v1/chat/completions"
)


# =========================================
# GENERATE WITH LLM
# =========================================

def generate_with_llm(
        prompt: str
):

    """
    =========================================
    GENERIC LLM SERVICE
    =========================================

    This service:

    - sends prompts to OpenRouter
    - receives AI responses
    - returns generated content
    """

    try:

        # =================================
        # HEADERS
        # =================================

        headers = {

            "Authorization":
                f"Bearer {OPENROUTER_API_KEY}",

            "Content-Type":
                "application/json"
        }

        # =================================
        # REQUEST BODY
        # =================================

        body = {

            "model":
                "deepseek/deepseek-chat",

            "messages": [

                {
                    "role": "user",

                    "content": prompt
                }
            ]
        }

        # =================================
        # SEND REQUEST
        # =================================

        response = requests.post(

            OPENROUTER_URL,

            headers=headers,

            json=body
        )

        # =================================
        # CONVERT RESPONSE
        # =================================

        result = response.json()

        ai_text = result["choices"][0][
            "message"
        ]["content"]

        # =================================
        # RETURN AI RESPONSE
        # =================================

        return {

            "response":
                ai_text
        }

    # =====================================
    # ERROR HANDLING
    # =====================================

    except Exception as e:

        print("\n========== LLM ERROR ==========\n")

        print(str(e))

        print("\n===============================\n")

        return {

            "response":
                "AI service unavailable."
        }