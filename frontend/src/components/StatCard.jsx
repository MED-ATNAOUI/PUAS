export default function StatCard({ icon, value, label, color = 'indigo', trend }) {
  const colorMap = {
    indigo: 'from-indigo-500 to-indigo-600 shadow-indigo-200',
    violet: 'from-violet-500 to-violet-600 shadow-violet-200',
    cyan: 'from-cyan-500 to-cyan-600 shadow-cyan-200',
    emerald: 'from-emerald-500 to-emerald-600 shadow-emerald-200',
    amber: 'from-amber-500 to-amber-600 shadow-amber-200',
    rose: 'from-rose-500 to-rose-600 shadow-rose-200',
  };

  return (
    <div className="bg-white rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-slate-100 p-6 group">
      <div className="flex items-start justify-between">
        <div>
          <p className="text-sm font-medium text-slate-500 mb-1">{label}</p>
          <p className="text-3xl font-bold text-slate-800 tracking-tight">{value}</p>
          {trend && (
            <p className={`text-xs font-medium mt-2 ${trend > 0 ? 'text-emerald-500' : 'text-red-500'}`}>
              {trend > 0 ? '↑' : '↓'} {Math.abs(trend)}%
            </p>
          )}
        </div>
        <div className={`w-12 h-12 bg-gradient-to-br ${colorMap[color]} rounded-2xl flex items-center justify-center shadow-lg group-hover:scale-110 transition-transform`}>
          <span className="text-white text-xl">{icon}</span>
        </div>
      </div>
    </div>
  );
}
