import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../api/client.js';

export default function PostDetailPage() {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [comment, setComment] = useState('');
  const [error, setError] = useState('');

  const refresh = async () => {
    try {
      const detail = await apiRequest(`/api/posts/${id}`);
      setPost(detail);
      const list = await apiRequest(`/api/posts/${id}/comments`);
      setComments(list);
    } catch (err) {
      setError(err.message);
    }
  };

  useEffect(() => {
    apiRequest(`/api/posts/${id}/view`, { method: 'POST' }).catch(() => null);
    refresh();
  }, [id]);

  const handleLike = async () => {
    try {
      const updated = await apiRequest(`/api/posts/${id}/like`, { method: 'POST' });
      setPost(updated);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleComment = async (event) => {
    event.preventDefault();
    setError('');
    try {
      await apiRequest(`/api/posts/${id}/comments`, {
        method: 'POST',
        body: JSON.stringify({ content: comment })
      });
      setComment('');
      refresh();
    } catch (err) {
      setError(err.message);
    }
  };

  if (!post) {
    return <p>加载中...</p>;
  }

  return (
    <section className="card">
      <h1>{post.title}</h1>
      <p className="meta">作者：{post.author}</p>
      <p>{post.content}</p>
      <div className="meta">
        <span>浏览 {post.viewCount}</span>
        <span>点赞 {post.likeCount}</span>
        <button type="button" onClick={handleLike}>点赞</button>
      </div>
      {error && <p className="error">{error}</p>}
      <section className="comment-section">
        <h2>评论</h2>
        <form onSubmit={handleComment} className="form">
          <textarea value={comment} onChange={(e) => setComment(e.target.value)} required />
          <button type="submit">发表评论</button>
        </form>
        <ul className="comment-list">
          {comments.map((item) => (
            <li key={item.id}>
              <p className="meta">{item.author} · {item.createdAt}</p>
              <p>{item.content}</p>
            </li>
          ))}
        </ul>
      </section>
    </section>
  );
}
