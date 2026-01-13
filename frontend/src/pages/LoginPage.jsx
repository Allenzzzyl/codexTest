import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../api/client.js';
import { useAuth } from '../components/useAuth.js';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { setToken } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    try {
      const data = await apiRequest('/api/auth/login', {
        method: 'POST',
        body: JSON.stringify({ username, password })
      });
      setToken(data.token);
      navigate('/');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <section className="card">
      <h1>登录</h1>
      <form onSubmit={handleSubmit} className="form">
        <label>
          用户名
          <input value={username} onChange={(e) => setUsername(e.target.value)} required />
        </label>
        <label>
          密码
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </label>
        {error && <p className="error">{error}</p>}
        <button type="submit">登录</button>
      </form>
      <p className="hint">默认注册接口：/api/auth/register</p>
    </section>
  );
}
