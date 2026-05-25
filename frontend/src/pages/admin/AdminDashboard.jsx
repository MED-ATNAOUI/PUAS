import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../../api/axios';
import StatCard from '../../components/StatCard';

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/admin/stats')
      .then((res) => setStats(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-10">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {[1, 2, 3, 4, 5, 6].map((i) => (<div key={i} className="skeleton h-28 rounded-2xl" />))}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 page-enter">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-800 tracking-tight">Dashboard Admin</h1>
        <p className="text-slate-500 mt-1">Vue d'ensemble de la plateforme</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5 mb-10">
        <StatCard icon="👥" value={stats?.totalUsers || 0} label="Utilisateurs" color="indigo" />
        <StatCard icon="📚" value={stats?.totalCourses || 0} label="Cours" color="violet" />
        <StatCard icon="📑" value={stats?.totalSections || 0} label="Sections" color="cyan" />
        <StatCard icon="🎬" value={stats?.totalVideos || 0} label="Vidéos" color="emerald" />
        <StatCard icon="📝" value={stats?.totalQuizzes || 0} label="Quiz" color="amber" />
        <StatCard icon="🎓" value={stats?.totalEnrollments || 0} label="Inscriptions" color="rose" />
      </div>

      {/* Quick links */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <Link to="/admin/courses" className="bg-white rounded-2xl shadow-md hover:shadow-xl border border-slate-100 p-6 flex items-center gap-4 group transition-all">
          <div className="w-12 h-12 bg-gradient-to-br from-violet-500 to-violet-600 rounded-2xl flex items-center justify-center text-white text-xl shadow-lg">📚</div>
          <div>
            <h3 className="font-bold text-slate-800 group-hover:text-indigo-600 transition-colors">Gérer les cours</h3>
            <p className="text-sm text-slate-500">Ajouter, modifier ou supprimer des cours</p>
          </div>
          <svg className="w-5 h-5 text-slate-300 ml-auto group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" /></svg>
        </Link>
        <Link to="/admin/users" className="bg-white rounded-2xl shadow-md hover:shadow-xl border border-slate-100 p-6 flex items-center gap-4 group transition-all">
          <div className="w-12 h-12 bg-gradient-to-br from-indigo-500 to-indigo-600 rounded-2xl flex items-center justify-center text-white text-xl shadow-lg">👥</div>
          <div>
            <h3 className="font-bold text-slate-800 group-hover:text-indigo-600 transition-colors">Gérer les utilisateurs</h3>
            <p className="text-sm text-slate-500">Voir et gérer les comptes</p>
          </div>
          <svg className="w-5 h-5 text-slate-300 ml-auto group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" /></svg>
        </Link>
      </div>
    </div>
  );
}
