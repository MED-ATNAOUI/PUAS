import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import CourseCard from '../components/CourseCard';

export default function CoursesPage() {
  const { user } = useAuth();
  const [courses, setCourses] = useState([]);
  const [enrollments, setEnrollments] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const [coursesRes, enrollRes] = await Promise.all([
        api.get('/courses'),
        api.get(`/enrollments/student/${user.userId}`),
      ]);
      setCourses(coursesRes.data);
      setEnrollments(enrollRes.data);
    } catch (err) { console.error(err); }
    finally { setLoading(false); }
  };

  const handleEnroll = async (courseId) => {
    try {
      await api.post(`/enrollments/student/${user.userId}/course/${courseId}`);
      loadData();
    } catch (err) { console.error(err); }
  };

  const enrolledIds = new Set(enrollments.map((e) => e.courseId));
  const filtered = courses.filter((c) =>
    c.title.toLowerCase().includes(search.toLowerCase()) ||
    c.description?.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-10">
        <div className="skeleton h-12 w-80 rounded-xl mb-8" />
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {[1, 2, 3, 4, 5, 6].map((i) => (<div key={i} className="skeleton h-64 rounded-2xl" />))}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 page-enter">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
        <div>
          <h1 className="text-3xl font-bold text-slate-800 tracking-tight">Catalogue des cours</h1>
          <p className="text-slate-500 mt-1">{courses.length} cours disponibles</p>
        </div>
        <div className="relative">
          <svg className="w-5 h-5 text-slate-400 absolute left-3.5 top-1/2 -translate-y-1/2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <input
            type="text"
            placeholder="Rechercher un cours..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="pl-11 pr-4 py-3 border border-slate-200 rounded-xl text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none w-full sm:w-72 transition-all"
          />
        </div>
      </div>

      {filtered.length > 0 ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {filtered.map((course) => (
            <CourseCard
              key={course.id}
              course={course}
              enrolled={enrolledIds.has(course.id)}
              onEnroll={!enrolledIds.has(course.id) ? handleEnroll : undefined}
            />
          ))}
        </div>
      ) : (
        <div className="bg-white rounded-2xl border border-slate-100 p-12 text-center">
          <p className="text-slate-400 text-lg">Aucun cours trouvé pour "{search}"</p>
        </div>
      )}
    </div>
  );
}
