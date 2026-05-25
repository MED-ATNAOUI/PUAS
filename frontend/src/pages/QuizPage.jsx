import { useState, useEffect } from 'react';
import { useParams, useSearchParams, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import ProgressBar from '../components/ProgressBar';

export default function QuizPage() {
  const { sectionId } = useParams();
  const [searchParams] = useSearchParams();
  const difficulty = searchParams.get('difficulty') || 'EASY';
  const { user } = useAuth();

  const [quiz, setQuiz] = useState(null);
  const [currentQ, setCurrentQ] = useState(0);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    api.get(`/quiz/section/${sectionId}/difficulty/${difficulty}`)
      .then((res) => setQuiz(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [sectionId, difficulty]);

  const handleAnswer = (questionId, answer) => {
    setAnswers({ ...answers, [questionId]: answer });
  };

  const handleNext = () => {
    if (currentQ < quiz.questions.length - 1) setCurrentQ(currentQ + 1);
  };
  const handlePrev = () => {
    if (currentQ > 0) setCurrentQ(currentQ - 1);
  };

  const handleSubmit = async () => {
    setSubmitting(true);
    try {
      const res = await api.post('/quiz/submit', {
        quizId: quiz.id,
        studentId: user.userId,
        answers,
      });
      setResult(res.data);
    } catch (err) { console.error(err); }
    finally { setSubmitting(false); }
  };

  if (loading) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-10 space-y-4">
        <div className="skeleton h-4 w-48 rounded" />
        <div className="skeleton h-64 rounded-2xl" />
      </div>
    );
  }

  if (!quiz || !quiz.questions?.length) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center">
        <p className="text-6xl mb-4">📝</p>
        <h2 className="text-xl font-bold text-slate-800 mb-2">Aucun quiz disponible</h2>
        <p className="text-slate-500 mb-6">Il n'y a pas de quiz pour cette section et cette difficulté.</p>
        <Link to={`/lesson/${sectionId}`} className="px-6 py-3 bg-indigo-600 text-white rounded-xl font-semibold hover:bg-indigo-700 transition-all">
          Retour au chapitre
        </Link>
      </div>
    );
  }

  // Result screen
  if (result) {
    const scoreNum = parseInt(result.replace(/\D/g, '')) || 0;
    return (
      <div className="max-w-2xl mx-auto px-4 py-16 text-center page-enter">
        <div className="bg-white rounded-3xl shadow-xl border border-slate-100 p-10">
          <div className="relative w-36 h-36 mx-auto mb-6">
            <svg className="w-36 h-36 -rotate-90" viewBox="0 0 120 120">
              <circle cx="60" cy="60" r="50" stroke="#e2e8f0" strokeWidth="10" fill="none" />
              <circle cx="60" cy="60" r="50" stroke={scoreNum >= 70 ? '#10b981' : scoreNum >= 50 ? '#f59e0b' : '#ef4444'} strokeWidth="10" fill="none"
                strokeDasharray={`${(scoreNum / 100) * 314} 314`} strokeLinecap="round" className="transition-all duration-1000" />
            </svg>
            <div className="absolute inset-0 flex items-center justify-center">
              <span className="text-3xl font-bold text-slate-800">{scoreNum}%</span>
            </div>
          </div>

          <h2 className="text-2xl font-bold text-slate-800 mb-2">
            {scoreNum >= 70 ? '🎉 Excellent !' : scoreNum >= 50 ? '💪 Bien joué !' : '📖 Continuez !'}
          </h2>
          <p className="text-slate-500 mb-8">{result}</p>

          <div className="flex items-center justify-center gap-4">
            <button onClick={() => { setResult(null); setCurrentQ(0); setAnswers({}); }}
              className="px-6 py-3 border-2 border-indigo-600 text-indigo-600 rounded-xl font-semibold hover:bg-indigo-50 transition-all">
              Recommencer
            </button>
            <Link to={`/lesson/${sectionId}`} className="px-6 py-3 bg-indigo-600 text-white rounded-xl font-semibold hover:bg-indigo-700 transition-all shadow-lg shadow-indigo-200">
              Retour au cours
            </Link>
          </div>
        </div>
      </div>
    );
  }

  const question = quiz.questions[currentQ];
  const options = [
    { key: 'A', text: question.optionA },
    { key: 'B', text: question.optionB },
    { key: 'C', text: question.optionC },
    { key: 'D', text: question.optionD },
  ];

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 py-8 page-enter">
      {/* Header */}
      <div className="mb-8">
        <div className="flex items-center justify-between mb-3">
          <span className="text-sm font-medium text-slate-500">Question {currentQ + 1} / {quiz.questions.length}</span>
          <span className={`px-3 py-1 rounded-full text-xs font-semibold ${
            difficulty === 'EASY' ? 'bg-emerald-50 text-emerald-600' :
            difficulty === 'MEDIUM' ? 'bg-amber-50 text-amber-600' : 'bg-red-50 text-red-600'
          }`}>
            {difficulty === 'EASY' ? 'Facile' : difficulty === 'MEDIUM' ? 'Moyen' : 'Difficile'}
          </span>
        </div>
        <ProgressBar value={currentQ + 1} max={quiz.questions.length} color="indigo" size="sm" />
      </div>

      {/* Question */}
      <div className="bg-white rounded-3xl shadow-xl border border-slate-100 p-8">
        <h2 className="text-xl font-bold text-slate-800 mb-8 leading-relaxed">{question.question}</h2>

        <div className="space-y-3">
          {options.map((opt) => (
            <button
              key={opt.key}
              onClick={() => handleAnswer(question.id, opt.key)}
              className={`w-full text-left p-4 rounded-2xl border-2 transition-all flex items-center gap-4 ${
                answers[question.id] === opt.key
                  ? 'border-indigo-500 bg-indigo-50 text-indigo-700'
                  : 'border-slate-200 hover:border-slate-300 hover:bg-slate-50 text-slate-700'
              }`}
            >
              <span className={`w-10 h-10 rounded-xl flex items-center justify-center text-sm font-bold shrink-0 ${
                answers[question.id] === opt.key
                  ? 'bg-indigo-600 text-white'
                  : 'bg-slate-100 text-slate-500'
              }`}>
                {opt.key}
              </span>
              <span className="font-medium">{opt.text}</span>
            </button>
          ))}
        </div>

        {/* Navigation */}
        <div className="flex items-center justify-between mt-8 pt-6 border-t border-slate-100">
          <button onClick={handlePrev} disabled={currentQ === 0}
            className="px-5 py-2.5 border border-slate-200 rounded-xl text-sm font-medium text-slate-600 hover:bg-slate-50 disabled:opacity-40 disabled:cursor-not-allowed transition-all">
            ← Précédent
          </button>

          {currentQ === quiz.questions.length - 1 ? (
            <button onClick={handleSubmit} disabled={submitting || Object.keys(answers).length < quiz.questions.length}
              className="px-6 py-2.5 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 text-white rounded-xl text-sm font-semibold transition-all shadow-lg shadow-indigo-200 flex items-center gap-2">
              {submitting && <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />}
              Terminer le quiz
            </button>
          ) : (
            <button onClick={handleNext} disabled={!answers[question.id]}
              className="px-5 py-2.5 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 text-white rounded-xl text-sm font-semibold transition-all shadow-lg shadow-indigo-200">
              Suivant →
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
