import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import SectionCard from '../components/SectionCard';

export default function CourseDetailPage() {
  const { id } = useParams();
  const { user } = useAuth();
  const [course, setCourse] = useState(null);
  const [sections, setSections] = useState([]);
  const [enrolled, setEnrolled] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => { loadData(); }, [id]);

  const loadData = async () => {
    try {
      const [courseRes, sectionsRes, enrollRes] = await Promise.all([
        api.get(`/courses/${id}`),
        api.get(`/sections/course/${id}`),
        api.get(`/enrollments/student/${user.userId}`),
      ]);
      setCourse(courseRes.data);
      setSections(sectionsRes.data);
      setEnrolled(enrollRes.data.some((e) => e.courseId === parseInt(id)));
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const handleEnroll = async () => {
    try {
      await api.post(`/enrollments/student/${user.userId}/course/${id}`);
      setEnrolled(true);
    } catch (err) { console.error(err); }
  };

  const handleUnenroll = async () => {
    try {
      await api.delete(`/enrollments/student/${user.userId}/course/${id}`);
      setEnrolled(false);
    } catch (err) { console.error(err); }
  };

  if (loading) {
    return (
      <div className="max-w-5xl mx-auto px-4 py-10">
        <div className="skeleton h-8 w-64 rounded-lg mb-4" />
        <div className="skeleton h-48 rounded-2xl mb-8" />
        <div className="space-y-4">{[1, 2, 3].map((i) => (<div key={i} className="skeleton h-24 rounded-2xl" />))}</div>
      </div>
    );
  }

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8 page-enter">
      {/* Breadcrumb */}
      <nav className="flex items-center gap-2 text-sm text-slate-500 mb-6">
        <Link to="/home" className="hover:text-indigo-600 transition-colors">Accueil</Link>
        <span>/</span>
        <Link to="/courses" className="hover:text-indigo-600 transition-colors">Cours</Link>
        <span>/</span>
        <span className="text-slate-800 font-medium">{course?.title}</span>
      </nav>

      {/* Course Header */}
      <div className="bg-gradient-to-br from-indigo-600 via-violet-600 to-purple-700 rounded-3xl p-8 md:p-10 mb-8 relative overflow-hidden">
        <div className="absolute -right-10 -top-10 w-48 h-48 bg-white/10 rounded-full" />
        <div className="absolute -left-5 -bottom-10 w-32 h-32 bg-white/5 rounded-full" />
        <div className="relative z-10">
          <h1 className="text-3xl md:text-4xl font-bold text-white mb-4 tracking-tight">{course?.title}</h1>
          <p className="text-white/80 text-lg max-w-2xl leading-relaxed mb-6">{course?.description || 'Aucune description'}</p>
          <div className="flex items-center gap-4">
            <span className="inline-flex items-center gap-2 px-4 py-2 bg-white/15 backdrop-blur-sm text-white rounded-full text-sm font-medium border border-white/20">
              📑 {sections.length} chapitres
            </span>
            {enrolled ? (
              <button onClick={handleUnenroll} className="px-5 py-2.5 bg-white/20 backdrop-blur-sm text-white rounded-xl text-sm font-semibold hover:bg-white/30 transition-all border border-white/20">
                Se désinscrire
              </button>
            ) : (
              <button onClick={handleEnroll} className="px-5 py-2.5 bg-white text-indigo-600 rounded-xl text-sm font-bold hover:bg-white/90 transition-all shadow-lg">
                S'inscrire au cours
              </button>
            )}
          </div>
        </div>
      </div>

      {/* Sections list */}
      <div>
        <h2 className="text-xl font-bold text-slate-800 mb-5">Chapitres du cours</h2>
        {sections.length > 0 ? (
          <div className="space-y-4">
            {sections.map((section, i) => (
              <SectionCard key={section.id} section={section} index={i} />
            ))}
          </div>
        ) : (
          <div className="bg-white rounded-2xl border border-slate-100 p-8 text-center">
            <p className="text-slate-400">Aucun chapitre pour le moment.</p>
          </div>
        )}
      </div>
    </div>
  );
}
