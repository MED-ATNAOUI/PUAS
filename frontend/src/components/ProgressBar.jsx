export default function ProgressBar({ value = 0, max = 100, label, color = 'indigo', size = 'md' }) {
  const percent = Math.min(Math.round((value / max) * 100), 100);
  const heights = { sm: 'h-1.5', md: 'h-2.5', lg: 'h-4' };
  const colors = {
    indigo: 'bg-indigo-500',
    emerald: 'bg-emerald-500',
    amber: 'bg-amber-500',
    red: 'bg-red-500',
    violet: 'bg-violet-500',
  };

  return (
    <div className="w-full">
      {label && (
        <div className="flex items-center justify-between mb-1.5">
          <span className="text-xs font-medium text-slate-600">{label}</span>
          <span className="text-xs font-bold text-slate-800">{percent}%</span>
        </div>
      )}
      <div className={`w-full bg-slate-100 rounded-full ${heights[size]} overflow-hidden`}>
        <div
          className={`${colors[color]} ${heights[size]} rounded-full transition-all duration-700 ease-out`}
          style={{ width: `${percent}%` }}
        />
      </div>
    </div>
  );
}
