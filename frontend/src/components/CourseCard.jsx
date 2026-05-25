import { Link } from 'react-router-dom';

export default function CourseCard({ course, enrolled = false, onEnroll, onUnenroll }) {
  return (
    <div className="bg-white rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-slate-100 overflow-hidden group">
      {/* Course header gradient */}
      <div className="h-32 bg-gradient-to-br from-indigo-500 via-violet-500 to-purple-600 p-5 flex flex-col justify-end relative overflow-hidden">
        <div className="absolute inset-0 bg-black/10" />
        <div className="absolute -right-6 -top-6 w-24 h-24 bg-white/10 rounded-full" />
        <div className="absolute -right-2 -bottom-8 w-32 h-32 bg-white/5 rounded-full" />
        <h3 className="text-lg font-bold text-white relative z-10 line-clamp-2">{course.title}</h3>
      </div>

      <div className="p-5">
        <p className="text-sm text-slate-500 line-clamp-2 mb-4 leading-relaxed">
          {course.description || 'Aucune description disponible.'}
        </p>

        <div className="flex items-center justify-between">
          {enrolled ? (
            <span className="inline-flex items-center gap-1.5 px-3 py-1.5 bg-emerald-50 text-emerald-600 rounded-full text-xs font-semibold">
              <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M5 13l4 4L19 7" />
              </svg>
              Inscrit
            </span>
          ) : onEnroll ? (
            <button
              onClick={() => onEnroll(course.id)}
              className="px-4 py-2 bg-indigo-50 text-indigo-600 rounded-xl text-xs font-semibold hover:bg-indigo-100 transition-colors"
            >
              S'inscrire
            </button>
          ) : null}

          <Link
            to={`/courses/${course.id}`}
            className="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-xl text-xs font-semibold transition-all shadow-md shadow-indigo-200 hover:shadow-indigo-300"
          >
            Voir le cours
          </Link>
        </div>
      </div>
    </div>
  );
}
