from app.service.llm_service import generate_with_llm
from app.model.request_models import ExerciseRequest
from app.model.response_models import ExerciseResponse


class ExerciseGenerator:

    async def generate_exercise(
            self,
            request: ExerciseRequest
    ) -> ExerciseResponse:

        """
        =========================================
        GENERATE PROGRAMMING EXERCISE USING AI
        =========================================
        """

        # -------------------------------------
        # BUILD PROMPT
        # -------------------------------------

        prompt = f"""
        Tu es un professeur expert en informatique et programmation.
        Génère un exercice de programmation complet en français.

        Cours : {request.course}
        Section : {request.section}
        Niveau de difficulté : {request.difficulty}

        STRUCTURE OBLIGATOIRE — réponds UNIQUEMENT avec ce JSON exact,
        sans texte avant ni après :

        {{
            "title": "Titre de l'exercice",
            "statement": "Énoncé complet de l'exercice avec contexte, contraintes et exemples d'entrée/sortie. Utilise \\n pour les sauts de ligne.",
            "solution": "Solution complète commentée avec blocs de code. Utilise \\n pour les sauts de ligne.",
            "difficulty": "{request.difficulty}"
        }}

        Règles :
        - L'énoncé (statement) doit être détaillé : contexte, objectif, contraintes, exemples
        - La solution doit être commentée et expliquer chaque étape
        - Utilise du markdown dans statement et solution (blocs ```code```)
        - Adapte la complexité au niveau : {request.difficulty}
        - Réponds UNIQUEMENT en JSON valide, pas de texte autour
        """

        # -------------------------------------
        # CALL LLM
        # -------------------------------------

        llm_response = generate_with_llm(prompt)

        ai_text = llm_response.get("response", "")

        # -------------------------------------
        # PARSE JSON RESPONSE
        # -------------------------------------

        import json
        import re

        try:
            json_match = re.search(r'\{[\s\S]*\}', ai_text)
            if json_match:
                parsed = json.loads(json_match.group())
            else:
                raise ValueError("No JSON found")

            return ExerciseResponse(
                title=parsed.get("title", "Exercice"),
                statement=parsed.get("statement", ""),
                solution=parsed.get("solution", ""),
                difficulty=parsed.get("difficulty", request.difficulty)
            )

        except (json.JSONDecodeError, ValueError) as e:

            print(f"\n========== EXERCISE PARSE ERROR ==========\n")
            print(f"Error: {str(e)}")
            print(f"Raw response: {ai_text[:500]}")
            print(f"\n==========================================\n")

            # Fallback : utiliser le texte brut de l'IA
            # Séparer sur un marqueur si l'IA n'a pas retourné du JSON
            if "===SOLUTION===" in ai_text:
                parts = ai_text.split("===SOLUTION===", 1)
                return ExerciseResponse(
                    title="Exercice généré",
                    statement=parts[0].strip(),
                    solution=parts[1].strip() if len(parts) > 1 else "",
                    difficulty=request.difficulty
                )

            return ExerciseResponse(
                title="Exercice généré",
                statement=ai_text[:2000],
                solution="Voir l'énoncé ci-dessus pour la solution.",
                difficulty=request.difficulty
            )