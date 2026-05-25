import { useState, useEffect, useRef } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';

export default function ChatPage() {
  const { user } = useAuth();
  const [conversations, setConversations] = useState([]);
  const [activeConv, setActiveConv] = useState(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [sending, setSending] = useState(false);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const messagesEndRef = useRef(null);

  useEffect(() => { loadConversations(); }, []);

  const loadConversations = async () => {
    try {
      const res = await api.get(`/chat/conversations/${user.userId}`);
      setConversations(res.data);
    } catch (err) { console.error(err); }
  };

  const selectConversation = async (conv) => {
    setActiveConv(conv.id);
    setSidebarOpen(false);
    try {
      const res = await api.get(`/chat/conversation/${conv.id}`);
      setMessages(res.data.messages || []);
    } catch (err) { console.error(err); }
  };

  const startNewConversation = () => {
    setActiveConv(null);
    setMessages([]);
    setSidebarOpen(false);
  };

  const handleSend = async (e) => {
    e.preventDefault();
    if (!input.trim() || sending) return;

    const userMsg = { role: 'USER', content: input, sentAt: new Date().toISOString() };
    setMessages((prev) => [...prev, userMsg]);
    const msg = input;
    setInput('');
    setSending(true);

    try {
      const res = await api.post('/chat', {
        userId: user.userId,
        conversationId: activeConv,
        message: msg,
      });
      const aiMsg = { role: 'AI', content: res.data.response || res.data.reply, sentAt: new Date().toISOString() };
      setMessages((prev) => [...prev, aiMsg]);

      if (!activeConv && res.data.conversationId) {
        setActiveConv(res.data.conversationId);
        loadConversations();
      }
    } catch (err) {
      setMessages((prev) => [...prev, { role: 'AI', content: 'Erreur de connexion au service IA.', sentAt: new Date().toISOString() }]);
    } finally {
      setSending(false);
    }
  };

  const deleteConversation = async (convId, e) => {
    e.stopPropagation();
    try {
      await api.delete(`/chat/conversation/${convId}`);
      if (activeConv === convId) { setActiveConv(null); setMessages([]); }
      loadConversations();
    } catch (err) { console.error(err); }
  };

  useEffect(() => { messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' }); }, [messages]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-6 page-enter">
      <div className="bg-white rounded-3xl shadow-xl border border-slate-100 overflow-hidden" style={{ height: 'calc(100vh - 180px)' }}>
        <div className="flex h-full">
          {/* Sidebar */}
          <div className={`${sidebarOpen ? 'translate-x-0' : '-translate-x-full'} md:translate-x-0 fixed md:relative z-30 md:z-auto w-72 h-full bg-slate-50 border-r border-slate-200 flex flex-col transition-transform`}>
            <div className="p-4 border-b border-slate-200">
              <button onClick={startNewConversation} className="w-full px-4 py-3 bg-indigo-600 hover:bg-indigo-700 text-white rounded-xl text-sm font-semibold transition-all shadow-md flex items-center justify-center gap-2">
                <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" /></svg>
                Nouvelle conversation
              </button>
            </div>
            <div className="flex-1 overflow-y-auto p-2 space-y-1">
              {conversations.map((conv) => (
                <div key={conv.id} onClick={() => selectConversation(conv)}
                  className={`flex items-center justify-between p-3 rounded-xl cursor-pointer group transition-all ${activeConv === conv.id ? 'bg-indigo-50 text-indigo-700' : 'hover:bg-slate-100 text-slate-700'}`}>
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium truncate">{conv.title || 'Conversation'}</p>
                    <p className="text-xs text-slate-400 mt-0.5">{new Date(conv.createdAt).toLocaleDateString('fr-FR')}</p>
                  </div>
                  <button onClick={(e) => deleteConversation(conv.id, e)} className="opacity-0 group-hover:opacity-100 p-1 hover:bg-red-50 rounded-lg transition-all">
                    <svg className="w-4 h-4 text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" /></svg>
                  </button>
                </div>
              ))}
              {conversations.length === 0 && (
                <p className="text-sm text-slate-400 text-center py-8">Aucune conversation</p>
              )}
            </div>
          </div>
          {sidebarOpen && <div className="fixed inset-0 bg-black/20 z-20 md:hidden" onClick={() => setSidebarOpen(false)} />}

          {/* Chat area */}
          <div className="flex-1 flex flex-col">
            {/* Chat header */}
            <div className="flex items-center gap-3 p-4 border-b border-slate-200">
              <button onClick={() => setSidebarOpen(!sidebarOpen)} className="md:hidden p-2 hover:bg-slate-50 rounded-xl">
                <svg className="w-5 h-5 text-slate-600" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" /></svg>
              </button>
              <div className="w-9 h-9 bg-gradient-to-br from-indigo-500 to-violet-500 rounded-xl flex items-center justify-center text-white text-lg">🤖</div>
              <div>
                <h2 className="text-sm font-bold text-slate-800">Chatbot IA</h2>
                <p className="text-xs text-slate-400">Ton tuteur personnel</p>
              </div>
            </div>

            {/* Messages */}
            <div className="flex-1 overflow-y-auto p-4 space-y-4">
              {messages.length === 0 && (
                <div className="flex flex-col items-center justify-center h-full text-center">
                  <span className="text-6xl mb-4">🤖</span>
                  <h3 className="text-lg font-bold text-slate-800 mb-2">Comment puis-je t'aider ?</h3>
                  <p className="text-sm text-slate-400 max-w-sm">Pose-moi une question sur tes cours, je suis là pour t'accompagner.</p>
                </div>
              )}
              {messages.map((msg, i) => (
                <div key={i} className={`flex ${msg.role === 'USER' ? 'justify-end' : 'justify-start'} animate-fade-in`}>
                  <div className={`max-w-[75%] px-5 py-3.5 rounded-2xl text-sm leading-relaxed ${
                    msg.role === 'USER'
                      ? 'bg-indigo-600 text-white rounded-br-md'
                      : 'bg-slate-100 text-slate-800 rounded-bl-md'
                  }`}>
                    <p className="whitespace-pre-wrap">{msg.content}</p>
                  </div>
                </div>
              ))}
              {sending && (
                <div className="flex justify-start animate-fade-in">
                  <div className="bg-slate-100 rounded-2xl rounded-bl-md px-5 py-4 flex items-center gap-1.5">
                    <div className="w-2 h-2 bg-slate-400 rounded-full typing-dot" />
                    <div className="w-2 h-2 bg-slate-400 rounded-full typing-dot" />
                    <div className="w-2 h-2 bg-slate-400 rounded-full typing-dot" />
                  </div>
                </div>
              )}
              <div ref={messagesEndRef} />
            </div>

            {/* Input */}
            <form onSubmit={handleSend} className="p-4 border-t border-slate-200">
              <div className="flex items-center gap-3">
                <input
                  value={input}
                  onChange={(e) => setInput(e.target.value)}
                  placeholder="Écris ton message..."
                  className="flex-1 border border-slate-200 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all"
                />
                <button type="submit" disabled={!input.trim() || sending}
                  className="w-12 h-12 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-400 text-white rounded-xl flex items-center justify-center transition-all shadow-md shrink-0">
                  <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" /></svg>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
