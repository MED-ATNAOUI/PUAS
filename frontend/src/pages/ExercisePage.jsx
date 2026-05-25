import { useState, useEffect } from 'react';
import axios from '../api/axios';
import ReactMarkdown from 'react-markdown';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { oneDark } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { BookOpen, Code, Loader2, ChevronDown, Lightbulb, Eye, EyeOff } from 'lucide-react';

const DIFFICULTIES = [
    { value: 'beginner', label: 'Débutant' },
    { value: 'intermediate', label: 'Intermédiaire' },
    { value: 'advanced', label: 'Avancé' },
];

const ExercisePage = () => {
    const [sections, setSections] = useState([]);
    const [selectedSection, setSelectedSection] = useState('');
    const [difficulty, setDifficulty] = useState('beginner');
    const [exercise, setExercise] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [activeTab, setActiveTab] = useState('statement'); // 'statement' | 'solution'
    const [showSolution, setShowSolution] = useState(false);

    // Charger les sections disponibles
    useEffect(() => {
        const fetchSections = async () => {
            try {
                const response = await axios.get('/api/sections');
                setSections(response.data);
            } catch (err) {
                console.error('Erreur chargement sections:', err);
            }
        };
        fetchSections();
    }, []);

    const handleGenerate = async () => {
        if (!selectedSection) {
            setError('Veuillez sélectionner une section.');
            return;
        }
        setError('');
        setLoading(true);
        setExercise(null);
        setShowSolution(false);
        setActiveTab('statement');

        try {
            const response = await axios.get(
                `/api/exercises/section/${selectedSection}/difficulty/${difficulty}`
            );
            setExercise(response.data);
        } catch (err) {
            setError("Erreur lors de la génération de l'exercice. Veuillez réessayer.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    // Composant de rendu Markdown avec syntax highlighting
    const MarkdownRenderer = ({ content }) => (
        <ReactMarkdown
            components={{
                code({ node, inline, className, children, ...props }) {
                    const match = /language-(\w+)/.exec(className || '');
                    if (!inline && match) {
                        return (
                            <div className="rounded-xl overflow-hidden my-4 border border-gray-700/40">
                                <div className="flex items-center justify-between px-4 py-2 bg-gray-900 border-b border-gray-700/40">
                                    <div className="flex gap-1.5">
                                        <span className="w-3 h-3 rounded-full bg-red-500/70" />
                                        <span className="w-3 h-3 rounded-full bg-yellow-500/70" />
                                        <span className="w-3 h-3 rounded-full bg-green-500/70" />
                                    </div>
                                    <span className="text-xs text-gray-500 font-mono">{match[1]}</span>
                                </div>
                                <SyntaxHighlighter
                                    style={oneDark}
                                    language={match[1]}
                                    PreTag="div"
                                    customStyle={{
                                        margin: 0,
                                        padding: '1rem 1.25rem',
                                        background: 'rgba(10,15,30,0.95)',
                                        fontSize: '0.85rem',
                                    }}
                                    {...props}
                                >
                                    {String(children).replace(/\n$/, '')}
                                </SyntaxHighlighter>
                            </div>
                        );
                    }
                    return (
                        <code className="bg-indigo-500/10 text-indigo-300 px-1.5 py-0.5 rounded text-sm" {...props}>
                            {children}
                        </code>
                    );
                },
            }}
        >
            {content}
        </ReactMarkdown>
    );

    return (
        <div className="min-h-screen bg-gray-950 text-white px-4 py-8">
            <div className="max-w-4xl mx-auto">

                {/* Header */}
                <div className="mb-8">
                    <div className="flex items-center gap-3 mb-2">
                        <div className="w-10 h-10 bg-gradient-to-br from-emerald-500 to-teal-600 rounded-xl flex items-center justify-center shadow-lg shadow-emerald-500/20">
                            <Code className="w-5 h-5 text-white" />
                        </div>
                        <h1 className="text-2xl font-bold text-white">Exercices IA</h1>
                    </div>
                    <p className="text-gray-400 ml-13">
                        Générez des exercices de programmation personnalisés selon votre section et niveau.
                    </p>
                </div>

                {/* Formulaire */}
                <div className="bg-gray-900/60 backdrop-blur-sm border border-gray-800/60 rounded-2xl p-6 mb-6">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

                        {/* Section */}
                        <div className="md:col-span-2">
                            <label className="block text-sm font-semibold text-gray-300 mb-2">Section</label>
                            <div className="relative">
                                <select
                                    value={selectedSection}
                                    onChange={(e) => setSelectedSection(e.target.value)}
                                    className="w-full bg-gray-800/60 border border-gray-700/50 rounded-xl px-4 py-3 text-white appearance-none focus:border-emerald-500/50 focus:ring-2 focus:ring-emerald-500/20 outline-none transition-all"
                                >
                                    <option value="">Sélectionner une section...</option>
                                    {sections.map((s) => (
                                        <option key={s.id} value={s.id}>
                                            {s.courseTitle ? `${s.courseTitle} — ` : ''}{s.title}
                                        </option>
                                    ))}
                                </select>
                                <ChevronDown className="absolute right-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-500 pointer-events-none" />
                            </div>
                        </div>

                        {/* Difficulté */}
                        <div>
                            <label className="block text-sm font-semibold text-gray-300 mb-2">Difficulté</label>
                            <div className="relative">
                                <select
                                    value={difficulty}
                                    onChange={(e) => setDifficulty(e.target.value)}
                                    className="w-full bg-gray-800/60 border border-gray-700/50 rounded-xl px-4 py-3 text-white appearance-none focus:border-emerald-500/50 focus:ring-2 focus:ring-emerald-500/20 outline-none transition-all"
                                >
                                    {DIFFICULTIES.map((d) => (
                                        <option key={d.value} value={d.value}>{d.label}</option>
                                    ))}
                                </select>
                                <ChevronDown className="absolute right-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-500 pointer-events-none" />
                            </div>
                        </div>
                    </div>

                    {error && (
                        <div className="mt-4 p-3 bg-red-500/10 border border-red-500/20 rounded-xl text-red-400 text-sm">
                            ⚠️ {error}
                        </div>
                    )}

                    <button
                        onClick={handleGenerate}
                        disabled={loading}
                        className="mt-5 w-full py-3.5 bg-gradient-to-r from-emerald-500 to-teal-600 rounded-xl text-white font-bold shadow-lg shadow-emerald-500/20 hover:shadow-emerald-500/40 hover:scale-[1.01] active:scale-[0.99] transition-all duration-300 disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
                    >
                        {loading ? (
                            <>
                                <Loader2 className="w-5 h-5 animate-spin" />
                                Génération en cours...
                            </>
                        ) : (
                            <>
                                <BookOpen className="w-5 h-5" />
                                Générer un exercice
                            </>
                        )}
                    </button>
                </div>

                {/* Résultat */}
                {exercise && (
                    <div className="bg-gray-900/60 backdrop-blur-sm border border-gray-800/60 rounded-2xl overflow-hidden">

                        {/* Title bar */}
                        <div className="px-6 py-4 border-b border-gray-800/60 flex items-start justify-between gap-4">
                            <div>
                                <h2 className="text-lg font-bold text-white">{exercise.title}</h2>
                                <div className="flex items-center gap-2 mt-1">
                                    {exercise.courseTitle && (
                                        <span className="text-xs text-gray-500">{exercise.courseTitle}</span>
                                    )}
                                    {exercise.courseTitle && exercise.sectionTitle && (
                                        <span className="text-gray-700">·</span>
                                    )}
                                    {exercise.sectionTitle && (
                                        <span className="text-xs text-gray-500">{exercise.sectionTitle}</span>
                                    )}
                                    <span className={`text-xs px-2 py-0.5 rounded-full border font-medium ${exercise.difficulty === 'beginner'
                                        ? 'bg-green-500/10 text-green-400 border-green-500/20'
                                        : exercise.difficulty === 'intermediate'
                                            ? 'bg-orange-500/10 text-orange-400 border-orange-500/20'
                                            : 'bg-red-500/10 text-red-400 border-red-500/20'
                                        }`}>
                                        {exercise.difficulty === 'beginner' ? 'Débutant'
                                            : exercise.difficulty === 'intermediate' ? 'Intermédiaire'
                                                : 'Avancé'}
                                    </span>
                                </div>
                            </div>
                        </div>

                        {/* Tabs */}
                        <div className="flex border-b border-gray-800/60">
                            <button
                                onClick={() => setActiveTab('statement')}
                                className={`flex items-center gap-2 px-6 py-3 text-sm font-medium transition-all border-b-2 ${activeTab === 'statement'
                                    ? 'text-emerald-400 border-emerald-500'
                                    : 'text-gray-500 border-transparent hover:text-gray-300'
                                    }`}
                            >
                                <BookOpen className="w-4 h-4" />
                                Énoncé
                            </button>
                            <button
                                onClick={() => { setActiveTab('solution'); setShowSolution(true); }}
                                className={`flex items-center gap-2 px-6 py-3 text-sm font-medium transition-all border-b-2 ${activeTab === 'solution'
                                    ? 'text-indigo-400 border-indigo-500'
                                    : 'text-gray-500 border-transparent hover:text-gray-300'
                                    }`}
                            >
                                <Lightbulb className="w-4 h-4" />
                                Solution
                                {!showSolution && (
                                    <span className="text-xs bg-gray-800 px-2 py-0.5 rounded-full">🔒</span>
                                )}
                            </button>
                        </div>

                        {/* Content */}
                        <div className="p-6">
                            {activeTab === 'statement' && (
                                <div className="prose prose-invert max-w-none text-gray-200 text-sm leading-relaxed">
                                    <MarkdownRenderer content={exercise.statement} />
                                </div>
                            )}

                            {activeTab === 'solution' && (
                                <div>
                                    {!showSolution ? (
                                        <div className="text-center py-12">
                                            <div className="w-16 h-16 bg-indigo-500/10 rounded-2xl flex items-center justify-center mx-auto mb-4">
                                                <EyeOff className="w-7 h-7 text-indigo-400" />
                                            </div>
                                            <p className="text-gray-400 mb-4">
                                                Essayez de résoudre l'exercice avant de consulter la solution !
                                            </p>
                                            <button
                                                onClick={() => setShowSolution(true)}
                                                className="flex items-center gap-2 px-6 py-3 bg-indigo-500/10 border border-indigo-500/20 rounded-xl text-indigo-400 hover:bg-indigo-500/20 transition-all mx-auto font-medium"
                                            >
                                                <Eye className="w-4 h-4" />
                                                Afficher la solution
                                            </button>
                                        </div>
                                    ) : (
                                        <div className="prose prose-invert max-w-none text-gray-200 text-sm leading-relaxed">
                                            <MarkdownRenderer content={exercise.solution} />
                                        </div>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ExercisePage;