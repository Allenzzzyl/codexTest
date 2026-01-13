export async function apiRequest(path, options = {}) {
  const token = localStorage.getItem('blog_token');
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  };
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }
  const response = await fetch(path, { ...options, headers });
  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: '请求失败' }));
    throw new Error(error.message || '请求失败');
  }
  if (response.status === 204) {
    return null;
  }
  return response.json();
}
