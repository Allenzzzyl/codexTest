import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../api/client.js';

export default function PostEditorPage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    try {
      const post = await apiRequest('/api/posts', {
        method: 'POST',
        body: JSON.stringify({ title, content })
      });
      navigate(`/posts/${post.id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <section className="card">
      <h1>发布文章</h1>
      <form onSubmit={handleSubmit} className="form">
        <label>
          标题
          <input value={title} onChange={(e) => setTitle(e.target.value)} required />
        </label>
        <label>
          正文
          <textarea value={content} onChange={(e) => setContent(e.target.value)} required />
        </label>
        {error && <p className="error">{error}</p>}
        <button type="submit">发布</button>
      </form>
      <p className="hint">需要登录且拥有 ROLE_AUTHOR 权限。</p>
    </section>
  );
}
