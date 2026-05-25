import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../api/axios';
import VideoPlayer from '../components/VideoPlayer';
import QuizCard from '../components/QuizCard';

export default function LessonPage() {
  const { id } = useParams();
  const [section, setSection] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get(`/sections/${id}`)
      .then((res) => setSection(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-10">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2 space-y-4">
            <div className="skeleton aspect-video rounded-2xl" />
            <div className="skeleton h-8 w-64 rounded-lg" />
            <div className="skeleton h-40 rounded-2xl" />
          </div>
          <div className="skeleton h-80 rounded-2xl" />
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 page-enter">
      {/* Breadcrumb */}
      <nav className="flex items-center gap-2 text-sm text-slate-500 mb-6">
        <Link to="/home" className="hover:text-indigo-600 transition-colors">Accueil</Link>
        <span>/</span>
        <Link to={`/courses/${section?.courseId}`} className="hover:text-indigo-600 transition-colors">{section?.courseTitle}</Link>
        <span>/</span>
        <span className="text-slate-800 font-medium">{section?.title}</span>
      </nav>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left — Content */}
        <div className="lg:col-span-2 space-y-6">
          <VideoPlayer url={section?.videoUrl} title={section?.title} />

          <div>
            <h1 className="text-2xl md:text-3xl font-bold text-slate-800 tracking-tight mb-4">{section?.title}</h1>
            <div className="bg-white rounded-2xl border border-slate-100 p-6">
              <p className="text-slate-600 leading-relaxed whitespace-pre-line">{section?.content || 'Aucun contenu disponible.'}</p>
            </div>
          </div>

          {section?.summary && (
            <div className="bg-gradient-to-br from-indigo-50 to-violet-50 rounded-2xl border border-indigo-100 p-6">
              <div className="flex items-center gap-2 mb-3">
                <span className="text-lg">🤖</span>
                <h3 className="text-sm font-bold text-indigo-700">Résumé IA</h3>
              </div>
              <p className="text-sm text-indigo-800/80 leading-relaxed">{section.summary}</p>
            </div>
          )}
        </div>

        {/* Right — Sidebar */}
        <div className="space-y-5">
          <div className="bg-white rounded-2xl border border-slate-100 p-6 shadow-md">
            <h3 className="text-lg font-bold text-slate-800 mb-4">Quiz disponible</h3>
            <p className="text-sm text-slate-500 mb-5">Testez vos connaissances sur ce chapitre.</p>
            <div className="space-y-3">
              {['EASY', 'MEDIUM', 'HARD'].map((diff) => (
                <QuizCard key={diff} difficulty={diff} onStart={() => window.location.href = `/quiz/${id}?difficulty=${diff}`} />
              ))}
            </div>
          </div>

          <Link
            to="/chat"
            className="flex items-center gap-3 bg-gradient-to-br from-indigo-600 to-violet-600 text-white rounded-2xl p-5 shadow-lg shadow-indigo-200 hover:shadow-indigo-300 hover:scale-[1.02] transition-all group"
          >
            <span className="text-3xl">🤖</span>
            <div>
              <p className="font-bold text-sm">Chatbot IA</p>
              <p className="text-xs text-white/80">Poser une question sur ce chapitre</p>
            </div>
            <svg className="w-5 h-5 ml-auto group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </Link>
        </div>
      </div>
    </div>
  );
}
