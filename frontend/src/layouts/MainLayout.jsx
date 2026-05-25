import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar';

export default function MainLayout() {
  return (
    <div className="min-h-screen bg-slate-50 flex flex-col">
      <Navbar />
      <main className="flex-1">
        <div className="page-enter">
          <Outlet />
        </div>
      </main>
      <footer className="bg-white border-t border-slate-200 py-6">
        <div className="max-w-7xl mx-auto px-4 text-center">
          <p className="text-sm text-slate-500">
            © 2026 <span className="font-semibold bg-gradient-to-r from-indigo-600 to-violet-500 bg-clip-text text-transparent">LearnAI</span> — Plateforme d'apprentissage intelligente
          </p>
        </div>
      </footer>
    </div>
  );
}
