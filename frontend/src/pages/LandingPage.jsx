import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function LandingPage() {
  const { isAuthenticated } = useAuth();

  const features = [
    {
      icon: '🧠',
      title: 'Quiz générés par IA',
      description: 'Des quiz adaptatifs créés par intelligence artificielle selon votre niveau et la matière étudiée.',
    },
    {
      icon: '🤖',
      title: 'Chatbot tuteur',
      description: 'Un assistant IA disponible 24/7 pour répondre à vos questions et vous accompagner.',
    },
    {
      icon: '📊',
      title: 'Progression en temps réel',
      description: 'Suivez votre avancement, vos scores et votre niveau avec des statistiques détaillées.',
    },
  ];

  return (
    <div className="page-enter">
      {/* Hero Section */}
      <section className="relative overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-br from-indigo-600 via-violet-600 to-purple-700" />
        <div className="absolute inset-0 bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNjAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA2MCA2MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxnIGZpbGw9IiNmZmYiIGZpbGwtb3BhY2l0eT0iMC4wNSI+PHBhdGggZD0iTTM2IDM0djItSDI0di0yaDEyek0zNiAyNHYySDI0di0yaDEyeiIvPjwvZz48L2c+PC9zdmc+')] opacity-30" />

        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 md:py-36">
          <div className="text-center max-w-4xl mx-auto">
            <div className="inline-flex items-center gap-2 px-4 py-2 bg-white/10 backdrop-blur-sm rounded-full text-sm text-white/90 mb-8 border border-white/20">
              <span className="w-2 h-2 bg-emerald-400 rounded-full animate-pulse" />
              Propulsé par l'Intelligence Artificielle
            </div>

            <h1 className="text-4xl md:text-6xl lg:text-7xl font-extrabold text-white tracking-tight leading-tight mb-6">
              Apprends avec
              <span className="block bg-gradient-to-r from-cyan-300 to-emerald-300 bg-clip-text text-transparent">
                l'Intelligence Artificielle
              </span>
            </h1>

            <p className="text-lg md:text-xl text-white/80 max-w-2xl mx-auto mb-10 leading-relaxed">
              Des quiz adaptatifs, un chatbot tuteur et un suivi personnalisé pour accélérer ton apprentissage.
            </p>

            <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
              <Link
                to={isAuthenticated ? '/home' : '/register'}
                className="px-8 py-4 bg-white text-indigo-600 font-bold rounded-2xl text-lg shadow-2xl shadow-indigo-900/30 hover:shadow-indigo-900/50 hover:scale-105 transition-all"
              >
                Commencer gratuitement
              </Link>
              <Link
                to={isAuthenticated ? '/courses' : '/login'}
                className="px-8 py-4 border-2 border-white/30 text-white font-semibold rounded-2xl text-lg hover:bg-white/10 backdrop-blur-sm transition-all"
              >
                Voir les cours →
              </Link>
            </div>
          </div>
        </div>

        {/* Decorative shapes */}
        <div className="absolute -left-20 -bottom-20 w-72 h-72 bg-cyan-400/20 rounded-full blur-3xl" />
        <div className="absolute -right-20 -top-20 w-96 h-96 bg-violet-400/20 rounded-full blur-3xl" />
      </section>

      {/* Features Section */}
      <section className="py-20 md:py-28 bg-slate-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 tracking-tight mb-4">
              Pourquoi choisir <span className="text-indigo-600">LearnAI</span> ?
            </h2>
            <p className="text-lg text-slate-500 max-w-2xl mx-auto">
              Une plateforme pensée pour l'apprentissage moderne, alimentée par l'IA.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {features.map((feature, i) => (
              <div
                key={i}
                className="bg-white rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-slate-100 p-8 text-center group animate-fade-in-up"
                style={{ animationDelay: `${i * 0.15}s` }}
              >
                <div className="text-5xl mb-5">{feature.icon}</div>
                <h3 className="text-xl font-bold text-slate-800 mb-3 group-hover:text-indigo-600 transition-colors">
                  {feature.title}
                </h3>
                <p className="text-sm text-slate-500 leading-relaxed">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 bg-white">
        <div className="max-w-4xl mx-auto px-4 text-center">
          <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6 tracking-tight">
            Prêt à transformer ton apprentissage ?
          </h2>
          <p className="text-lg text-slate-500 mb-10">
            Rejoins des milliers d'étudiants qui utilisent déjà l'IA pour apprendre plus efficacement.
          </p>
          <Link
            to="/register"
            className="inline-block px-10 py-4 bg-gradient-to-r from-indigo-600 to-violet-600 text-white font-bold rounded-2xl text-lg shadow-xl shadow-indigo-200 hover:shadow-indigo-300 hover:scale-105 transition-all"
          >
            Créer mon compte gratuitement
          </Link>
        </div>
      </section>
    </div>
  );
}
