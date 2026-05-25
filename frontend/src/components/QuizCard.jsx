export default function QuizCard({ difficulty, onStart }) {
  const config = {
    EASY: { label: 'Facile', color: 'emerald', icon: '🟢' },
    MEDIUM: { label: 'Moyen', color: 'amber', icon: '🟡' },
    HARD: { label: 'Difficile', color: 'red', icon: '🔴' },
    beginner: { label: 'Débutant', color: 'emerald', icon: '🟢' },
    intermediate: { label: 'Intermédiaire', color: 'amber', icon: '🟡' },
    advanced: { label: 'Avancé', color: 'red', icon: '🔴' },
  };
  const c = config[difficulty] || config.EASY;
  const bgMap = { emerald: 'bg-emerald-50 border-emerald-200 hover:bg-emerald-100', amber: 'bg-amber-50 border-amber-200 hover:bg-amber-100', red: 'bg-red-50 border-red-200 hover:bg-red-100' };
  const textMap = { emerald: 'text-emerald-700', amber: 'text-amber-700', red: 'text-red-700' };

  return (
    <button
      onClick={() => onStart(difficulty)}
      className={`w-full p-4 rounded-2xl border-2 ${bgMap[c.color]} transition-all text-left group`}
    >
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          <span className="text-2xl">{c.icon}</span>
          <span className={`font-semibold ${textMap[c.color]}`}>{c.label}</span>
        </div>
        <svg className="w-5 h-5 text-slate-400 group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
        </svg>
      </div>
    </button>
  );
}
