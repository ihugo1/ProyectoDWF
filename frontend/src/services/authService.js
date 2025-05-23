export const login = async (dui, password) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ dui, password }),
  });

  if (!response.ok) {
    throw new Error('Credenciales incorrectas');
  }
  return await response.json(); 
};