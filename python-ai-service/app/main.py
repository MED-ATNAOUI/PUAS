from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.routes.quiz import router as quiz_router
from app.routes.chat import router as chat_router
from app.routes.exercise import router as exercise_router


# =========================================
# CREATE FASTAPI APPLICATION
# =========================================

app = FastAPI(
    title="PFE AI Service",
    description="""
    AI microservice for:
    - Quiz generation
    - Exercise generation
    - AI chat
    """,
    version="2.0.0"
)


# =========================================
# CORS MIDDLEWARE
# =========================================

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)


# =========================================
# ROUTES
# =========================================

app.include_router(quiz_router, prefix="/api", tags=["Quiz AI"])
app.include_router(chat_router, prefix="/api", tags=["Chat AI"])
app.include_router(exercise_router, prefix="/api", tags=["Exercise AI"])


# =========================================
# ROOT
# =========================================

@app.get("/")
async def root():
    return {"message": "PFE AI Service is running - v2.0"}