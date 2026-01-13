import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { apiRequest } from '../api/client.js';

export default function PostListPage() {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    apiRequest('/api/posts')
      .then(setPosts)
      .catch((err) => setError(err.message));
  }, []);

  return (
    <section>
      <h1>最新文章</h1>
      {error && <p className="error">{error}</p>}
      <div className="post-list">
        {posts.map((post) => (
          <article key={post.id} className="card">
            <h2>
              <Link to={`/posts/${post.id}`}>{post.title}</Link>
            </h2>
            <div className="meta">
              <span>浏览 {post.viewCount}</span>
              <span>点赞 {post.likeCount}</span>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}
