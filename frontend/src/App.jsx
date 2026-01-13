import { Link, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage.jsx';
import PostListPage from './pages/PostListPage.jsx';
import PostDetailPage from './pages/PostDetailPage.jsx';
import PostEditorPage from './pages/PostEditorPage.jsx';
import { useAuth } from './components/useAuth.js';

export default function App() {
  const { token, logout } = useAuth();

  return (
    <div className="app">
      <header className="app-header">
        <Link to="/" className="logo">个人博客</Link>
        <nav>
          <Link to="/">文章</Link>
          <Link to="/editor">发布</Link>
        </nav>
        <div className="auth-actions">
          {token ? (
            <button type="button" onClick={logout}>退出</button>
          ) : (
            <Link to="/login">登录</Link>
          )}
        </div>
      </header>
      <main>
        <Routes>
          <Route path="/" element={<PostListPage />} />
          <Route path="/posts/:id" element={<PostDetailPage />} />
          <Route path="/editor" element={<PostEditorPage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </main>
    </div>
  );
}
