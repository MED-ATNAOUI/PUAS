# 🎓 PFE Learning Platform

Plateforme d'apprentissage intelligente avec génération de quiz par IA et chatbot pédagogique.

## 📐 Architecture du Projet

```
pfe-learning-platform/
├── spring-backend/        → API REST (Spring Boot 3.3 / Java 17)
├── python-ai-service/     → Microservice IA (FastAPI / Python)
└── frontend/              → Interface utilisateur (React / Vite)
```

| Composant | Technologie | Port |
|-----------|-------------|------|
| Backend | Spring Boot 3.3, JPA, JWT, MySQL | `8080` |
| Service IA | FastAPI, OpenRouter (DeepSeek) | `8000` |
| Frontend | React + Vite | `5173` |

---

## 🔧 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java 17+** → [Télécharger](https://adoptium.net/)
- **MySQL 8+** → [Télécharger](https://dev.mysql.com/downloads/)
- **Python 3.10+** → [Télécharger](https://www.python.org/downloads/)
- **Node.js 18+** → [Télécharger](https://nodejs.org/) *(pour le frontend)*
- **Postman** *(optionnel, recommandé)* → [Télécharger](https://www.postman.com/downloads/)

---

## 🚀 Étapes de Lancement

### Étape 1 — Créer la base de données MySQL

Ouvrez MySQL et exécutez :

```sql
CREATE DATABASE IF NOT EXISTS learningplatform;
```

> **Note** : Les tables sont créées automatiquement au démarrage grâce à `ddl-auto: update`.

Vérifiez que les identifiants dans `spring-backend/src/main/resources/application.yml` correspondent à votre configuration MySQL :

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/learningplatform
    username: root
    password: admin123
```

Si votre mot de passe MySQL est différent, modifiez le fichier `application.yml`.

---

### Étape 2 — Lancer le Backend Spring Boot

**Linux / macOS** :
```bash
cd spring-backend
./mvnw spring-boot:run
```

**Windows (PowerShell)** :
```powershell
cd spring-backend
mvn clean compile
mvn spring-boot:run
```

✅ Le backend démarre sur **http://localhost:8080**

---

### Étape 3 — Lancer le Service Python IA

```bash
cd python-ai-service
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

✅ Le service IA démarre sur **http://localhost:8000**

---

### Étape 4 — Lancer le Frontend React

```powershell
cd frontend

# Installer les dépendances
npm install
npm install react-router-dom axios tailwindcss @tailwindcss/vite

# Supprimer les anciens fichiers inutiles
Remove-Item -Path "src\App.css" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\pages\Intro.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\pages\home.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\pages\Exercise.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\pages\Chapter.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\pages\Quiz.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\components\Chatbot.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\components\ChapterCard.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\components\ModuleCard.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\components\NewsSection.jsx" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "src\service" -Recurse -Force -ErrorAction SilentlyContinue

# Lancer le serveur de développement
npm run dev
```

✅ Le frontend démarre sur **http://localhost:5173**

---

## 📖 Documentation API Swagger

Une fois le backend lancé, accédez à la documentation interactive :

> **http://localhost:8080/swagger-ui.html**

Vous pouvez tester tous les endpoints directement depuis cette interface.

---

## 🧪 Guide de Test des Endpoints

### 🔓 1. Authentification

#### 1.1 — Inscription d'un utilisateur

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean@test.com",
    "password": "123456"
  }'
```

**Réponse attendue** :
```
Utilisateur enregistré avec succès
```

#### 1.2 — Inscription d'un administrateur

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Admin",
    "prenom": "Super",
    "email": "admin@test.com",
    "password": "123456",
    "role": "ADMIN",
    "adminCode": "apprendreàcomprendre"
  }'
```

#### 1.3 — Connexion (récupérer le token JWT)

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jean@test.com",
    "password": "123456"
  }'
```

**Réponse attendue** :
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "USER",
  "email": "jean@test.com",
  "userId": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "level": "beginner"
}
```

> ⚠️ **Important** : Copiez le `token` — vous en aurez besoin pour toutes les requêtes suivantes.
> Remplacez `VOTRE_TOKEN_USER` ou `VOTRE_TOKEN_ADMIN` par le token reçu.

#### 1.4 — Modifier le profil

```bash
curl -X PUT http://localhost:8080/auth/profile/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_USER" \
  -d '{
    "nom": "Dupont-Martin",
    "prenom": "Jean-Pierre"
  }'
```

---

### 📚 2. Gestion des Cours (ADMIN)

#### 2.1 — Créer un cours

```bash
curl -X POST http://localhost:8080/courses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Java Avancé",
    "description": "Cours complet sur Java : POO, Collections, Streams"
  }'
```

#### 2.2 — Lister tous les cours

```bash
curl http://localhost:8080/courses \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 2.3 — Récupérer un cours par ID

```bash
curl http://localhost:8080/courses/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 2.4 — Modifier un cours

```bash
curl -X PUT http://localhost:8080/courses/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Java Avancé - Mise à jour",
    "description": "Cours complet mis à jour"
  }'
```

#### 2.5 — Supprimer un cours

```bash
curl -X DELETE http://localhost:8080/courses/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

---

### 📑 3. Gestion des Sections (ADMIN)

#### 3.1 — Créer une section

```bash
curl -X POST http://localhost:8080/sections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Les Collections Java",
    "content": "Les collections en Java permettent de stocker et manipuler des groupes d objets...",
    "summary": "Introduction aux List, Set et Map en Java",
    "courseId": 1
  }'
```

#### 3.2 — Lister toutes les sections

```bash
curl http://localhost:8080/sections \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 3.3 — Sections d'un cours

```bash
curl http://localhost:8080/sections/course/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 3.4 — Modifier une section

```bash
curl -X PUT http://localhost:8080/sections/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Les Collections Java - Mise à jour",
    "content": "Contenu mis à jour...",
    "courseId": 1
  }'
