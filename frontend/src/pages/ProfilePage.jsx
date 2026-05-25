import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';

export default function ProfilePage() {
  const { user, updateUser } = useAuth();
  const [form, setForm] = useState({
    nom: user?.nom || '',
    prenom: user?.prenom || '',
    email: user?.email || '',
    password: '',
    level: user?.level || 'beginner',
  });
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const body = { ...form };
      if (!body.password) delete body.password;
      await api.put(`/auth/profile/${user.userId}`, body);
      updateUser({ nom: form.nom, prenom: form.prenom, email: form.email, level: form.level });
      setSuccess('Profil mis à jour avec succès !');
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Erreur lors de la mise à jour');
    } finally {
      setLoading(false);
    }
  };

  const initials = `${user?.prenom?.[0] || ''}${user?.nom?.[0] || ''}`.toUpperCase();

  const levelLabels = { beginner: 'Débutant', intermediate: 'Intermédiaire', advanced: 'Avancé' };

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 py-8 page-enter">
      <h1 className="text-3xl font-bold text-slate-800 tracking-tight mb-8">Mon Profil</h1>

      <div className="bg-white rounded-3xl shadow-xl border border-slate-100 overflow-hidden">
        {/* Header */}
        <div className="bg-gradient-to-br from-indigo-600 to-violet-600 p-8 flex items-center gap-5">
          <div className="w-20 h-20 bg-white/20 backdrop-blur-sm rounded-2xl flex items-center justify-center text-3xl font-bold text-white border-2 border-white/30 shadow-xl">
            {initials}
          </div>
          <div>
            <h2 className="text-2xl font-bold text-white">{user?.prenom} {user?.nom}</h2>
            <div className="flex items-center gap-3 mt-2">
              <span className="px-3 py-1 bg-white/20 text-white rounded-full text-xs font-semibold border border-white/20">
                {user?.role}
              </span>
              <span className="px-3 py-1 bg-white/20 text-white rounded-full text-xs font-semibold border border-white/20">
                {levelLabels[user?.level] || user?.level}
              </span>
            </div>
          </div>
        </div>

        {/* Form */}
        <div className="p-8">
          {success && (
            <div className="mb-5 p-4 bg-emerald-50 border border-emerald-200 rounded-xl text-sm text-emerald-600 animate-fade-in">
              ✅ {success}
            </div>
          )}
          {error && (
            <div className="mb-5 p-4 bg-red-50 border border-red-200 rounded-xl text-sm text-red-600 animate-fade-in">
              ❌ {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Nom</label>
                <input name="nom" value={form.nom} onChange={handleChange} className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Prénom</label>
                <input name="prenom" value={form.prenom} onChange={handleChange} className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Email</label>
              <input name="email" type="email" value={form.email} onChange={handleChange} className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Nouveau mot de passe <span className="text-slate-400">(laisser vide pour ne pas changer)</span></label>
              <input name="password" type="password" value={form.password} onChange={handleChange} placeholder="••••••••" className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Niveau</label>
              <select name="level" value={form.level} onChange={handleChange} className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none bg-white transition-all">
                <option value="beginner">Débutant</option>
                <option value="intermediate">Intermédiaire</option>
                <option value="advanced">Avancé</option>
              </select>
            </div>
            <button type="submit" disabled={loading}
              className="px-8 py-3.5 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 text-white font-semibold rounded-xl transition-all shadow-lg shadow-indigo-200 flex items-center gap-2">
              {loading && <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />}
              Sauvegarder
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
