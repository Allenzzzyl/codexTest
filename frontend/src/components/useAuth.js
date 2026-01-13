import { useEffect, useState } from 'react';

const storageKey = 'blog_token';

export function useAuth() {
  const [token, setToken] = useState(() => localStorage.getItem(storageKey));

  useEffect(() => {
    if (token) {
      localStorage.setItem(storageKey, token);
    } else {
      localStorage.removeItem(storageKey);
    }
  }, [token]);

  const logout = () => setToken(null);

  return { token, setToken, logout };
}
