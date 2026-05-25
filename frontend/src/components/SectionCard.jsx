import { Link } from 'react-router-dom';

export default function SectionCard({ section, index }) {
  return (
    <div className="bg-white rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 border border-slate-100 p-5 group animate-fade-in-up" style={{ animationDelay: `${index * 0.1}s` }}>
      <div className="flex items-start gap-4">
        {/* Section number */}
        <div className="w-10 h-10 bg-gradient-to-br from-indigo-500 to-violet-500 rounded-xl flex items-center justify-center text-white font-bold text-sm shrink-0 shadow-md">
          {index + 1}
        </div>

        <div className="flex-1 min-w-0">
          <h3 className="text-base font-semibold text-slate-800 mb-1 group-hover:text-indigo-600 transition-colors">
            {section.title}
          </h3>
          <p className="text-sm text-slate-500 line-clamp-2 leading-relaxed">
            {section.summary || section.content?.substring(0, 100) || 'Contenu du chapitre'}
          </p>

          <div className="flex items-center gap-3 mt-3">
            {section.videoUrl && (
              <span className="inline-flex items-center gap-1 text-xs text-violet-600 bg-violet-50 px-2.5 py-1 rounded-full font-medium">
                <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z" />
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                Vidéo
              </span>
            )}
            <Link
              to={`/lesson/${section.id}`}
              className="inline-flex items-center gap-1 text-xs font-semibold text-indigo-600 hover:text-indigo-700 transition-colors"
            >
              Commencer
              <svg className="w-3.5 h-3.5 group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
              </svg>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
