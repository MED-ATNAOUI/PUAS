import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import CourseCard from '../components/CourseCard';
import StatCard from '../components/StatCard';

export default function HomePage() {
  const { user } = useAuth();
  const [enrollments, setEnrollments] = useState([]);
  const [courses, setCourses] = useState([]);
  const [progress, setProgress] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [enrollRes, coursesRes, progressRes] = await Promise.all([
        api.get(`/enrollments/student/${user.userId}`),
        api.get('/courses'),
        api.get(`/api/progress/student/${user.userId}`),
      ]);
      setEnrollments(enrollRes.data);
      setCourses(coursesRes.data);
      setProgress(progressRes.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const enrolledIds = new Set(enrollments.map((e) => e.courseId));
  const recommendedCourses = courses.filter((c) => !enrolledIds.has(c.id)).slice(0, 3);
  const enrolledCourses = courses.filter((c) => enrolledIds.has(c.id));

  const avgScore = progress.length > 0
    ? Math.round(progress.reduce((sum, p) => sum + (p.averageScore || p.score || 0), 0) / progress.length)
    : 0;

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-10">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
          {[1, 2, 3].map((i) => (<div key={i} className="skeleton h-28 rounded-2xl" />))}
        </div>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[1, 2, 3].map((i) => (<div key={i} className="skeleton h-64 rounded-2xl" />))}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 page-enter">
      {/* Salutation */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-800 tracking-tight">
          Bonjour, {user?.prenom} 👋
        </h1>
        <p className="text-slate-500 mt-1">Bienvenue sur votre tableau de bord.</p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-5 mb-10">
        <StatCard icon="📚" value={enrollments.length} label="Cours inscrits" color="indigo" />
        <StatCard icon="✅" value={progress.length} label="Quiz complétés" color="emerald" />
        <StatCard icon="⭐" value={`${avgScore}%`} label="Score moyen" color="violet" />
      </div>

      {/* Mes cours */}
      <section className="mb-10">
        <div className="flex items-center justify-between mb-5">
          <h2 className="text-xl font-bold text-slate-800">Mes cours</h2>
          <Link to="/courses" className="text-sm text-indigo-600 font-semibold hover:text-indigo-700">
            Voir tous →
          </Link>
        </div>

        {enrolledCourses.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {enrolledCourses.map((course) => (
              <CourseCard key={course.id} course={course} enrolled />
            ))}
          </div>
        ) : (
          <div className="bg-white rounded-2xl border border-slate-100 p-8 text-center">
            <p className="text-slate-400 mb-4">Vous n'êtes inscrit à aucun cours.</p>
            <Link to="/courses" className="px-6 py-3 bg-indigo-600 text-white font-semibold rounded-xl hover:bg-indigo-700 transition-all inline-block">
              Découvrir les cours
            </Link>
          </div>
        )}
      </section>

      {/* Recommandés */}
      {recommendedCourses.length > 0 && (
        <section>
          <h2 className="text-xl font-bold text-slate-800 mb-5">Cours recommandés</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {recommendedCourses.map((course) => (
              <CourseCard key={course.id} course={course} />
            ))}
          </div>
        </section>
      )}
    </div>
  );
}