```

#### 3.5 — Supprimer une section

```bash
curl -X DELETE http://localhost:8080/sections/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

---

### 🎬 4. Gestion des Vidéos (ADMIN)

#### 4.1 — Ajouter une vidéo

```bash
curl -X POST http://localhost:8080/videos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Introduction aux Collections",
    "description": "Vidéo explicative sur les collections Java",
    "youtubeUrl": "https://www.youtube.com/watch?v=example",
    "thumbnailUrl": "https://img.youtube.com/vi/example/0.jpg",
    "courseId": 1,
    "sectionId": 1
  }'
```

#### 4.2 — Lister les vidéos d'un cours

```bash
curl http://localhost:8080/videos/course/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 4.3 — Lister les vidéos d'une section

```bash
curl http://localhost:8080/videos/section/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 4.4 — Modifier une vidéo

```bash
curl -X PUT http://localhost:8080/videos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN" \
  -d '{
    "title": "Titre mis à jour",
    "youtubeUrl": "https://www.youtube.com/watch?v=new",
    "courseId": 1,
    "sectionId": 1
  }'
```

#### 4.5 — Supprimer une vidéo

```bash
curl -X DELETE http://localhost:8080/videos/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

---

### 📝 5. Inscription aux Cours (Enrollment)

#### 5.1 — S'inscrire à un cours

```bash
curl -X POST http://localhost:8080/enrollments/student/1/course/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 5.2 — Voir mes inscriptions

```bash
curl http://localhost:8080/enrollments/student/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 5.3 — Voir les étudiants d'un cours

```bash
curl http://localhost:8080/enrollments/course/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

#### 5.4 — Se désinscrire d'un cours

```bash
curl -X DELETE http://localhost:8080/enrollments/student/1/course/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

---

### 🤖 6. Quiz IA

> ⚠️ Le service Python doit être en cours d'exécution sur le port 8000.

#### 6.1 — Générer un quiz

```bash
curl http://localhost:8080/quiz/section/1/difficulty/beginner \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

Difficultés disponibles : `beginner`, `intermediate`, `advanced`

#### 6.2 — Soumettre les réponses d'un quiz

```bash
curl -X POST http://localhost:8080/api/progress/submit-quiz \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_USER" \
  -d '{
    "quizId": 1,
    "studentId": 1,
    "answers": {
      "1": "Option A",
      "2": "Option B",
      "3": "Option C"
    }
  }'
```

#### 6.3 — Voir ma progression

```bash
curl http://localhost:8080/api/progress/student/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

---

### 💬 7. Chat IA

#### 7.1 — Envoyer un message (nouvelle conversation)

```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_USER" \
  -d '{
    "message": "Explique-moi les listes en Java",
    "userId": 1
  }'
```

#### 7.2 — Continuer une conversation existante

```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_USER" \
  -d '{
    "message": "Et les HashMap ?",
    "userId": 1,
    "conversationId": 1
  }'
```

#### 7.3 — Voir mes conversations

```bash
curl http://localhost:8080/chat/conversations/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 7.4 — Voir une conversation avec ses messages

```bash
curl http://localhost:8080/chat/conversation/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

#### 7.5 — Supprimer une conversation

```bash
curl -X DELETE http://localhost:8080/chat/conversation/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_USER"
```

---

### 👑 8. Administration (ADMIN uniquement)

#### 8.1 — Lister tous les utilisateurs

```bash
curl http://localhost:8080/admin/users \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

#### 8.2 — Voir un utilisateur par ID

```bash
curl http://localhost:8080/admin/users/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

#### 8.3 — Statistiques du dashboard

```bash
curl http://localhost:8080/admin/stats \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

**Réponse attendue** :
```json
{
  "totalUsers": 2,
  "totalCourses": 1,
  "totalSections": 1,
  "totalVideos": 1,
  "totalQuizzes": 1,
  "totalEnrollments": 1
}
```

#### 8.4 — Supprimer un utilisateur

```bash
curl -X DELETE http://localhost:8080/admin/user/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_ADMIN"
```

---

## 🧪 Test avec Postman (Recommandé)

1. **Importer l'API** : `GET http://localhost:8080/v3/api-docs`
2. **Créer une variable d'environnement** `token` pour stocker le JWT
3. **Configurer l'Authorization** : Type `Bearer Token` → `{{token}}`
4. Tester chaque endpoint dans l'ordre ci-dessus

---

## 🔒 Rôles et Accès

| Rôle | Accès |
|------|-------|
| **Public** | `/auth/register`, `/auth/login`, `/swagger-ui/**` |
| **USER** | Tous les endpoints sauf `/admin/**` |
| **ADMIN** | Tous les endpoints + CRUD complet + `/admin/**` |

---

## 🛠️ Technologies Utilisées

### Backend (Spring Boot)
- Spring Boot 3.3.5
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA + MySQL
- Bean Validation (jakarta.validation)
- Swagger / OpenAPI (springdoc 2.3.0)
- Lombok

### Service IA (Python)
- FastAPI 0.109
- OpenRouter API (DeepSeek Chat)
- Pydantic 2.5

### Frontend
- React 18 + Vite
